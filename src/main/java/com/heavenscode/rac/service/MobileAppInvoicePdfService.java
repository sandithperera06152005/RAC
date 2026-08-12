package com.heavenscode.rac.service;

import com.heavenscode.rac.config.ApplicationProperties;
import com.heavenscode.rac.domain.Salesinvoice;
import com.heavenscode.rac.repository.SalesinvoiceRepository;
import com.heavenscode.rac.web.rest.errors.BadRequestAlertException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MobileAppInvoicePdfService {

    private static final String ENTITY_NAME = "salesinvoice";
    private static final float PAGE_WIDTH = 595F;
    private static final float PAGE_HEIGHT = 842F;
    private static final String LOGO_RESOURCE = "templates/invoice/rac-logo.png";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMMM-yyyy", Locale.ENGLISH);
    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

    private final ApplicationProperties applicationProperties;
    private final SalesinvoiceRepository salesinvoiceRepository;
    private final LegacyInvoiceChildrenReadService legacyInvoiceChildrenReadService;

    public MobileAppInvoicePdfService(
        ApplicationProperties applicationProperties,
        SalesinvoiceRepository salesinvoiceRepository,
        LegacyInvoiceChildrenReadService legacyInvoiceChildrenReadService
    ) {
        this.applicationProperties = applicationProperties;
        this.salesinvoiceRepository = salesinvoiceRepository;
        this.legacyInvoiceChildrenReadService = legacyInvoiceChildrenReadService;
    }

    public MobileAppInvoicePdfResult generate(Long invoiceId) {
        Salesinvoice invoice = salesinvoiceRepository
            .findById(invoiceId)
            .orElseThrow(() -> new BadRequestAlertException("Sales invoice not found", ENTITY_NAME, "idnotfound"));

        if (StringUtils.isBlank(invoice.getCode())) {
            throw new BadRequestAlertException("Sales invoice code is required to generate PDF", ENTITY_NAME, "codemissing");
        }

        Path outputDirectory = resolveOutputDirectory();
        Path outputFile = outputDirectory.resolve(sanitizeFileName(invoice.getCode()) + ".pdf");
        Integer invoiceIdAsInteger = Math.toIntExact(invoice.getId());

        List<Map<String, Object>> itemLines = readItemLines(invoiceIdAsInteger);
        List<Map<String, Object>> serviceLines = readServiceLines(invoiceIdAsInteger);
        List<Map<String, Object>> commonServiceLines = readCommonServiceLines(invoiceIdAsInteger);

        try {
            Files.createDirectories(outputDirectory);
            Files.write(outputFile, buildPdf(invoice, itemLines, serviceLines, commonServiceLines));
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to save mobile app sales invoice PDF", ex);
        }

        return new MobileAppInvoicePdfResult(invoice.getId(), invoice.getCode(), outputFile.toString());
    }

    private Path resolveOutputDirectory() {
        String configuredPath = applicationProperties.getMobileAppInvoicePdf().getFilePath();
        if (StringUtils.isBlank(configuredPath)) {
            throw new BadRequestAlertException("Mobile app invoice PDF file path is not configured", ENTITY_NAME, "filepathmissing");
        }
        return Path.of(configuredPath);
    }

    private byte[] buildPdf(
        Salesinvoice invoice,
        List<Map<String, Object>> itemLines,
        List<Map<String, Object>> serviceLines,
        List<Map<String, Object>> commonServiceLines
    ) throws IOException {
        PdfTextPage page = new PdfTextPage();
        page.imageTop(24, 24, 258, 54, PdfImage.fromClasspath(LOGO_RESOURCE));
        addHeader(page, invoice);
        addTemplateLines(page);
        float tableBottom = addLineTable(page, itemLines, serviceLines, commonServiceLines);
        addTotals(page, invoice, tableBottom);
        addFooter(page);
        return page.toPdf();
    }

    private void addTemplateLines(PdfTextPage page) {
        page.lineTop(24, 804, 570, 804, 0.8F);
        page.lineTop(24, 818, 570, 818, 0.8F);
    }

    private void addHeader(PdfTextPage page, Salesinvoice invoice) {
        page.topTextBold(462, 22, 18, "INVOICE");
        page.topTextBold(415, 104, 10, "Rathnaweera Auto Care PVT LTD");

        page.topTextBold(429, 128, 8, "Tel: 047 - 2261613 / 076 - 8223055");
        page.topTextBold(412, 143, 8, "Web: www.rathnaweeraautocare.com");
        page.topTextBold(396, 157, 8, "E-mail: rathnaweeraautocare@yahoo.com");
        page.topTextBold(432, 172, 8, "Facebook: rathnaweera auto care");
        page.topTextBold(406, 187, 8, "Address: Moraketiya Road, Embilipitiya.");

        page.topTextBold(24, 80, 8, "Invoice To:");
        page.topTextBold(24, 98, 8, "Name:");
        page.topText(66, 98, 8, value(invoice.getCustomername()));
        page.topTextBold(24, 116, 8, "Address:");
        page.topText(66, 116, 8, limit(value(invoice.getCustomeraddress()), 52));
        page.topTextBold(24, 134, 8, "Vehicle No:");
        page.topText(96, 134, 8, value(invoice.getVehicleno()));
        page.topTextBold(24, 152, 8, "CURR Service:");
        page.topText(114, 152, 8, value(invoice.getCurrentmeter()));
        page.topTextBold(24, 170, 8, "Next Services:");
        page.topText(114, 170, 8, value(invoice.getNextmeter()));
        page.topTextBold(24, 188, 8, "INV Type:");
        page.topText(114, 188, 8, value(invoice.getPaymenttype()).toUpperCase(Locale.ENGLISH));

        page.topTextBold(382, 80, 8, "Invoice Date:");
        page.topTextBold(460, 80, 8, formatDate(invoice));
        page.topTextBold(389, 56, 8, "Invoice No:");
        page.topTextBold(460, 56, 10, value(invoice.getCode()));
    }

    private float addLineTable(
        PdfTextPage page,
        List<Map<String, Object>> itemLines,
        List<Map<String, Object>> serviceLines,
        List<Map<String, Object>> commonServiceLines
    ) {
        float tableTop = 205F;
        float headerBottom = 224F;
        float rowHeight = 18F;

        page.topTextBold(48, 212, 8, "Description");
        page.topTextBold(360, 212, 8, "Discount");
        page.topTextBold(415, 212, 8, "QTY");
        page.topTextBold(459, 212, 8, "Unit Price");
        page.topTextBold(523, 212, 8, "Total Amount");

        int lineNumber = 1;
        float topY = 226F;
        for (Map<String, Object> line : itemLines) {
            if (topY > 760) {
                break;
            }
            BigDecimal quantity = amount(line.get("quantity"));
            BigDecimal unitPrice = amount(valueOrFallback(line, "sellingprice", "itemprice"));
            BigDecimal lineTotal = line.get("linetotal") != null ? amount(line.get("linetotal")) : unitPrice.multiply(quantity);
            addDetailLine(
                page,
                topY,
                lineNumber++,
                text(stringValue(line.get("itemname")), stringValue(line.get("description"))),
                line.get("discount"),
                quantity,
                unitPrice,
                lineTotal
            );
            topY += rowHeight;
        }
        for (Map<String, Object> line : serviceLines) {
            if (topY > 760) {
                break;
            }
            BigDecimal price = amount(valueOrFallback(line, "servicePrice", "value"));
            addDetailLine(
                page,
                topY,
                lineNumber++,
                text(stringValue(line.get("serviceName")), stringValue(line.get("serviceDescription"))),
                line.get("discount"),
                BigDecimal.ONE,
                price,
                price
            );
            topY += rowHeight;
        }
        for (Map<String, Object> line : commonServiceLines) {
            if (topY > 760) {
                break;
            }
            BigDecimal price = amount(valueOrFallback(line, "servicePrice", "value"));
            addDetailLine(
                page,
                topY,
                lineNumber++,
                text(stringValue(line.get("name")), stringValue(line.get("description"))),
                line.get("discount"),
                BigDecimal.ONE,
                price,
                price
            );
            topY += rowHeight;
        }

        float tableBottom = headerBottom + (Math.max(lineNumber - 1, 1) * rowHeight);
        drawLineTable(page, tableTop, headerBottom, tableBottom);
        return tableBottom;
    }

    private void addDetailLine(
        PdfTextPage page,
        float topY,
        int lineNumber,
        String description,
        Object discount,
        BigDecimal quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
    ) {
        page.topRightText(42, topY, 8, String.valueOf(lineNumber));
        page.topText(48, topY, 8, limit(description, 52));
        page.topRightText(392, topY, 8, money(amount(discount)));
        page.topRightText(429, topY, 8, money(quantity));
        page.topRightText(494, topY, 8, money(unitPrice));
        page.topRightText(570, topY, 8, money(lineTotal));
    }

    private void drawLineTable(PdfTextPage page, float tableTop, float headerBottom, float tableBottom) {
        float left = 20F;
        float right = 574F;

        page.lineTop(left, tableTop, right, tableTop, 0.6F);
        page.lineTop(left, headerBottom, right, headerBottom, 0.4F);
        page.lineTop(left, tableBottom, right, tableBottom, 0.4F);
        page.lineTop(left, tableTop, left, tableBottom, 0.4F);
        page.lineTop(45, tableTop, 45, tableBottom, 0.4F);
        page.lineTop(345, tableTop, 345, tableBottom, 0.4F);
        page.lineTop(396, tableTop, 396, tableBottom, 0.4F);
        page.lineTop(433, tableTop, 433, tableBottom, 0.4F);
        page.lineTop(498, tableTop, 498, tableBottom, 0.4F);
        page.lineTop(right, tableTop, right, tableBottom, 0.4F);
    }

    private void addTotals(PdfTextPage page, Salesinvoice invoice, float tableBottom) {
        float topY = tableBottom + 12F;
        page.topTextBold(396, topY, 8, "Sub Total :");
        page.topRightTextBold(572, topY, 8, money(amount(invoice.getSubtotal())));
        page.topTextBold(396, topY + 18F, 8, "Discount Total :");
        page.topRightTextBold(572, topY + 18F, 8, money(amount(invoice.getTotaldiscount())));
        page.topTextBold(396, topY + 36F, 8, "Net Total :");
        page.topRightTextBold(572, topY + 36F, 8, money(amount(invoice.getNettotal())));
        page.topTextBold(396, topY + 54F, 8, "Advance Amount :");
        page.topRightTextBold(572, topY + 54F, 8, money(amount(invoice.getAdvancepayment())));
        page.topTextBold(396, topY + 72F, 8, "Paid Amount :");
        page.topRightTextBold(572, topY + 72F, 8, money(amount(invoice.getPaidamount())));
        page.topTextBold(396, topY + 90F, 8, "Balance Amount :");
        page.topRightTextBold(572, topY + 90F, 8, money(amount(invoice.getAmountowing())));
    }

    private void addFooter(PdfTextPage page) {
        page.topText(
            79,
            777,
            7,
            "Vehicle Service / Quick Wash / Accident Repairs / Wheel Alignment / Spare Parts/ Auto Paint & Materials / Lubricant Store / Hybrid"
        );
        page.topText(142, 789, 7, "Care / Auto AC Repairs / Mechanical Repairs / Recovery Service / EV Charging / Filling Station");
        page.topText(152, 807, 7, "NOTE: Cheques should be drawn in favour of \"Rathnaweera auto care (PVT) LTD\" Only.");
    }

    private String formatDate(Salesinvoice invoice) {
        if (invoice.getInvoicedate() == null) {
            return "";
        }
        return DATE_FORMATTER.format(invoice.getInvoicedate().atZone(ZoneId.systemDefault()));
    }

    private List<Map<String, Object>> readItemLines(Integer invoiceId) {
        LinkedHashMap<String, String> columns = new LinkedHashMap<>();
        columns.put("itemname", "itemname");
        columns.put("description", "description");
        columns.put("quantity", "quantity");
        columns.put("itemprice", "itemprice");
        columns.put("discount", "discount");
        columns.put("sellingprice", "sellingprice");
        columns.put("linetotal", "linetotal");
        return legacyInvoiceChildrenReadService.findByInvoiceId(
            "salesinvoicelines",
            List.of("invoiceid", "invoiceId", "invocieid"),
            columns,
            invoiceId
        );
    }

    private List<Map<String, Object>> readServiceLines(Integer invoiceId) {
        LinkedHashMap<String, String> columns = new LinkedHashMap<>();
        columns.put("serviceName", "servicename");
        columns.put("serviceDescription", "servicediscription");
        columns.put("value", "value");
        columns.put("discount", "discount");
        columns.put("servicePrice", "serviceprice");
        return legacyInvoiceChildrenReadService.findByInvoiceId("salesinvoiceservicechargeline", "invoiceid", columns, invoiceId);
    }

    private List<Map<String, Object>> readCommonServiceLines(Integer invoiceId) {
        LinkedHashMap<String, String> columns = new LinkedHashMap<>();
        columns.put("name", "name");
        columns.put("description", "description");
        columns.put("value", "value");
        columns.put("discount", "discount");
        columns.put("servicePrice", "serviceprice");
        return legacyInvoiceChildrenReadService.findByInvoiceId("saleinvoicecommonservicecharge", "invoiceid", columns, invoiceId);
    }

    private BigDecimal amount(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue()).setScale(2, RoundingMode.HALF_UP);
        }
        try {
            return new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException ex) {
            return BigDecimal.ZERO;
        }
    }

    private String money(BigDecimal amount) {
        return MONEY_FORMAT.format(amount);
    }

    private String value(Object value) {
        return value == null ? "" : value.toString();
    }

    private String stringValue(Object value) {
        return value == null ? "" : value.toString();
    }

    private Object valueOrFallback(Map<String, Object> values, String primary, String fallback) {
        Object value = values.get(primary);
        return value != null ? value : values.get(fallback);
    }

    private String text(String primary, String fallback) {
        return StringUtils.defaultIfBlank(primary, StringUtils.defaultString(fallback));
    }

    private String limit(String value, int length) {
        String safeValue = StringUtils.defaultString(value);
        return safeValue.length() <= length ? safeValue : safeValue.substring(0, length);
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
    }

    public record MobileAppInvoicePdfResult(Long invoiceId, String invoiceCode, String filePath) {}

    private static final class PdfTextPage {

        private final List<String> operations = new ArrayList<>();
        private PdfImage image;

        void text(float x, float y, int fontSize, String text) {
            text("F1", x, y, fontSize, text);
        }

        void textBold(float x, float y, int fontSize, String text) {
            text("F2", x, y, fontSize, text);
        }

        void text(String fontName, float x, float y, int fontSize, String text) {
            operations.add(String.format(Locale.ENGLISH, "BT /%s %d Tf %.2f %.2f Td (%s) Tj ET", fontName, fontSize, x, y, escape(text)));
        }

        void rightText(float rightX, float y, int fontSize, String text) {
            float estimatedWidth = StringUtils.defaultString(text).length() * fontSize * 0.48F;
            text(rightX - estimatedWidth, y, fontSize, text);
        }

        void imageTop(float x, float topY, float width, float height, PdfImage image) {
            this.image = image;
            float y = PAGE_HEIGHT - topY - height;
            operations.add(String.format(Locale.ENGLISH, "q %.2f 0 0 %.2f %.2f %.2f cm /Im1 Do Q", width, height, x, y));
        }

        void lineTop(float x1, float topY1, float x2, float topY2, float width) {
            float y1 = PAGE_HEIGHT - topY1;
            float y2 = PAGE_HEIGHT - topY2;
            operations.add(String.format(Locale.ENGLISH, "%.2f w %.2f %.2f m %.2f %.2f l S", width, x1, y1, x2, y2));
        }

        void topText(float x, float topY, int fontSize, String text) {
            text(x, PAGE_HEIGHT - topY - fontSize, fontSize, text);
        }

        void topTextBold(float x, float topY, int fontSize, String text) {
            textBold(x, PAGE_HEIGHT - topY - fontSize, fontSize, text);
        }

        void topRightText(float rightX, float topY, int fontSize, String text) {
            float estimatedWidth = StringUtils.defaultString(text).length() * fontSize * 0.48F;
            topText(rightX - estimatedWidth, topY, fontSize, text);
        }

        void topRightTextBold(float rightX, float topY, int fontSize, String text) {
            float estimatedWidth = StringUtils.defaultString(text).length() * fontSize * 0.5F;
            topTextBold(rightX - estimatedWidth, topY, fontSize, text);
        }

        byte[] toPdf() throws IOException {
            String content = String.join("\n", operations);
            byte[] contentBytes = content.getBytes(StandardCharsets.ISO_8859_1);
            boolean hasImage = image != null;

            List<byte[]> objects = new ArrayList<>();
            objects.add("<< /Type /Catalog /Pages 2 0 R >>".getBytes(StandardCharsets.ISO_8859_1));
            objects.add("<< /Type /Pages /Kids [3 0 R] /Count 1 >>".getBytes(StandardCharsets.ISO_8859_1));
            String xObject = hasImage ? " /XObject << /Im1 7 0 R >>" : "";
            objects.add(
                ("<< /Type /Page /Parent 2 0 R /MediaBox [0 0 " +
                    PAGE_WIDTH +
                    " " +
                    PAGE_HEIGHT +
                    "] /Resources << /Font << /F1 4 0 R /F2 5 0 R >>" +
                    xObject +
                    " >> /Contents 6 0 R >>").getBytes(StandardCharsets.ISO_8859_1)
            );
            objects.add("<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>".getBytes(StandardCharsets.ISO_8859_1));
            objects.add("<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica-Bold >>".getBytes(StandardCharsets.ISO_8859_1));
            objects.add(
                ("<< /Length " + contentBytes.length + " >>\nstream\n" + content + "\nendstream").getBytes(StandardCharsets.ISO_8859_1)
            );
            if (hasImage) {
                objects.add(image.toPdfObject());
            }

            ByteArrayOutputStream pdf = new ByteArrayOutputStream();
            List<Integer> offsets = new ArrayList<>();
            write(pdf, "%PDF-1.4\n");
            for (int i = 0; i < objects.size(); i++) {
                offsets.add(pdf.size());
                write(pdf, (i + 1) + " 0 obj\n");
                pdf.write(objects.get(i));
                write(pdf, "\nendobj\n");
            }

            int xrefOffset = pdf.size();
            write(pdf, "xref\n0 " + (objects.size() + 1) + "\n");
            write(pdf, "0000000000 65535 f \n");
            for (Integer offset : offsets) {
                write(pdf, String.format(Locale.ENGLISH, "%010d 00000 n \n", offset));
            }
            write(pdf, "trailer\n<< /Size " + (objects.size() + 1) + " /Root 1 0 R >>\n");
            write(pdf, "startxref\n" + xrefOffset + "\n%%EOF\n");
            return pdf.toByteArray();
        }

        private static String escape(String value) {
            return StringUtils.defaultString(value).replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)");
        }

        private static void write(ByteArrayOutputStream outputStream, String value) throws IOException {
            outputStream.write(value.getBytes(StandardCharsets.ISO_8859_1));
        }
    }

    private record PdfImage(int width, int height, byte[] rgbData) {
        static PdfImage fromClasspath(String resourcePath) throws IOException {
            try (InputStream inputStream = new ClassPathResource(resourcePath).getInputStream()) {
                BufferedImage image = ImageIO.read(inputStream);
                if (image == null) {
                    throw new IOException("Unable to read invoice image resource " + resourcePath);
                }
                ByteArrayOutputStream raw = new ByteArrayOutputStream();
                for (int y = 0; y < image.getHeight(); y++) {
                    for (int x = 0; x < image.getWidth(); x++) {
                        int rgb = image.getRGB(x, y);
                        raw.write((rgb >> 16) & 0xFF);
                        raw.write((rgb >> 8) & 0xFF);
                        raw.write(rgb & 0xFF);
                    }
                }
                return new PdfImage(image.getWidth(), image.getHeight(), raw.toByteArray());
            }
        }

        byte[] toPdfObject() throws IOException {
            ByteArrayOutputStream compressed = new ByteArrayOutputStream();
            try (DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(compressed)) {
                deflaterOutputStream.write(rgbData);
            }
            byte[] imageBytes = compressed.toByteArray();
            String header =
                "<< /Type /XObject /Subtype /Image /Width " +
                width +
                " /Height " +
                height +
                " /ColorSpace /DeviceRGB /BitsPerComponent 8 /Filter /FlateDecode /Length " +
                imageBytes.length +
                " >>\nstream\n";

            ByteArrayOutputStream object = new ByteArrayOutputStream();
            object.write(header.getBytes(StandardCharsets.ISO_8859_1));
            object.write(imageBytes);
            object.write("\nendstream".getBytes(StandardCharsets.ISO_8859_1));
            return object.toByteArray();
        }
    }
}
