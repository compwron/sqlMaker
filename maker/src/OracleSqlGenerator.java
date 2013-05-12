package src;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;

public class OracleSqlGenerator implements SqlGenerator {
    private static final String sqlStringQuote = "'";
    private static final String endLine = ";";
    private static final String space = " ";

    private String query = "";

    public OracleSqlGenerator selectAll() {
        query += "select * ";
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

    public OracleSqlGenerator is(String value) {
        query += "is " + sqlStringQuote + value + sqlStringQuote;
        return this;
    }

    public OracleSqlGenerator select(Enum... columnNames) {
        query += "select ";
        query += StringUtils.join(transformColumnsToStrings(columnNames), ", ") + " ";
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

    public OracleSqlGenerator isEqualTo(String value) {
        query += "= " + sqlStringQuote + value + sqlStringQuote;
        return this;
    }

    public OracleSqlGenerator orderBy(Enum... columns) {
        query += "order by ";
        query += StringUtils.join(transformColumnsToStrings(columns), ", ");
        return this;
    }

    private ArrayList<String> transformColumnsToStrings(Enum... columnNames){
        ArrayList<String> stringColumnNames = new ArrayList<String>();
        for (Enum column : columnNames) {
            stringColumnNames.add(column.name());
        }
        return stringColumnNames;
    }

    public OracleSqlGenerator and(Enum columnName) {
        query += " and " + columnName + space;
        return this;
    }

    public OracleSqlGenerator unionAll() {
        query += " union all";
        return this;
    }

    public OracleSqlGenerator selectAllByName(Enum[] columnNames) {
        query += "select " + StringUtils.join(transformColumnsToStrings(columnNames), ", ") + space;
        return this;
    }

    public OracleSqlGenerator parameterizedInsert(String tableName, Enum[] columns) {
        query += "insert into " + tableName + " (" + StringUtils.join(transformColumnsToStrings(columns), ", ") + ")" + valuesFor(columns);
        return this;
    }

    private String valuesFor(Enum[] columns) {
        String questionMarks = "";
        for (int i = 0; i < columns.length -1 ; i++){
            questionMarks += "?,";
        }
        questionMarks += "?";

        return " values (" + questionMarks +")";
    }

    public OracleSqlGenerator literal(String literalSql) {
        query += literalSql;
        return this;
    }

    public OracleSqlGenerator selectAsTableNameUnderscoreColumnName(String tableName, Enum... columnNames) {
        for (Enum column : columnNames){
            query += tableName + "_" + column + ", ";
        }

        query = removeLastComma(query);

        return this;
    }

    private String removeLastComma(String s) {
        s = s.trim();
        return s.substring(0, s.length() - 1);
    }

    public String build() {
        return query + endLine;
    }

    public String buildWithoutSemicolon() {
        return query;
    }
}
