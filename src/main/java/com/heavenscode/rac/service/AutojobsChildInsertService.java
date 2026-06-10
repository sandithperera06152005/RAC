package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Autojobsaleinvoicecommonservicecharge;
import com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline;
import com.heavenscode.rac.domain.Autojobsinvoicelinebatches;
import com.heavenscode.rac.domain.Autojobsinvoicelines;
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
public class AutojobsChildInsertService {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsChildInsertService.class);

    private final JdbcTemplate jdbcTemplate;

    public AutojobsChildInsertService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Autojobsalesinvoiceservicechargeline insertServiceChargeLine(Autojobsalesinvoiceservicechargeline entity) {
        try {
            String tableName = resolveQualifiedTableName("autojobsalesinvoiceservicechargeline");
            GeneratedKey generatedKey = insertWithDetectedKey(
                tableName,
                mapOf(
                    "invoiceid",
                    entity.getInvoiceid(),
                    "lineid",
                    entity.getLineid(),
                    "optionid",
                    entity.getOptionid(),
                    "servicename",
                    entity.getServicename(),
                    "servicediscription",
                    entity.getServicediscription(),
                    "value",
                    entity.getValue(),
                    "addedbyid",
                    entity.getAddedbyid(),
                    "iscustomersrvice",
                    entity.getIscustomersrvice(),
                    "discount",
                    entity.getDiscount(),
                    "serviceprice",
                    entity.getServiceprice()
                )
            );
            applyGeneratedKey(entity, generatedKey);
            return entity;
        } catch (DataAccessException ex) {
            throw rethrowWithSqlDetails("autojobsalesinvoiceservicechargeline", entity, ex);
        }
    }

    public Autojobsaleinvoicecommonservicecharge insertCommonServiceCharge(Autojobsaleinvoicecommonservicecharge entity) {
        try {
            String tableName = resolveQualifiedTableName("autojobsaleinvoicecommonservicecharge");
            GeneratedKey generatedKey = insertWithDetectedKey(
                tableName,
                mapOf(
                    "invoiceid",
                    entity.getInvoiceid(),
                    "lineid",
                    entity.getLineid(),
                    "optionid",
                    entity.getOptionid(),
                    "mainid",
                    entity.getMainid(),
                    "code",
                    entity.getCode(),
                    "name",
                    entity.getName(),
                    "description",
                    entity.getDescription(),
                    "value",
                    entity.getValue(),
                    "addedbyid",
                    entity.getAddedbyid(),
                    "discount",
                    entity.getDiscount(),
                    "serviceprice",
                    entity.getServiceprice()
                )
            );
            applyGeneratedKey(entity, generatedKey);
            return entity;
        } catch (DataAccessException ex) {
            throw rethrowWithSqlDetails("autojobsaleinvoicecommonservicecharge", entity, ex);
        }
    }

    public Autojobsinvoicelines insertInvoiceLine(Autojobsinvoicelines entity) {
        try {
            String tableName = resolveQualifiedTableName("autojobsinvoicelines");
            GeneratedKey generatedKey = insertWithDetectedKey(
                tableName,
                mapOf(
                    "invocieid",
                    entity.getInvocieid(),
                    "lineid",
                    entity.getLineid(),
                    "itemid",
                    entity.getItemid(),
                    "itemcode",
                    entity.getItemcode(),
                    "itemname",
                    entity.getItemname(),
                    "description",
                    entity.getDescription(),
                    "unitofmeasurement",
                    entity.getUnitofmeasurement(),
                    "quantity",
                    entity.getQuantity(),
                    "itemcost",
                    entity.getItemcost(),
                    "itemprice",
                    entity.getItemprice(),
                    "discount",
                    entity.getDiscount(),
                    "tax",
                    entity.getTax(),
                    "sellingprice",
                    entity.getSellingprice(),
                    "linetotal",
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
            throw rethrowWithSqlDetails("autojobsinvoicelines", entity, ex);
        }
    }

    public Autojobsinvoicelinebatches insertInvoiceLineBatch(Autojobsinvoicelinebatches entity) {
        try {
            InvoiceLineKey invoiceLineKey = resolveInvoiceLineKey(entity);
            entity.setId(invoiceLineKey.id());
            entity.setLineid(invoiceLineKey.lineId());
            String tableName = resolveQualifiedTableName("autojobsinvoicelinebatches");

            // Check if a record already exists for this invoice line item
            Integer existingCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " +
                tableName +
                " WHERE " +
                bracket("id") +
                " = ? AND " +
                bracket("lineid") +
                " = ? AND " +
                bracket("itemid") +
                " = ?",
                Integer.class,
                entity.getId(),
                entity.getLineid(),
                entity.getItemid()
            );

            if (existingCount != null && existingCount > 0) {
                // Update existing record
                String updateSql =
                    "UPDATE " +
                    tableName +
                    " SET " +
                    bracket("issued") +
                    " = ?, " +
                    bracket("issueddatetime") +
                    " = ?, " +
                    bracket("issuedby") +
                    " = ?, " +
                    bracket("lmd") +
                    " = ? WHERE " +
                    bracket("id") +
                    " = ? AND " +
                    bracket("lineid") +
                    " = ? AND " +
                    bracket("itemid") +
                    " = ?";
                jdbcTemplate.update(
                    updateSql,
                    entity.getIssued() != null ? (entity.getIssued() ? 1 : 0) : 0,
                    toTimestamp(entity.getIssueddatetime()),
                    entity.getIssuedby(),
                    toTimestamp(Instant.now()),
                    entity.getId(),
                    entity.getLineid(),
                    entity.getItemid()
                );
                return entity;
            }

            entity.setBatchlineid(resolveNextBatchLineId(tableName, entity.getId(), entity.getLineid(), entity.getBatchlineid()));
            GeneratedKey generatedKey = insertWithDetectedKey(
                tableName,
                mapOf(
                    "id",
                    entity.getId(),
                    "lineid",
                    entity.getLineid(),
                    "batchlineid",
                    entity.getBatchlineid(),
                    "itemid",
                    entity.getItemid(),
                    "code",
                    entity.getCode(),
                    "batchid",
                    entity.getBatchid(),
                    "batchcode",
                    entity.getBatchcode(),
                    "txdate",
                    toTimestamp(entity.getTxdate()),
                    "manufacturedate",
                    toTimestamp(entity.getManufacturedate()),
                    "expireddate",
                    toTimestamp(entity.getExpireddate()),
                    "qty",
                    entity.getQty(),
                    "cost",
                    entity.getCost(),
                    "price",
                    entity.getPrice(),
                    "notes",
                    entity.getNotes(),
                    "lmu",
                    entity.getLmu(),
                    "lmd",
                    toTimestamp(entity.getLmd()),
                    "nbt",
                    entity.getNbt(),
                    "vat",
                    entity.getVat(),
                    "discount",
                    entity.getDiscount(),
                    "total",
                    entity.getTotal(),
                    "issued",
                    entity.getIssued(),
                    "issuedby",
                    entity.getIssuedby(),
                    "issueddatetime",
                    toTimestamp(entity.getIssueddatetime()),
                    "addedbyid",
                    entity.getAddedbyid(),
                    "canceloptid",
                    entity.getCanceloptid(),
                    "cancelopt",
                    entity.getCancelopt(),
                    "cancelby",
                    entity.getCancelby()
                )
            );
            applyGeneratedKey(entity, generatedKey);
            return entity;
        } catch (DataAccessException ex) {
            throw rethrowWithSqlDetails("autojobsinvoicelinebatches", entity, ex);
        }
    }

    public Autojobsinvoicelinebatches cancelInvoiceLineBatch(Autojobsinvoicelinebatches entity) {
        try {
            InvoiceLineKey invoiceLineKey = resolveInvoiceLineKey(entity);
            entity.setId(invoiceLineKey.id());
            entity.setLineid(invoiceLineKey.lineId());
            String tableName = resolveQualifiedTableName("autojobsinvoicelinebatches");

            Integer existingCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " +
                tableName +
                " WHERE " +
                bracket("id") +
                " = ? AND " +
                bracket("lineid") +
                " = ? AND " +
                bracket("itemid") +
                " = ?",
                Integer.class,
                entity.getId(),
                entity.getLineid(),
                entity.getItemid()
            );

            if (existingCount != null && existingCount > 0) {
                String updateSql =
                    "UPDATE " +
                    tableName +
                    " SET " +
                    bracket("canceloptid") +
                    " = ?, " +
                    bracket("cancelopt") +
                    " = ?, " +
                    bracket("cancelby") +
                    " = ?, " +
                    bracket("lmd") +
                    " = ? WHERE " +
                    bracket("id") +
                    " = ? AND " +
                    bracket("lineid") +
                    " = ? AND " +
                    bracket("itemid") +
                    " = ?";
                jdbcTemplate.update(
                    updateSql,
                    entity.getCanceloptid(),
                    entity.getCancelopt(),
                    entity.getCancelby(),
                    toTimestamp(Instant.now()),
                    entity.getId(),
                    entity.getLineid(),
                    entity.getItemid()
                );
                return entity;
            }

            entity.setIssued(false);
            entity.setBatchlineid(resolveNextBatchLineId(tableName, entity.getId(), entity.getLineid(), entity.getBatchlineid()));
            GeneratedKey generatedKey = insertWithDetectedKey(
                tableName,
                mapOf(
                    "id",
                    entity.getId(),
                    "lineid",
                    entity.getLineid(),
                    "batchlineid",
                    entity.getBatchlineid(),
                    "itemid",
                    entity.getItemid(),
                    "code",
                    entity.getCode(),
                    "batchid",
                    entity.getBatchid(),
                    "batchcode",
                    entity.getBatchcode(),
                    "txdate",
                    toTimestamp(entity.getTxdate() != null ? entity.getTxdate() : Instant.now()),
                    "manufacturedate",
                    toTimestamp(entity.getManufacturedate() != null ? entity.getManufacturedate() : Instant.now()),
                    "expireddate",
                    toTimestamp(entity.getExpireddate() != null ? entity.getExpireddate() : Instant.now()),
                    "qty",
                    entity.getQty() != null ? entity.getQty() : 1f,
                    "cost",
                    entity.getCost() != null ? entity.getCost() : 0f,
                    "price",
                    entity.getPrice() != null ? entity.getPrice() : 0f,
                    "notes",
                    entity.getNotes(),
                    "lmu",
                    entity.getLmu() != null ? entity.getLmu() : 0,
                    "lmd",
                    toTimestamp(Instant.now()),
                    "nbt",
                    entity.getNbt() != null ? entity.getNbt() : false,
                    "vat",
                    entity.getVat() != null ? entity.getVat() : false,
                    "discount",
                    entity.getDiscount() != null ? entity.getDiscount() : 0f,
                    "total",
                    entity.getTotal() != null ? entity.getTotal() : 0f,
                    "issued",
                    false,
                    "issuedby",
                    0,
                    "issueddatetime",
                    toTimestamp(Instant.now()),
                    "addedbyid",
                    entity.getAddedbyid() != null ? entity.getAddedbyid() : 0,
                    "canceloptid",
                    entity.getCanceloptid(),
                    "cancelopt",
                    entity.getCancelopt(),
                    "cancelby",
                    entity.getCancelby()
                )
            );
            applyGeneratedKey(entity, generatedKey);
            return entity;
        } catch (DataAccessException ex) {
            throw rethrowWithSqlDetails("autojobsinvoicelinebatches", entity, ex);
        }
    }

    private Integer resolveNextBatchLineId(
        String qualifiedTableName,
        Integer parentId,
        Integer parentLineId,
        Integer requestedBatchLineId
    ) {
        Map<String, String> columns = getActualColumns(qualifiedTableName);
        String idColumn = columns.get("id");
        String lineIdColumn = columns.get("lineid");
        String batchLineIdColumn = columns.get("batchlineid");

        if (idColumn == null || lineIdColumn == null || batchLineIdColumn == null || parentId == null || parentLineId == null) {
            return requestedBatchLineId;
        }

        Integer nextBatchLineId = jdbcTemplate.queryForObject(
            "SELECT COALESCE(MAX(" +
            bracket(batchLineIdColumn) +
            "), 0) + 1 FROM " +
            qualifiedTableName +
            " WHERE " +
            bracket(idColumn) +
            " = ? AND " +
            bracket(lineIdColumn) +
            " = ?",
            Integer.class,
            parentId,
            parentLineId
        );

        return nextBatchLineId != null ? nextBatchLineId : requestedBatchLineId;
    }

    private InvoiceLineKey resolveInvoiceLineKey(Autojobsinvoicelinebatches entity) {
        Integer requestedId = entity.getId();
        Integer requestedLineId = entity.getLineid();
        Integer fallbackId = requestedId != null ? requestedId : requestedLineId;
        Integer fallbackLineId = requestedLineId != null ? requestedLineId : requestedId;

        if (fallbackId == null || fallbackLineId == null) {
            throw new IllegalStateException("AutoJobsInvoiceLines parent key is missing");
        }

        try {
            String qualifiedTableName = resolveQualifiedTableName("autojobsinvoicelines");
            Map<String, String> columns = getActualColumns(qualifiedTableName);
            String idColumn = columns.get("id");
            String lineIdColumn = columns.get("lineid");
            String itemIdColumn = columns.get("itemid");
            String itemCodeColumn = columns.get("itemcode");

            if (idColumn == null || lineIdColumn == null) {
                return new InvoiceLineKey(fallbackId, fallbackLineId);
            }

            List<InvoiceLineKey> matches = jdbcTemplate.query(
                buildInvoiceLineLookupSql(qualifiedTableName, idColumn, lineIdColumn, itemIdColumn, itemCodeColumn),
                (rs, rowNum) -> new InvoiceLineKey(rs.getInt("id"), rs.getInt("lineid")),
                buildInvoiceLineLookupParams(entity, fallbackId, fallbackLineId)
            );

            return matches.isEmpty() ? new InvoiceLineKey(fallbackId, fallbackLineId) : matches.get(0);
        } catch (IllegalStateException ex) {
            return new InvoiceLineKey(fallbackId, fallbackLineId);
        }
    }

    private String buildInvoiceLineLookupSql(
        String qualifiedTableName,
        String idColumn,
        String lineIdColumn,
        String itemIdColumn,
        String itemCodeColumn
    ) {
        StringBuilder sql = new StringBuilder()
            .append("SELECT TOP 1 ")
            .append(bracket(idColumn))
            .append(" AS [id], ")
            .append(bracket(lineIdColumn))
            .append(" AS [lineid] FROM ")
            .append(qualifiedTableName)
            .append(" WHERE (")
            .append(bracket(idColumn))
            .append(" = ? AND ")
            .append(bracket(lineIdColumn))
            .append(" = ?)");

        if (itemIdColumn != null) {
            sql.append(" OR (").append(bracket(lineIdColumn)).append(" = ? AND ").append(bracket(itemIdColumn)).append(" = ?)");
        }

        if (itemCodeColumn != null) {
            sql.append(" OR (").append(bracket(lineIdColumn)).append(" = ? AND ").append(bracket(itemCodeColumn)).append(" = ?)");
        }

        sql
            .append(" OR ")
            .append(bracket(idColumn))
            .append(" = ? OR ")
            .append(bracket(lineIdColumn))
            .append(" = ? OR ")
            .append(bracket(idColumn))
            .append(" = ? OR ")
            .append(bracket(lineIdColumn))
            .append(" = ? ORDER BY CASE WHEN ")
            .append(bracket(idColumn))
            .append(" = ? AND ")
            .append(bracket(lineIdColumn))
            .append(" = ? THEN 0 ELSE 1 END");

        return sql.toString();
    }

    private Object[] buildInvoiceLineLookupParams(Autojobsinvoicelinebatches entity, Integer fallbackId, Integer fallbackLineId) {
        List<Object> params = new ArrayList<>();
        params.add(fallbackId);
        params.add(fallbackLineId);

        if (entity.getItemid() != null) {
            params.add(fallbackLineId);
            params.add(entity.getItemid());
        }

        if (entity.getCode() != null && !entity.getCode().isBlank()) {
            params.add(fallbackLineId);
            params.add(entity.getCode());
        }

        params.add(fallbackId);
        params.add(fallbackLineId);
        params.add(fallbackLineId);
        params.add(fallbackId);
        params.add(fallbackId);
        params.add(fallbackLineId);
        return params.toArray();
    }

    private GeneratedKey insertWithDetectedKey(String qualifiedTableName, Map<String, Object> requestedValues) {
        Map<String, String> actualColumns = getActualColumns(qualifiedTableName);
        String generatedColumn = resolveGeneratedKeyColumn(actualColumns);
        Object explicitGeneratedValue = generatedColumn != null ? requestedValues.get(generatedColumn) : null;

        LinkedHashMap<String, Object> insertValues = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : requestedValues.entrySet()) {
            String actualColumn = actualColumns.get(entry.getKey());
            if (actualColumn != null && (!actualColumn.equalsIgnoreCase(generatedColumn) || explicitGeneratedValue != null)) {
                insertValues.put(actualColumn, entry.getValue());
            }
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

    private void applyGeneratedKey(Autojobsinvoicelines entity, GeneratedKey generatedKey) {
        if (generatedKey.value == null || generatedKey.columnName == null) {
            return;
        }
        if ("id".equalsIgnoreCase(generatedKey.columnName)) {
            entity.setId(generatedKey.value);
        } else if ("lineid".equalsIgnoreCase(generatedKey.columnName)) {
            entity.setLineid(generatedKey.value.intValue());
        }
    }

    private void applyGeneratedKey(Autojobsalesinvoiceservicechargeline entity, GeneratedKey generatedKey) {
        if (generatedKey.value == null || generatedKey.columnName == null) {
            return;
        }
        if ("id".equalsIgnoreCase(generatedKey.columnName)) {
            entity.setId(generatedKey.value);
        } else if ("lineid".equalsIgnoreCase(generatedKey.columnName)) {
            entity.setLineid(generatedKey.value.intValue());
        }
    }

    private void applyGeneratedKey(Autojobsaleinvoicecommonservicecharge entity, GeneratedKey generatedKey) {
        if (generatedKey.value == null || generatedKey.columnName == null) {
            return;
        }
        if ("id".equalsIgnoreCase(generatedKey.columnName)) {
            entity.setId(generatedKey.value);
        } else if ("lineid".equalsIgnoreCase(generatedKey.columnName)) {
            entity.setLineid(generatedKey.value.intValue());
        }
    }

    private void applyGeneratedKey(Autojobsinvoicelinebatches entity, GeneratedKey generatedKey) {
        if (generatedKey.value == null || generatedKey.columnName == null) {
            return;
        }
        if ("id".equalsIgnoreCase(generatedKey.columnName)) {
            entity.setId(generatedKey.value.intValue());
        } else if ("lineid".equalsIgnoreCase(generatedKey.columnName)) {
            entity.setLineid(generatedKey.value.intValue());
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

    private record InvoiceLineKey(Integer id, Integer lineId) {}
}
