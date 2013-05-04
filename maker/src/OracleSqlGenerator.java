package src;

import com.sun.deploy.util.StringUtils;
import test.FooTable;

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

    public String build() {
        return query + endLine;
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

    public OracleSqlGenerator select(FooTable... columnNames) {
        query += "select ";
        query += StringUtils.join(transformColumnsToStrings(columnNames), ", ") + " ";
        return this;
    }

    public OracleSqlGenerator update(String tableName, Enum columnB) {
        query += "update " + tableName + space + columnB.name() + space;
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
}
