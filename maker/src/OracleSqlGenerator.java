package src;

import test.FooTable;

public class OracleSqlGenerator {
    private String query = "";

    public OracleSqlGenerator selectAll() {
        query += "select * ";
        return this;
    }

    public OracleSqlGenerator from(String tableName) {
        query += "from " + tableName;
        return this;
    }

    public String toString() {
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
}
