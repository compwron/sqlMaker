package src;

import java.util.ArrayList;

public class OracleSqlGenerator implements SqlGenerator {
    private static final String sqlStringQuote = "'";
    private static final String space = " ";

    private String query = "";

    public OracleSqlGenerator selectAll() {
        query += "select * ";
        return this;
    }

    public OracleSqlGenerator select(Enum... columnNames) {
        query += "select ";
        query += transformColumnsToStrings(columnNames) + space;
        return this;
    }

    public OracleSqlGenerator selectAllByName(Enum[] columnNames) {
        query += "select " + transformColumnsToStrings(columnNames) + space;
        return this;
    }

    public OracleSqlGenerator from(String tableName) {
        query += "from " + tableName;
        return this;
    }

    public OracleSqlGenerator where(Enum columnName) {
        query += " where " + columnName.name() + space;
        return this;
    }

    public OracleSqlGenerator is(int value) {
        query += "is " + value;
        return this;
    }

    public OracleSqlGenerator isEqualTo(String value) {
        query += "= " + sqlStringQuote + value + sqlStringQuote;
        return this;
    }

    public OracleSqlGenerator is(String value) {
        query += "is " + sqlStringQuote + value + sqlStringQuote;
        return this;
    }

    public OracleSqlGenerator update(String tableName, Enum column) {
        query += "update " + tableName + space + column.name() + space;
        return this;
    }

    public OracleSqlGenerator with(String data) {
        query += "with '" + data + sqlStringQuote;
        return this;
    }

    public OracleSqlGenerator orderBy(Enum... columns) {
        query += "order by ";
        query += transformColumnsToStrings(columns);
        return this;
    }

    public OracleSqlGenerator and(Enum columnName) {
        query += " and " + columnName + space;
        return this;
    }

    public OracleSqlGenerator unionAll() {
        query += " union all";
        return this;
    }

    public OracleSqlGenerator parameterizedInsert(String tableName, Enum[] columns) {
        query += "insert into " + tableName + " (" + transformColumnsToStrings(columns) + ")" + valuesFor(columns);
        return this;
    }

    private String valuesFor(Enum[] columns) {
        String questionMarks = "";
        for (int i = 0; i < columns.length - 1; i++) {
            questionMarks += "?,";
        }
        questionMarks += "?";

        return " values (" + questionMarks + ")";
    }

    public OracleSqlGenerator literal(String literalSql) {
        query += literalSql;
        return this;
    }

    public OracleSqlGenerator selectAsTableNameUnderscoreColumnName(String tableName, Enum... columnNames) {
        for (Enum column : columnNames) {
            query += tableName + "_" + column + ", ";
        }
        query = removeLastComma(query);
        return this;
    }

    public String build() {
        return query + ";";
    }

    public String buildWithoutSemicolon() {
        return query;
    }

    private String removeLastComma(String s) {
        s = s.trim();
        return s.substring(0, s.length() - 1);
    }

    private String transformColumnsToStrings(Enum... columnNames) {
        ArrayList<String> stringColumnNames = new ArrayList<String>();
        for (Enum column : columnNames) {
            stringColumnNames.add(column.name());
        }
        String queryWithExtraTrailingComma = "";
        for (String columnName : stringColumnNames) {
            queryWithExtraTrailingComma += columnName + ", ";
        }

        return removeLastComma(queryWithExtraTrailingComma.trim());
    }


}
