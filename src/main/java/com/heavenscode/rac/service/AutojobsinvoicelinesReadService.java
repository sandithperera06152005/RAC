package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Autojobsinvoicelines;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AutojobsinvoicelinesReadService {

    private final JdbcTemplate jdbcTemplate;

    public AutojobsinvoicelinesReadService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Autojobsinvoicelines> findByInvoiceId(Integer invoiceId) {
        String qualifiedTableName = resolveQualifiedTableName("autojobsinvoicelines");
        Map<String, String> columns = getActualColumns(qualifiedTableName);

        String idExpression = columns.containsKey("id")
            ? bracket(columns.get("id"))
            : columns.containsKey("lineid")
                ? "CAST(" + bracket(columns.get("lineid")) + " AS bigint)"
                : "CAST(ROW_NUMBER() OVER (ORDER BY (SELECT 1)) AS bigint)";

        String sql =
            "SELECT " +
            idExpression +
            " AS [id], " +
            selectColumn(columns, "invocieid") +
            " AS [invocieid], " +
            selectColumn(columns, "lineid") +
            " AS [lineid], " +
            selectColumn(columns, "itemid") +
            " AS [itemid], " +
            selectColumn(columns, "itemcode") +
            " AS [itemcode], " +
            selectColumn(columns, "itemname") +
            " AS [itemname], " +
            selectColumn(columns, "description") +
            " AS [description], " +
            selectColumn(columns, "unitofmeasurement") +
            " AS [unitofmeasurement], " +
            selectColumn(columns, "quantity") +
            " AS [quantity], " +
            selectColumn(columns, "itemcost") +
            " AS [itemcost], " +
            selectColumn(columns, "itemprice") +
            " AS [itemprice], " +
            selectColumn(columns, "discount") +
            " AS [discount], " +
            selectColumn(columns, "tax") +
            " AS [tax], " +
            selectColumn(columns, "sellingprice") +
            " AS [sellingprice], " +
            selectColumn(columns, "linetotal") +
            " AS [linetotal], " +
            selectColumn(columns, "lmu") +
            " AS [lmu], " +
            selectColumn(columns, "lmd") +
            " AS [lmd], " +
            selectColumn(columns, "nbt") +
            " AS [nbt], " +
            selectColumn(columns, "vat") +
            " AS [vat] " +
            "FROM " +
            qualifiedTableName +
            " WHERE " +
            selectColumn(columns, "invocieid") +
            " = ? ORDER BY " +
            selectColumn(columns, "lineid");

        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> {
                Autojobsinvoicelines line = new Autojobsinvoicelines();
                long id = rs.getLong("id");
                if (!rs.wasNull()) {
                    line.setId(id);
                }
                int invocieid = rs.getInt("invocieid");
                if (!rs.wasNull()) {
                    line.setInvocieid(invocieid);
                }
                int lineid = rs.getInt("lineid");
                if (!rs.wasNull()) {
                    line.setLineid(lineid);
                }
                int itemid = rs.getInt("itemid");
                if (!rs.wasNull()) {
                    line.setItemid(itemid);
                }
                line.setItemcode(rs.getString("itemcode"));
                line.setItemname(rs.getString("itemname"));
                line.setDescription(rs.getString("description"));
                line.setUnitofmeasurement(rs.getString("unitofmeasurement"));
                float quantity = rs.getFloat("quantity");
                if (!rs.wasNull()) {
                    line.setQuantity(quantity);
                }
                float itemcost = rs.getFloat("itemcost");
                if (!rs.wasNull()) {
                    line.setItemcost(itemcost);
                }
                float itemprice = rs.getFloat("itemprice");
                if (!rs.wasNull()) {
                    line.setItemprice(itemprice);
                }
                float discount = rs.getFloat("discount");
                if (!rs.wasNull()) {
                    line.setDiscount(discount);
                }
                float tax = rs.getFloat("tax");
                if (!rs.wasNull()) {
                    line.setTax(tax);
                }
                float sellingprice = rs.getFloat("sellingprice");
                if (!rs.wasNull()) {
                    line.setSellingprice(sellingprice);
                }
                float linetotal = rs.getFloat("linetotal");
                if (!rs.wasNull()) {
                    line.setLinetotal(linetotal);
                }
                int lmu = rs.getInt("lmu");
                if (!rs.wasNull()) {
                    line.setLmu(lmu);
                }
                Timestamp lmd = rs.getTimestamp("lmd");
                if (lmd != null) {
                    line.setLmd(lmd.toInstant());
                }
                boolean nbt = rs.getBoolean("nbt");
                if (!rs.wasNull()) {
                    line.setNbt(nbt);
                }
                boolean vat = rs.getBoolean("vat");
                if (!rs.wasNull()) {
                    line.setVat(vat);
                }
                return line;
            },
            invoiceId
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

    private String selectColumn(Map<String, String> columns, String logicalName) {
        String actual = columns.get(logicalName.toLowerCase());
        if (actual == null) {
            return "CAST(NULL AS sql_variant)";
        }
        return bracket(actual);
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
