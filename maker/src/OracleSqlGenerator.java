package src;

import test.BarTable;
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
        ArrayList<String> stringColumnNames = new ArrayList<String>();
        for (FooTable column : columnNames) {
            stringColumnNames.add(column.name());
        }

//        StringUtils.join(stringColumnNames, ", "); // get a different java and use this
        query += "select ";
        for (String columnName : stringColumnNames) {
            query += columnName;
            if (!isLastColumnInList(stringColumnNames, columnName)) {
                query += ", ";
            } else {
                query += space;
            }
        }
        return this;
    }

    private boolean isLastColumnInList(ArrayList<String> stringColumnNames, String columnName) {
        return columnName.equals(stringColumnNames.get(stringColumnNames.size() - 1));
    }

    public OracleSqlGenerator update(String tableName, BarTable columnB) {
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
}
