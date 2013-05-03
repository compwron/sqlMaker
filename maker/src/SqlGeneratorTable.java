package src;

public abstract class SqlGeneratorTable {
    private final String tableName;
    private final String[] columnNames;

    public SqlGeneratorTable(String tableName, String... columnNames) {
        this.tableName = tableName;
        this.columnNames = columnNames;
    }
}
