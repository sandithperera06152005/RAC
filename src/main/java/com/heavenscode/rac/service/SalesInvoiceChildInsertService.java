package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.SaleInvoiceCommonServiceCharge;
import com.heavenscode.rac.domain.SalesInvoiceLines;
import com.heavenscode.rac.domain.SalesInvoiceServiceChargeLine;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SalesInvoiceChildInsertService {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceChildInsertService.class);

    private final JdbcTemplate jdbcTemplate;

    public SalesInvoiceChildInsertService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SalesInvoiceLines insertInvoiceLine(SalesInvoiceLines entity) {
        try {
            String tableName = resolveQualifiedTableName("salesinvoicelines");
            hydrateItemIdFromItemCode(entity);
            if (invoiceLineAlreadyExists(tableName, entity.getInvoiceid(), entity.getLineid())) {
                updateExistingInvoiceLineItemId(tableName, entity);
                LOG.debug(
                    "Skipping duplicate SalesInvoiceLine insert for invoiceId={} lineId={}",
                    entity.getInvoiceid(),
                    entity.getLineid()
                );
                return entity;
            }
            GeneratedKey generatedKey = insertWithDetectedKey(
                tableName,
                mapOf(
                    "invoiceid",
                    entity.getInvoiceid(),
                    "invocieid",
                    entity.getInvoiceid(),
                    "invoice_id",
                    entity.getInvoiceid(),
                    "lineid",
                    entity.getLineid(),
                    "line_id",
                    entity.getLineid(),
                    "itemid",
                    entity.getItemid(),
                    "item_id",
                    entity.getItemid(),
                    "itemcode",
                    entity.getItemcode(),
                    "item_code",
                    entity.getItemcode(),
                    "itemname",
                    entity.getItemname(),
                    "item_name",
                    entity.getItemname(),
                    "description",
                    entity.getDescription(),
                    "unitofmeasurement",
                    entity.getUnitofmeasurement(),
                    "unit_of_measurement",
                    entity.getUnitofmeasurement(),
                    "quantity",
                    entity.getQuantity(),
                    "itemcost",
                    entity.getItemcost(),
                    "item_cost",
                    entity.getItemcost(),
                    "itemprice",
                    entity.getItemprice(),
                    "item_price",
                    entity.getItemprice(),
                    "discount",
                    entity.getDiscount(),
                    "tax",
                    entity.getTax(),
                    "sellingprice",
                    entity.getSellingprice(),
                    "selling_price",
                    entity.getSellingprice(),
                    "linetotal",
                    entity.getLinetotal(),
                    "line_total",
                    entity.getLinetotal(),
                    "lmu",
                    entity.getLmu(),
                    "lmd",
                    toTimestamp(entity.getLmd()),
                    "nbt",
                    entity.getNbt(),
                    "vat",
                    entity.getVat()
                )
            );
            applyGeneratedKey(entity, generatedKey);
            return entity;
        } catch (DataAccessException ex) {
            throw rethrowWithSqlDetails("salesinvoicelines", entity, ex);
        }
    }

    private void hydrateCommonServiceChargeDetails(SaleInvoiceCommonServiceCharge entity) {
        if (entity.getOptionId() == null || entity.getOptionId() <= 0) {
            return;
        }
        if (entity.getDiscount() == null) {
            entity.setDiscount(0.0f);
        }
        // If we already have both, no need to hydrate
        if (entity.getMainId() != null && entity.getCode() != null && !entity.getCode().isBlank()) {
            return;
        }

        try {
            String optionTable = resolveQualifiedTableName("commonserviceoption");
            Map<String, String> columns = getActualColumns(optionTable);
            String mainIdColumn = firstAvailableColumn(columns, "mainid", "main_id");
            String codeColumn = firstAvailableColumn(columns, "code");
            String priceColumn = firstAvailableColumn(columns, "value", "price");

            if (mainIdColumn == null && codeColumn == null && priceColumn == null) {
                return;
            }

            List<Map<String, Object>> detailsList = jdbcTemplate.queryForList(
                "SELECT " +
                (mainIdColumn != null ? bracket(mainIdColumn) : "NULL") +
                " as mainid, " +
                (codeColumn != null ? bracket(codeColumn) : "NULL") +
                " as code, " +
                (priceColumn != null ? bracket(priceColumn) : "NULL") +
                " as price " +
                " FROM " +
                optionTable +
                " WHERE id = ?",
                entity.getOptionId()
            );

            if (!detailsList.isEmpty()) {
                Map<String, Object> details = detailsList.get(0);
                if (entity.getMainId() == null && details.get("mainid") != null) {
                    entity.setMainId(((Number) details.get("mainid")).intValue());
                }
                if ((entity.getCode() == null || entity.getCode().isBlank()) && details.get("code") != null) {
                    entity.setCode(details.get("code").toString());
                }
                if (details.get("price") != null) {
                    entity.setServicePrice(((Number) details.get("price")).floatValue());
                }
            }
        } catch (Exception ex) {
            LOG.warn("Unable to resolve mainId and code for optionId '{}'", entity.getOptionId(), ex);
        }
    }

    private void hydrateItemIdFromItemCode(SalesInvoiceLines entity) {
        if (entity.getItemid() != null) {
            return;
        }
        if (entity.getItemcode() == null || entity.getItemcode().isBlank()) {
            return;
        }

        try {
            String inventoryTable = resolveQualifiedTableName("inventory");
            Map<String, String> inventoryColumns = getActualColumns(inventoryTable);
            String idColumn = firstAvailableColumn(inventoryColumns, "id");
            String codeColumn = firstAvailableColumn(inventoryColumns, "code", "itemcode", "item_code");
            if (idColumn == null || codeColumn == null) {
                return;
            }

            Integer itemId = jdbcTemplate.query(
                "SELECT TOP 1 " +
                bracket(idColumn) +
                " FROM " +
                inventoryTable +
                " WHERE " +
                bracket(codeColumn) +
                " IS NOT NULL AND UPPER(LTRIM(RTRIM(" +
                bracket(codeColumn) +
                "))) = UPPER(LTRIM(RTRIM(?)))",
                rs -> rs.next() ? rs.getInt(1) : null,
                entity.getItemcode()
            );
            if (itemId != null) {
                entity.setItemid(itemId);
            }
        } catch (Exception ex) {
            LOG.warn("Unable to resolve item id for code '{}'", entity.getItemcode(), ex);
        }
    }

    private boolean invoiceLineAlreadyExists(String qualifiedTableName, Integer invoiceId, Integer lineId) {
        if (invoiceId == null || lineId == null) {
            return false;
        }

        Map<String, String> columns = getActualColumns(qualifiedTableName);
        String invoiceColumn = firstAvailableColumn(columns, "invoiceid", "invocieid", "invoice_id");
        String lineColumn = firstAvailableColumn(columns, "lineid", "line_id");
        if (invoiceColumn == null || lineColumn == null) {
            return false;
        }

        Integer existingCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(1) FROM " + qualifiedTableName + " WHERE " + bracket(invoiceColumn) + " = ? AND " + bracket(lineColumn) + " = ?",
            Integer.class,
            invoiceId,
            lineId
        );
        return existingCount != null && existingCount > 0;
    }

    private void updateExistingInvoiceLineItemId(String qualifiedTableName, SalesInvoiceLines entity) {
        if (entity.getInvoiceid() == null || entity.getLineid() == null || entity.getItemid() == null) {
            return;
        }

        Map<String, String> columns = getActualColumns(qualifiedTableName);
        String invoiceColumn = firstAvailableColumn(columns, "invoiceid", "invocieid", "invoice_id");
        String lineColumn = firstAvailableColumn(columns, "lineid", "line_id");
        String itemIdColumn = firstAvailableColumn(columns, "itemid", "item_id");
        if (invoiceColumn == null || lineColumn == null || itemIdColumn == null) {
            return;
        }

        jdbcTemplate.update(
            "UPDATE " +
            qualifiedTableName +
            " SET " +
            bracket(itemIdColumn) +
            " = ? WHERE " +
            bracket(invoiceColumn) +
            " = ? AND " +
            bracket(lineColumn) +
            " = ?",
            entity.getItemid(),
            entity.getInvoiceid(),
            entity.getLineid()
        );
    }

    private String firstAvailableColumn(Map<String, String> actualColumns, String... candidates) {
        for (String candidate : candidates) {
            String actual = actualColumns.get(candidate.toLowerCase());
            if (actual != null) {
                return actual;
            }
        }
        return null;
    }

    private void hydrateServiceChargeLineDetails(SalesInvoiceServiceChargeLine entity) {
        // If optionId is missing or looks like a placeholder (index-based), try to resolve it via serviceName
        if (entity.getServiceName() != null && !entity.getServiceName().isBlank()) {
            try {
                String optionTable = resolveQualifiedTableName("billingserviceoption");
                List<Map<String, Object>> idList = jdbcTemplate.queryForList(
                    "SELECT id FROM " + optionTable + " WHERE servicename = ?",
                    entity.getServiceName()
                );
                if (!idList.isEmpty()) {
                    entity.setOptionId(((Number) idList.get(0).get("id")).intValue());
                }
            } catch (Exception ex) {
                LOG.warn("Unable to resolve optionId for serviceName '{}'", entity.getServiceName(), ex);
            }
        }

        if (entity.getOptionId() == null || entity.getOptionId() <= 0) {
            return;
        }

        // Handle Discount NULL
        if (entity.getDiscount() == null) {
            entity.setDiscount(0.0f);
        }

        try {
            String optionTable = resolveQualifiedTableName("billingserviceoption");
            Map<String, String> columns = getActualColumns(optionTable);
            String descColumn = firstAvailableColumn(columns, "servicediscription", "service_description", "description");
            String priceColumn = firstAvailableColumn(columns, "price", "value", "serviceprice", "service_price");

            if (descColumn == null && priceColumn == null) {
                return;
            }

            List<Map<String, Object>> detailsList = jdbcTemplate.queryForList(
                "SELECT " +
                (descColumn != null ? bracket(descColumn) : "NULL") +
                " as description, " +
                (priceColumn != null ? bracket(priceColumn) : "NULL") +
                " as price " +
                " FROM " +
                optionTable +
                " WHERE id = ?",
                entity.getOptionId()
            );

            if (!detailsList.isEmpty()) {
                Map<String, Object> details = detailsList.get(0);
                if (entity.getServiceDescription() == null || entity.getServiceDescription().isBlank()) {
                    if (details.get("description") != null) {
                        entity.setServiceDescription(details.get("description").toString());
                    }
                }
                // Set ServicePrice to original price from master if found
                if (details.get("price") != null) {
                    entity.setServicePrice(((Number) details.get("price")).floatValue());
                }
            }
        } catch (Exception ex) {
            LOG.warn("Unable to resolve details for optionId '{}' in billingserviceoption", entity.getOptionId(), ex);
        }
    }

    public SalesInvoiceServiceChargeLine insertServiceChargeLine(SalesInvoiceServiceChargeLine entity) {
        try {
            hydrateServiceChargeLineDetails(entity);
            String tableName = resolveQualifiedTableName("salesinvoiceservicechargeline");
            GeneratedKey generatedKey = insertWithDetectedKey(
                tableName,
                mapOf(
                    "invoiceid",
                    entity.getInvoiceId(),
                    "invoice_id",
                    entity.getInvoiceId(),
                    "lineid",
                    entity.getLineId(),
                    "line_id",
                    entity.getLineId(),
                    "optionid",
                    entity.getOptionId(),
                    "option_id",
                    entity.getOptionId(),
                    "servicename",
                    entity.getServiceName(),
                    "service_name",
                    entity.getServiceName(),
                    "servicediscription",
                    entity.getServiceDescription(),
                    "service_description",
                    entity.getServiceDescription(),
                    "value",
                    entity.getValue(),
                    "iscustomersrvice",
                    entity.getIsCustomerService(),
                    "is_customer_service",
                    entity.getIsCustomerService(),
                    "discount",
                    entity.getDiscount(),
                    "serviceprice",
                    entity.getServicePrice(),
                    "service_price",
                    entity.getServicePrice()
                )
            );
            applyGeneratedKey(entity, generatedKey);
            return entity;
        } catch (DataAccessException ex) {
            throw rethrowWithSqlDetails("salesinvoiceservicechargeline", entity, ex);
        }
    }

    public SaleInvoiceCommonServiceCharge insertCommonServiceCharge(SaleInvoiceCommonServiceCharge entity) {
        try {
            hydrateCommonServiceChargeDetails(entity);
            String tableName = resolveQualifiedTableName("saleinvoicecommonservicecharge");
            GeneratedKey generatedKey = insertWithDetectedKey(
                tableName,
                mapOf(
                    "invoiceid",
                    entity.getInvoiceId(),
                    "invoice_id",
                    entity.getInvoiceId(),
                    "lineid",
                    entity.getLineId(),
                    "line_id",
                    entity.getLineId(),
                    "optionid",
                    entity.getOptionId(),
                    "option_id",
                    entity.getOptionId(),
                    "mainid",
                    entity.getMainId(),
                    "main_id",
                    entity.getMainId(),
                    "code",
                    entity.getCode(),
                    "name",
                    entity.getName(),
                    "description",
                    entity.getDescription(),
                    "value",
                    entity.getValue(),
                    "discount",
                    entity.getDiscount(),
                    "serviceprice",
                    entity.getServicePrice(),
                    "service_price",
                    entity.getServicePrice()
                )
            );
            applyGeneratedKey(entity, generatedKey);
            return entity;
        } catch (DataAccessException ex) {
            throw rethrowWithSqlDetails("saleinvoicecommonservicecharge", entity, ex);
        }
    }

    private GeneratedKey insertWithDetectedKey(String qualifiedTableName, Map<String, Object> requestedValues) {
        Map<String, String> actualColumns = getActualColumns(qualifiedTableName);
        String generatedColumn = resolveGeneratedKeyColumn(actualColumns);
        Object explicitGeneratedValue = generatedColumn != null ? requestedValues.get(generatedColumn) : null;

        LinkedHashMap<String, Object> insertValues = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : requestedValues.entrySet()) {
            String actualColumn = actualColumns.get(entry.getKey().toLowerCase());
            if (actualColumn != null && (!actualColumn.equalsIgnoreCase(generatedColumn) || explicitGeneratedValue != null)) {
                insertValues.put(actualColumn, entry.getValue());
            }
        }

        if (insertValues.isEmpty()) {
            throw new IllegalStateException("No matching columns found for table " + qualifiedTableName);
        }

        String actualGeneratedColumn = generatedColumn != null ? actualColumns.get(generatedColumn) : null;
        if (actualGeneratedColumn != null && explicitGeneratedValue != null) {
            String sql =
                "INSERT INTO " +
                qualifiedTableName +
                " (" +
                bracketedColumns(insertValues.keySet()) +
                ") VALUES (" +
                placeholders(insertValues.size()) +
                ")";
            jdbcTemplate.update(sql, insertValues.values().toArray());
            return new GeneratedKey(actualGeneratedColumn, toLong(explicitGeneratedValue));
        }

        if (actualGeneratedColumn != null && isIdentityColumn(qualifiedTableName, actualGeneratedColumn)) {
            String sql =
                "INSERT INTO " +
                qualifiedTableName +
                " (" +
                bracketedColumns(insertValues.keySet()) +
                ") OUTPUT INSERTED." +
                bracket(actualGeneratedColumn) +
                " VALUES (" +
                placeholders(insertValues.size()) +
                ")";
            Long generatedValue = jdbcTemplate.queryForObject(sql, Long.class, insertValues.values().toArray());
            return new GeneratedKey(actualGeneratedColumn, generatedValue);
        }

        if (actualGeneratedColumn != null) {
            Long nextValue = jdbcTemplate.queryForObject(
                "SELECT COALESCE(MAX(" + bracket(actualGeneratedColumn) + "), 0) + 1 FROM " + qualifiedTableName,
                Long.class
            );
            insertValues.put(actualGeneratedColumn, nextValue);
            String sql =
                "INSERT INTO " +
                qualifiedTableName +
                " (" +
                bracketedColumns(insertValues.keySet()) +
                ") VALUES (" +
                placeholders(insertValues.size()) +
                ")";
            jdbcTemplate.update(sql, insertValues.values().toArray());
            return new GeneratedKey(actualGeneratedColumn, nextValue);
        }

        String sql =
            "INSERT INTO " +
            qualifiedTableName +
            " (" +
            bracketedColumns(insertValues.keySet()) +
            ") VALUES (" +
            placeholders(insertValues.size()) +
            ")";
        jdbcTemplate.update(sql, insertValues.values().toArray());
        return new GeneratedKey(null, null);
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

    private boolean isIdentityColumn(String qualifiedTableName, String columnName) {
        List<Integer> matches = jdbcTemplate.queryForList(
            "SELECT c.is_identity FROM sys.columns c WHERE c.object_id = OBJECT_ID(?) AND c.name = ?",
            Integer.class,
            unquote(qualifiedTableName),
            columnName
        );
        return !matches.isEmpty() && matches.get(0) != null && matches.get(0) == 1;
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

    private String resolveGeneratedKeyColumn(Map<String, String> actualColumns) {
        if (actualColumns.containsKey("id")) {
            return "id";
        }
        if (actualColumns.containsKey("lineid")) {
            return "lineid";
        }
        if (actualColumns.containsKey("line_id")) {
            return "line_id";
        }
        return null;
    }

    private String placeholders(int count) {
        return String.join(", ", java.util.Collections.nCopies(count, "?"));
    }

    private String bracketedColumns(Iterable<String> columns) {
        return String.join(", ", toList(columns).stream().map(this::bracket).toList());
    }

    private String unquote(String qualifiedTableName) {
        return qualifiedTableName.replace("[", "").replace("]", "");
    }

    private String schemaName(String qualifiedTableName) {
        String[] parts = unquote(qualifiedTableName).split("\\.");
        return parts[0];
    }

    private String tableNameOnly(String qualifiedTableName) {
        String[] parts = unquote(qualifiedTableName).split("\\.");
        return parts[1];
    }

    private String bracket(String columnName) {
        return "[" + columnName + "]";
    }

    private List<String> toList(Iterable<String> columns) {
        List<String> values = new ArrayList<>();
        for (String column : columns) {
            values.add(column);
        }
        return values;
    }

    private LinkedHashMap<String, Object> mapOf(Object... keyValues) {
        LinkedHashMap<String, Object> values = new LinkedHashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            values.put((String) keyValues[i], keyValues[i + 1]);
        }
        return values;
    }

    private void applyGeneratedKey(SalesInvoiceLines entity, GeneratedKey generatedKey) {
        if (generatedKey.value == null || generatedKey.columnName == null) {
            return;
        }
        if ("id".equalsIgnoreCase(generatedKey.columnName)) {
            entity.setId(generatedKey.value);
        } else if ("lineid".equalsIgnoreCase(generatedKey.columnName) || "line_id".equalsIgnoreCase(generatedKey.columnName)) {
            entity.setLineid(generatedKey.value.intValue());
        }
    }

    private void applyGeneratedKey(SalesInvoiceServiceChargeLine entity, GeneratedKey generatedKey) {
        if (generatedKey.value == null || generatedKey.columnName == null) {
            return;
        }
        if ("id".equalsIgnoreCase(generatedKey.columnName)) {
            entity.setId(generatedKey.value);
        } else if ("lineid".equalsIgnoreCase(generatedKey.columnName) || "line_id".equalsIgnoreCase(generatedKey.columnName)) {
            entity.setLineId(generatedKey.value.intValue());
        }
    }

    private void applyGeneratedKey(SaleInvoiceCommonServiceCharge entity, GeneratedKey generatedKey) {
        if (generatedKey.value == null || generatedKey.columnName == null) {
            return;
        }
        if ("id".equalsIgnoreCase(generatedKey.columnName)) {
            entity.setId(generatedKey.value);
        } else if ("lineid".equalsIgnoreCase(generatedKey.columnName) || "line_id".equalsIgnoreCase(generatedKey.columnName)) {
            entity.setLineId(generatedKey.value.intValue());
        }
    }

    private Timestamp toTimestamp(Instant instant) {
        return instant != null ? Timestamp.from(instant) : null;
    }

    private Long toLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        return Long.valueOf(value.toString());
    }

    private IllegalStateException rethrowWithSqlDetails(String tableName, Object payload, DataAccessException ex) {
        String detail = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        LOG.error("Insert failed for {} with payload {}", tableName, payload, ex);
        return new IllegalStateException("Insert into " + tableName + " failed: " + detail, ex);
    }

    private record GeneratedKey(String columnName, Long value) {}
}
