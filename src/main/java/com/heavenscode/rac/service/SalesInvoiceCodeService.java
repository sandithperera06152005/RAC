package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Salesinvoice;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SalesInvoiceCodeService {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceCodeService.class);

    private static final String VAT_SETTINGS_KEY = "SALESINVOICECODEVAT";
    private static final String VAT_INTERNAL_PREFIX = "SIV";
    private static final String VAT_CODE_MIDDLE = "RAC1";
    private static final String STANDARD_INVOICE_CODE_PREFIX = "SI";
    private static final int DEFAULT_STANDARD_CODE_PADDING = 5;
    private static final DateTimeFormatter VAT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyMMM", Locale.ENGLISH);

    private final JdbcTemplate jdbcTemplate;

    public SalesInvoiceCodeService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public String peekNextVatInvoiceCode() {
        int currentCode = readCurrentVatSequence(false);
        return formatVatInvoiceCode(currentCode, Instant.now());
    }

    @Transactional(readOnly = true)
    public String peekNextStandardInvoiceCode() {
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

        int currentCode = readCurrentVatSequence(true);
        Instant invoiceDate = salesinvoice.getInvoicedate() != null ? salesinvoice.getInvoicedate() : Instant.now();
        salesinvoice.setCode(formatVatInvoiceCode(currentCode, invoiceDate));
    }

    public void assignStandardInvoiceCode(Salesinvoice salesinvoice) {
        if (Boolean.TRUE.equals(salesinvoice.getIsvatinvoice())) {
            return;
        }

        StandardCodeInfo maxStandardCode = readMaxStandardSequence(true);
        salesinvoice.setCode(formatStandardCode(maxStandardCode.sequenceNumber() + 1, maxStandardCode.paddingLength()));
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

    private int readCurrentVatSequence(boolean incrementCounter) {
        if (systemSettingsTableExists()) {
            return readSequenceFromSystemSettings(incrementCounter);
        }

        LOG.warn("SystemSettings table not found; deriving VAT invoice sequence from salesinvoice codes");
        int maxUsed = readSequenceFromExistingInvoices();
        return maxUsed + 1;
    }

    private int readSequenceFromSystemSettings(boolean incrementCounter) {
        String qualifiedTableName = resolveQualifiedTableName("systemsettings");
        Map<String, String> columns = getActualColumns(qualifiedTableName);
        String keyColumn = resolveColumn(columns, "key");
        String nextValueColumn = resolveColumn(columns, "nextvalue");
        String lastValueColumn = resolveColumn(columns, "lastvalue");

        String selectSql =
            "SELECT " +
            bracket(nextValueColumn) +
            " FROM " +
            qualifiedTableName +
            " WITH (UPDLOCK, ROWLOCK) WHERE " +
            bracket(keyColumn) +
            " = ?";

        String nextValue;
        try {
            nextValue = jdbcTemplate.queryForObject(selectSql, String.class, VAT_SETTINGS_KEY);
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalStateException("SystemSettings row not found for key " + VAT_SETTINGS_KEY, ex);
        }

        int currentCode = extractSequenceNumber(nextValue);
        if (!incrementCounter) {
            return currentCode;
        }

        int nextCode = currentCode + 1;
        String updateSql =
            "UPDATE " +
            qualifiedTableName +
            " SET " +
            bracket(lastValueColumn) +
            " = ?, " +
            bracket(nextValueColumn) +
            " = ? WHERE " +
            bracket(keyColumn) +
            " = ?";

        jdbcTemplate.update(updateSql, VAT_INTERNAL_PREFIX + currentCode, VAT_INTERNAL_PREFIX + nextCode, VAT_SETTINGS_KEY);

        return currentCode;
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

    private int extractSequenceNumber(String storedValue) {
        if (storedValue == null || storedValue.isBlank()) {
            return 0;
        }

        String numericPart = storedValue.startsWith(VAT_INTERNAL_PREFIX)
            ? storedValue.substring(VAT_INTERNAL_PREFIX.length())
            : storedValue.replaceAll("\\D+", "");

        if (numericPart.isBlank()) {
            return 0;
        }

        return Integer.parseInt(numericPart);
    }

    private boolean systemSettingsTableExists() {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE LOWER(TABLE_NAME) = ?",
            Integer.class,
            "systemsettings"
        );
        return count != null && count > 0;
    }

    private String resolveColumn(Map<String, String> columns, String logicalName) {
        String actualColumn = columns.get(logicalName);
        if (actualColumn == null) {
            throw new IllegalStateException("Column " + logicalName + " was not found on SystemSettings");
        }
        return actualColumn;
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

    private Map<String, String> getActualColumns(String qualifiedTableName) {
        return jdbcTemplate
            .queryForList(
                "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?",
                String.class,
                schemaName(qualifiedTableName),
                tableNameOnly(qualifiedTableName)
            )
            .stream()
            .collect(Collectors.toMap(column -> column.toLowerCase(), column -> column, (left, right) -> left, LinkedHashMap::new));
    }

    private String schemaName(String qualifiedTableName) {
        String[] parts = unquote(qualifiedTableName).split("\\.", 2);
        return parts.length == 2 ? parts[0] : "dbo";
    }

    private String tableNameOnly(String qualifiedTableName) {
        String[] parts = unquote(qualifiedTableName).split("\\.", 2);
        return parts.length == 2 ? parts[1] : parts[0];
    }

    private String unquote(String qualifiedTableName) {
        return qualifiedTableName.replace("[", "").replace("]", "");
    }

    private String bracket(String columnName) {
        return "[" + columnName + "]";
    }
}
