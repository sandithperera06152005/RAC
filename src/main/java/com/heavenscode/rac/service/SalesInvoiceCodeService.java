package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Salesinvoice;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SalesInvoiceCodeService {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceCodeService.class);

    private static final String STANDARD_SETTINGS_KEY = "SALESINVOICECODE";
    private static final String VAT_SETTINGS_KEY = "SALESINVOICECODEVAT";
    private static final String VAT_CODE_MIDDLE = "RAC1";
    private static final String STANDARD_INVOICE_CODE_PREFIX = "SI";
    private static final int DEFAULT_STANDARD_CODE_PADDING = 5;
    private static final DateTimeFormatter VAT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyMMM", Locale.ENGLISH);

    private final JdbcTemplate jdbcTemplate;
    private final SystemSettingsCodeSequenceService systemSettingsCodeSequenceService;

    public SalesInvoiceCodeService(JdbcTemplate jdbcTemplate, SystemSettingsCodeSequenceService systemSettingsCodeSequenceService) {
        this.jdbcTemplate = jdbcTemplate;
        this.systemSettingsCodeSequenceService = systemSettingsCodeSequenceService;
    }

    @Transactional(readOnly = true)
    public String peekNextVatInvoiceCode() {
        int currentCode = readCurrentVatSequence(false, null, null);
        return formatVatInvoiceCode(currentCode, Instant.now());
    }

    @Transactional(readOnly = true)
    public String peekNextStandardInvoiceCode() {
        if (systemSettingsCodeSequenceService.systemSettingsTableExists()) {
            return systemSettingsCodeSequenceService.peekNextValue(STANDARD_SETTINGS_KEY);
        }

        StandardCodeInfo maxStandardCode = readMaxStandardSequence(false);
        return formatStandardCode(maxStandardCode.sequenceNumber() + 1, maxStandardCode.paddingLength());
    }

    public void assignInvoiceCode(Salesinvoice salesinvoice) {
        if (Boolean.TRUE.equals(salesinvoice.getIsvatinvoice())) {
            assignVatInvoiceCode(salesinvoice);
        } else {
            assignStandardInvoiceCode(salesinvoice);
        }
    }

    public void assignVatInvoiceCode(Salesinvoice salesinvoice) {
        if (!Boolean.TRUE.equals(salesinvoice.getIsvatinvoice())) {
            return;
        }

        int currentCode = readCurrentVatSequence(true, salesinvoice.getLmu(), salesinvoice.getLmd());
        Instant invoiceDate = salesinvoice.getInvoicedate() != null ? salesinvoice.getInvoicedate() : Instant.now();
        salesinvoice.setCode(formatVatInvoiceCode(currentCode, invoiceDate));
    }

    public void assignStandardInvoiceCode(Salesinvoice salesinvoice) {
        if (Boolean.TRUE.equals(salesinvoice.getIsvatinvoice())) {
            return;
        }

        if (systemSettingsCodeSequenceService.systemSettingsTableExists()) {
            salesinvoice.setCode(
                systemSettingsCodeSequenceService.consumeNextValue(STANDARD_SETTINGS_KEY, salesinvoice.getLmu(), salesinvoice.getLmd())
            );
        } else {
            StandardCodeInfo maxStandardCode = readMaxStandardSequence(true);
            salesinvoice.setCode(formatStandardCode(maxStandardCode.sequenceNumber() + 1, maxStandardCode.paddingLength()));
        }
    }

    String formatVatInvoiceCode(int sequenceNumber, Instant invoiceDate) {
        LocalDate date = invoiceDate.atZone(ZoneId.systemDefault()).toLocalDate();
        return (date.format(VAT_DATE_FORMAT) + "_" + VAT_CODE_MIDDLE + "_" + sequenceNumber).toUpperCase(Locale.ENGLISH);
    }

    private String formatStandardCode(int sequenceNumber, int paddingLength) {
        return STANDARD_INVOICE_CODE_PREFIX + String.format(Locale.ENGLISH, "%0" + paddingLength + "d", sequenceNumber);
    }

    private StandardCodeInfo readMaxStandardSequence(boolean withLock) {
        String qualifiedTableName = resolveQualifiedTableName("salesinvoice");
        String lockHint = withLock ? " WITH (UPDLOCK, HOLDLOCK)" : "";
        String sql =
            "SELECT TOP 1 [code] FROM " +
            qualifiedTableName +
            lockHint +
            " WHERE [code] LIKE ? " +
            "ORDER BY TRY_CAST(SUBSTRING([code], 3, LEN([code])) AS INT) DESC";

        List<String> codes = jdbcTemplate.queryForList(sql, String.class, STANDARD_INVOICE_CODE_PREFIX + "[0-9]%");
        if (codes.isEmpty()) {
            return new StandardCodeInfo(0, DEFAULT_STANDARD_CODE_PADDING);
        }

        String numericPart = codes.get(0).substring(STANDARD_INVOICE_CODE_PREFIX.length());
        return new StandardCodeInfo(Integer.parseInt(numericPart), numericPart.length());
    }

    private record StandardCodeInfo(int sequenceNumber, int paddingLength) {}

    private int readCurrentVatSequence(boolean incrementCounter, Integer lmu, Instant lmd) {
        if (systemSettingsCodeSequenceService.systemSettingsTableExists()) {
            String currentValue = incrementCounter
                ? systemSettingsCodeSequenceService.consumeNextValue(VAT_SETTINGS_KEY, lmu, lmd)
                : systemSettingsCodeSequenceService.peekNextValue(VAT_SETTINGS_KEY);
            return systemSettingsCodeSequenceService.extractSequenceNumber(currentValue);
        }

        LOG.warn("SystemSettings table not found; deriving VAT invoice sequence from salesinvoice codes");
        int maxUsed = readSequenceFromExistingInvoices();
        return maxUsed + 1;
    }

    private int readSequenceFromExistingInvoices() {
        String qualifiedTableName = resolveQualifiedTableName("salesinvoice");
        String sql =
            "SELECT COALESCE(MAX(TRY_CAST(" +
            "CASE " +
            "WHEN CHARINDEX('_" +
            VAT_CODE_MIDDLE +
            "_', [code]) > 0 " +
            "THEN SUBSTRING([code], CHARINDEX('_" +
            VAT_CODE_MIDDLE +
            "_', [code]) + 6, LEN([code])) " +
            "ELSE SUBSTRING([code], PATINDEX('%" +
            VAT_CODE_MIDDLE +
            "[0-9]%', [code]) + 4, LEN([code])) " +
            "END AS INT)), 0) " +
            "FROM " +
            qualifiedTableName +
            " WHERE [code] LIKE ?";

        Integer maxNumber = jdbcTemplate.queryForObject(sql, Integer.class, "%" + VAT_CODE_MIDDLE + "%");
        return maxNumber == null ? 0 : maxNumber;
    }

    private String resolveQualifiedTableName(String tableName) {
        List<String> tableNames = jdbcTemplate.queryForList(
            "SELECT TOP 1 QUOTENAME(TABLE_SCHEMA) + '.' + QUOTENAME(TABLE_NAME) FROM INFORMATION_SCHEMA.TABLES WHERE LOWER(TABLE_NAME) = ?",
            String.class,
            tableName.toLowerCase()
        );

        if (tableNames.isEmpty()) {
            throw new IllegalStateException("Table " + tableName + " was not found");
        }

        return tableNames.get(0);
    }
}
