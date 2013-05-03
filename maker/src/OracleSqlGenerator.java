package src;

import test.FooTable;

import java.util.ArrayList;

public class OracleSqlGenerator implements SqlGenerator {
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
        return query + ";";
    }

    public OracleSqlGenerator where(FooTable barColumn) {
        query += " where " + barColumn.name() + " ";
        return this;
    }

    public OracleSqlGenerator is(int value) {
        query += "is " + value;
        return this;
    }

    public OracleSqlGenerator is(String value) {
        query += "is " + "'" + value + "'";
        return this;
    }

    public OracleSqlGenerator select(FooTable... columnNames) {
        ArrayList<String> stringColumnNames = new ArrayList<String>();
        for(FooTable column : columnNames){
            stringColumnNames.add(column.name());
        }

        query += "select ";
        for(String columnName : stringColumnNames){
            query += columnName;
            if ( ! isLastColumnInList(stringColumnNames, columnName)){
                query += ", ";
            } else {
                query += " ";
            }
        }
        return this;
    }

    private boolean isLastColumnInList(ArrayList<String> stringColumnNames, String columnName) {
        return columnName.equals(stringColumnNames.get(stringColumnNames.size() - 1));
    }
}
