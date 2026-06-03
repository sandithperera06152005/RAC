package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Billingserviceoptionvalues;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BillingserviceoptionvaluesReadService {

    private static final Logger LOG = LoggerFactory.getLogger(BillingserviceoptionvaluesReadService.class);

    private final JdbcTemplate jdbcTemplate;

    public BillingserviceoptionvaluesReadService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Billingserviceoptionvalues> findByVehicleTypeId(Integer vehicleTypeId) {
        TableMetadata tableMetadata = resolveTableMetadata();
        String sql = buildVehicleTypeQuery(tableMetadata);

        try {
            return jdbcTemplate.query(sql, billingserviceoptionvaluesRowMapper(), vehicleTypeId);
        } catch (DataAccessException ex) {
            LOG.error(
                "Failed to load billing service option values using table {} and vehicle type column {}",
                tableMetadata.qualifiedTableName(),
                tableMetadata.vehicleTypeColumn(),
                ex
            );
            throw ex;
        }
    }

    private TableMetadata resolveTableMetadata() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
            "SELECT TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME " + "FROM INFORMATION_SCHEMA.COLUMNS"
        );

        Map<String, CandidateTable> candidates = new HashMap<>();
        for (Map<String, Object> row : rows) {
            String schema = String.valueOf(row.get("TABLE_SCHEMA"));
            String table = String.valueOf(row.get("TABLE_NAME"));
            String column = String.valueOf(row.get("COLUMN_NAME"));
            String key = schema + "." + table;
            candidates.computeIfAbsent(key, ignored -> new CandidateTable(schema, table)).addColumn(column);
        }

        return candidates
            .values()
            .stream()
            .filter(CandidateTable::hasRequiredColumns)
            .sorted(Comparator.comparingInt(CandidateTable::score).reversed())
            .findFirst()
            .map(CandidateTable::toMetadata)
            .orElseThrow(
                () -> new IllegalStateException("Could not resolve a billing service option values table from INFORMATION_SCHEMA")
            );
    }

    private String buildVehicleTypeQuery(TableMetadata tableMetadata) {
        return (
            "SELECT " +
            tableMetadata.idSelect() +
            ", " +
            tableMetadata.vehicleTypeSelect() +
            ", " +
            tableMetadata.billingServiceOptionSelect() +
            ", " +
            tableMetadata.valueSelect() +
            ", " +
            tableMetadata.lmdSelect() +
            ", " +
            tableMetadata.lmuSelect() +
            " FROM " +
            tableMetadata.qualifiedTableName() +
            " WHERE " +
            tableMetadata.bracket(tableMetadata.vehicleTypeColumn()) +
            " = ?"
        );
    }

    private RowMapper<Billingserviceoptionvalues> billingserviceoptionvaluesRowMapper() {
        return new RowMapper<>() {
            @Override
            public Billingserviceoptionvalues mapRow(ResultSet rs, int rowNum) throws SQLException {
                Billingserviceoptionvalues value = new Billingserviceoptionvalues();
                value.setId(rs.getLong("id"));
                value.setVehicletypeid((Integer) rs.getObject("vehicletypeid"));
                value.setBillingserviceoptionid((Integer) rs.getObject("billingserviceoptionid"));
                Number numericValue = (Number) rs.getObject("value");
                value.setValue(numericValue != null ? numericValue.floatValue() : null);
                Timestamp lmd = rs.getTimestamp("lmd");
                value.setLmd(lmd != null ? lmd.toInstant() : null);
                value.setLmu((Integer) rs.getObject("lmu"));
                return value;
            }
        };
    }

    private static final class CandidateTable {

        private final String schema;
        private final String table;
        private final List<String> columns = new ArrayList<>();

        private CandidateTable(String schema, String table) {
            this.schema = schema;
            this.table = table;
        }

        private void addColumn(String column) {
            columns.add(column);
        }

        private boolean hasRequiredColumns() {
            return resolveVehicleTypeColumn().isPresent() && resolveBillingServiceOptionColumn().isPresent();
        }

        private int score() {
            int score = 0;
            String normalizedTable = normalize(table);

            if ("billingserviceoptionvalues".equalsIgnoreCase(table)) {
                score += 100;
            }
            if (normalizedTable.contains("billing")) {
                score += 20;
            }
            if (normalizedTable.contains("service")) {
                score += 20;
            }
            if (normalizedTable.contains("option")) {
                score += 20;
            }
            if (normalizedTable.contains("value")) {
                score += 20;
            }
            if ("dbo".equalsIgnoreCase(schema)) {
                score += 10;
            }
            if (resolveValueColumn().isPresent()) {
                score += 5;
            }
            if (resolveLmdColumn().isPresent()) {
                score += 2;
            }
            if (resolveLmuColumn().isPresent()) {
                score += 2;
            }
            return score;
        }

        private Optional<String> findColumnByAliases(String... aliases) {
            return columns.stream().filter(column -> matchesAnyAlias(column, aliases)).findFirst();
        }

        private Optional<String> resolveIdColumn() {
            return findColumnByAliases("id");
        }

        private Optional<String> resolveVehicleTypeColumn() {
            return findColumnByAliases("vehicletypeid", "vehicletype", "typeid", "vehicletypefk");
        }

        private Optional<String> resolveBillingServiceOptionColumn() {
            return findColumnByAliases(
                "billingserviceoptionid",
                "billingserviceoption",
                "billingoptionid",
                "serviceoptionid",
                "billingserviceid"
            );
        }

        private Optional<String> resolveValueColumn() {
            return findColumnByAliases("value", "amount", "price", "servicevalue");
        }

        private Optional<String> resolveLmdColumn() {
            return findColumnByAliases("lmd", "lastmodifieddate", "modifieddate");
        }

        private Optional<String> resolveLmuColumn() {
            return findColumnByAliases("lmu", "lastmodifieduser", "modifieduser");
        }

        private TableMetadata toMetadata() {
            return new TableMetadata(
                schema,
                table,
                resolveIdColumn().orElse(null),
                resolveVehicleTypeColumn().orElseThrow(),
                resolveBillingServiceOptionColumn().orElseThrow(),
                resolveValueColumn().orElse(null),
                resolveLmdColumn().orElse(null),
                resolveLmuColumn().orElse(null)
            );
        }

        private boolean matchesAnyAlias(String column, String... aliases) {
            String normalizedColumn = normalize(column);
            for (String alias : aliases) {
                String normalizedAlias = normalize(alias);
                if (normalizedColumn.equals(normalizedAlias)) {
                    return true;
                }
            }
            return false;
        }

        private String normalize(String value) {
            return value == null ? "" : value.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        }
    }

    private record TableMetadata(
        String schema,
        String table,
        String idColumn,
        String vehicleTypeColumn,
        String billingServiceOptionColumn,
        String valueColumn,
        String lmdColumn,
        String lmuColumn
    ) {
        private String qualifiedTableName() {
            return bracket(schema) + "." + bracket(table);
        }

        private String idSelect() {
            return idColumn != null ? bracket(idColumn) + " AS [id]" : "ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS [id]";
        }

        private String vehicleTypeSelect() {
            return bracket(vehicleTypeColumn) + " AS [vehicletypeid]";
        }

        private String billingServiceOptionSelect() {
            return bracket(billingServiceOptionColumn) + " AS [billingserviceoptionid]";
        }

        private String valueSelect() {
            return valueColumn != null ? bracket(valueColumn) + " AS [value]" : "CAST(NULL AS float) AS [value]";
        }

        private String lmdSelect() {
            return lmdColumn != null ? bracket(lmdColumn) + " AS [lmd]" : "CAST(NULL AS datetime) AS [lmd]";
        }

        private String lmuSelect() {
            return lmuColumn != null ? bracket(lmuColumn) + " AS [lmu]" : "CAST(NULL AS int) AS [lmu]";
        }

        private String bracket(String identifier) {
            return "[" + identifier.replace("]", "]]") + "]";
        }
    }
}
