package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Autojobsinvoicelinebatches;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AutojobsinvoicelinebatchesReadService {

    private final JdbcTemplate jdbcTemplate;

    public AutojobsinvoicelinebatchesReadService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Autojobsinvoicelinebatches> findByParentInvoiceLineIds(List<Integer> invoiceLineIds) {
        if (invoiceLineIds == null || invoiceLineIds.isEmpty()) {
            return List.of();
        }

        String qualifiedTableName = resolveQualifiedTableName("autojobsinvoicelinebatches");
        Map<String, String> columns = getActualColumns(qualifiedTableName);
        String idColumn = requireColumn(columns, "id");
        String lineIdColumn = requireColumn(columns, "lineid");
        String itemIdColumn = requireColumn(columns, "itemid");
        String codeColumn = requireColumn(columns, "code");
        String issuedColumn = requireColumn(columns, "issued");

        String sql =
            "SELECT " +
            bracket(idColumn) +
            " AS [id], " +
            bracket(lineIdColumn) +
            " AS [lineid], " +
            bracket(itemIdColumn) +
            " AS [itemid], " +
            bracket(codeColumn) +
            " AS [code], " +
            bracket(issuedColumn) +
            " AS [issued] " +
            "FROM " +
            qualifiedTableName +
            " WHERE " +
            bracket(idColumn) +
            " IN (" +
            String.join(", ", java.util.Collections.nCopies(invoiceLineIds.size(), "?")) +
            ")";

        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> {
                Autojobsinvoicelinebatches batch = new Autojobsinvoicelinebatches();
                int id = rs.getInt("id");
                if (!rs.wasNull()) {
                    batch.setId(id);
                }
                int lineid = rs.getInt("lineid");
                if (!rs.wasNull()) {
                    batch.setLineid(lineid);
                }
                int itemid = rs.getInt("itemid");
                if (!rs.wasNull()) {
                    batch.setItemid(itemid);
                }
                batch.setCode(rs.getString("code"));
                boolean issued = rs.getBoolean("issued");
                if (!rs.wasNull()) {
                    batch.setIssued(issued);
                }
                return batch;
            },
            invoiceLineIds.toArray()
        );
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

    private String requireColumn(Map<String, String> columns, String logicalName) {
        String actual = columns.get(logicalName.toLowerCase());
        if (actual == null) {
            throw new IllegalStateException("Column " + logicalName + " was not found in AutoJobsInvoiceLineBatches");
        }
        return actual;
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
