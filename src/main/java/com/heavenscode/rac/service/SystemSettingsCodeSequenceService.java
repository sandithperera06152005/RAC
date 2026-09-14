package com.heavenscode.rac.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemSettingsCodeSequenceService {

    private final JdbcTemplate jdbcTemplate;

    public SystemSettingsCodeSequenceService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public boolean systemSettingsTableExists() {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE LOWER(TABLE_NAME) = ?",
            Integer.class,
            "systemsettings"
        );
        return count != null && count > 0;
    }

    @Transactional(readOnly = true)
    public String peekNextValue(String settingsKey) {
        return readNextValue(settingsKey, false, null, null);
    }

    public String consumeNextValue(String settingsKey, Integer lmu, Instant lmd) {
        return readNextValue(settingsKey, true, lmu, lmd);
    }

    private String readNextValue(String settingsKey, boolean incrementCounter, Integer lmu, Instant lmd) {
        String qualifiedTableName = resolveQualifiedTableName("systemsettings");
        Map<String, String> columns = getActualColumns(qualifiedTableName);
        String keyColumn = resolveColumn(columns, "key");
        String nextValueColumn = resolveColumn(columns, "nextvalue");

        String selectSql =
            "SELECT " +
            bracket(nextValueColumn) +
            " FROM " +
            qualifiedTableName +
            " WITH (UPDLOCK, ROWLOCK) WHERE " +
            bracket(keyColumn) +
            " = ?";

        String currentValue;
        try {
            currentValue = jdbcTemplate.queryForObject(selectSql, String.class, settingsKey);
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalStateException("SystemSettings row not found for key " + settingsKey, ex);
        }

        if (!incrementCounter) {
            return currentValue;
        }

        updateNextValue(qualifiedTableName, columns, keyColumn, currentValue, incrementTrailingNumber(currentValue), settingsKey, lmu, lmd);
        return currentValue;
    }

    private void updateNextValue(
        String qualifiedTableName,
        Map<String, String> columns,
        String keyColumn,
        String currentValue,
        String nextValue,
        String settingsKey,
        Integer lmu,
        Instant lmd
    ) {
        String lastValueColumn = resolveColumn(columns, "lastvalue");
        String nextValueColumn = resolveColumn(columns, "nextvalue");
        StringBuilder updateSql = new StringBuilder(
            "UPDATE " + qualifiedTableName + " SET " + bracket(lastValueColumn) + " = ?, " + bracket(nextValueColumn) + " = ?"
        );

        boolean hasLmu = columns.containsKey("lmu");
        boolean hasLmd = columns.containsKey("lmd");
        if (hasLmu) {
            updateSql.append(", ").append(bracket(columns.get("lmu"))).append(" = ?");
        }
        if (hasLmd) {
            updateSql.append(", ").append(bracket(columns.get("lmd"))).append(" = ?");
        }
        updateSql.append(" WHERE ").append(bracket(keyColumn)).append(" = ?");

        if (hasLmu && hasLmd) {
            jdbcTemplate.update(updateSql.toString(), currentValue, nextValue, lmu, timestamp(lmd), settingsKey);
        } else if (hasLmu) {
            jdbcTemplate.update(updateSql.toString(), currentValue, nextValue, lmu, settingsKey);
        } else if (hasLmd) {
            jdbcTemplate.update(updateSql.toString(), currentValue, nextValue, timestamp(lmd), settingsKey);
        } else {
            jdbcTemplate.update(updateSql.toString(), currentValue, nextValue, settingsKey);
        }
    }

    public int extractSequenceNumber(String storedValue) {
        if (storedValue == null || storedValue.isBlank()) {
            return 0;
        }

        String numericPart = storedValue.replaceAll("\\D+", "");
        if (numericPart.isBlank()) {
            return 0;
        }

        return Integer.parseInt(numericPart);
    }

    private String incrementTrailingNumber(String value) {
        if (value == null || value.isBlank()) {
            return "1";
        }

        int numberStart = value.length();
        while (numberStart > 0 && Character.isDigit(value.charAt(numberStart - 1))) {
            numberStart--;
        }

        if (numberStart == value.length()) {
            return value + "1";
        }

        String prefix = value.substring(0, numberStart);
        String numericPart = value.substring(numberStart);
        int nextNumber = Integer.parseInt(numericPart) + 1;
        return prefix + String.format(Locale.ENGLISH, "%0" + numericPart.length() + "d", nextNumber);
    }

    private Timestamp timestamp(Instant instant) {
        return Timestamp.from(instant != null ? instant : Instant.now());
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
            tableName.toLowerCase(Locale.ENGLISH)
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
            .collect(
                Collectors.toMap(column -> column.toLowerCase(Locale.ENGLISH), column -> column, (left, right) -> left, LinkedHashMap::new)
            );
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
