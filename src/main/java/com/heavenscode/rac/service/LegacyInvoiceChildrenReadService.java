package com.heavenscode.rac.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LegacyInvoiceChildrenReadService {

    private final JdbcTemplate jdbcTemplate;

    public LegacyInvoiceChildrenReadService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> findByInvoiceId(
        String tableName,
        String invoiceColumnLogicalName,
        LinkedHashMap<String, String> aliasToLogicalColumn,
        Integer invoiceId
    ) {
        String qualifiedTableName = resolveQualifiedTableName(tableName);
        Map<String, String> columns = getActualColumns(qualifiedTableName);
        String invoiceColumn = columns.get(invoiceColumnLogicalName.toLowerCase());

        if (invoiceColumn == null) {
            throw new IllegalStateException("Column " + invoiceColumnLogicalName + " was not found in " + tableName);
        }

        String selectClause = aliasToLogicalColumn
            .entrySet()
            .stream()
            .map(entry -> selectExpression(columns, entry.getValue()) + " AS [" + entry.getKey() + "]")
            .collect(Collectors.joining(", "));

        String sql = "SELECT " + selectClause + " FROM " + qualifiedTableName + " WHERE " + bracket(invoiceColumn) + " = ?";

        return jdbcTemplate.queryForList(sql, invoiceId);
    }

    public List<Map<String, Object>> findByInvoiceId(
        String tableName,
        List<String> invoiceColumnLogicalNames,
        LinkedHashMap<String, String> aliasToLogicalColumn,
        Integer invoiceId
    ) {
        String qualifiedTableName = resolveQualifiedTableName(tableName);
        Map<String, String> columns = getActualColumns(qualifiedTableName);
        String invoiceColumn = invoiceColumnLogicalNames
            .stream()
            .map(name -> columns.get(name.toLowerCase()))
            .filter(java.util.Objects::nonNull)
            .findFirst()
            .orElse(null);

        if (invoiceColumn == null) {
            throw new IllegalStateException("None of the invoice columns " + invoiceColumnLogicalNames + " were found in " + tableName);
        }

        String selectClause = aliasToLogicalColumn
            .entrySet()
            .stream()
            .map(entry -> selectExpression(columns, entry.getValue()) + " AS [" + entry.getKey() + "]")
            .collect(Collectors.joining(", "));

        String sql = "SELECT " + selectClause + " FROM " + qualifiedTableName + " WHERE " + bracket(invoiceColumn) + " = ?";

        return jdbcTemplate.queryForList(sql, invoiceId);
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

    private String selectExpression(Map<String, String> columns, String logicalName) {
        String actualColumn = columns.get(logicalName.toLowerCase());
        if (actualColumn == null) {
            return "CAST(NULL AS sql_variant)";
        }
        return bracket(actualColumn);
    }

    private String schemaName(String qualifiedTableName) {
        String[] parts = unquote(qualifiedTableName).split("\\.");
        return parts[0];
    }

    private String tableNameOnly(String qualifiedTableName) {
        String[] parts = unquote(qualifiedTableName).split("\\.");
        return parts[1];
    }

    private String unquote(String qualifiedTableName) {
        return qualifiedTableName.replace("[", "").replace("]", "");
    }

    private String bracket(String name) {
        return "[" + name + "]";
    }
}
