package test;

import org.junit.Before;
import org.junit.Test;
import src.OracleSqlGenerator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OracleSqlGeneratorTest {
    OracleSqlGenerator generator;

    @Before
    public void setUp() {
        generator = new OracleSqlGenerator();
    }

    @Test
    public void shouldCreateUnderscoreSeparatedAliasesForColumns(){
        String expectedAliases = "barTableName_ColumnA, barTableName_ColumnB";
        assertThat(generator.underscoreSeparated(BarTable.tableName, BarTable.ColumnA, BarTable.ColumnB).buildWithoutSemicolon(), is(expectedAliases));
    }

    @Test
    public void shouldCreateUnderscoreSeparatedAliasesForColumnsForAllColumnsInTable(){
        String expectedAliases = "barTableName_ColumnA, barTableName_ColumnB";
        assertThat(generator.underscoreSeparated(BarTable.tableName, BarTable.values()).buildWithoutSemicolon(), is(expectedAliases));
    }

    @Test
    public void shouldAllowCustomSqlInTheMiddleOfStatement(){
        String expectedNonsense = "select * from something nonsensical where BarColumn = 'bar';";
        assertThat(generator.selectAll().literal("from something nonsensical").where(FooTable.BarColumn).isEqualTo("bar").build(), is(expectedNonsense));
    }

    @Test
    public void parameterizedInsertOfAllValuesInTableShouldHaveSameNumberOfQuestionMarksAsFields(){
        String expectedInsert = "insert into fooTableName (BarColumn, BazColumn) values (?,?);";
        assertThat(generator.parameterizedInsert(FooTable.tableName, FooTable.values()).build(), is(expectedInsert));
    }

    @Test
    public void generatorShouldWriteSelectAllStatement() {
        assertThat(generator.selectAll().from(FooTable.tableName).build(), is("select * from fooTableName;"));
    }

    @Test
    public void shouldSelectWhereColumnValueIs1() {
        assertThat(generator.selectAll().from(FooTable.tableName).where(FooTable.BarColumn).is(1).build(), is("select * from fooTableName where BarColumn is 1;"));
    }

    @Test
    public void shouldSelectWhereColumnValueIsAString() {
        assertThat(generator.selectAll().from(FooTable.tableName).where(FooTable.BarColumn).is("a value").build(), is("select * from fooTableName where BarColumn is 'a value';"));
    }

    @Test
    public void shouldSelectMultipleColumns() {
        assertThat(generator.select(FooTable.BarColumn, FooTable.BazColumn).from(FooTable.tableName).build(), is("select BarColumn, BazColumn from fooTableName;"));
    }

    @Test
    public void shouldUpdateTable1WithDataFromTable2() {
        assertThat(generator.update(BarTable.tableName, BarTable.ColumnB).with("b").where(BarTable.ColumnA).is("a").build(), is("update barTableName ColumnB with 'b' where ColumnA is 'a';"));
    }

    @Test
    public void shouldSelectFromTableWhereColumnValueIsFoo() {
        String expectedSql = "select * from fooTableName where BarColumn = 'foo';";
        assertThat(generator.selectAll().from(FooTable.tableName).where(FooTable.BarColumn).isEqualTo("foo").build(), is(expectedSql));
    }

    @Test
    public void shouldMakeSameQueryToTwoTablesWithSameCode(){
        String fooExpectedSql = "select * from fooTableName where BarColumn = 'foo';";
        assertThat(generator.selectAll().from(FooTable.tableName).where(FooTable.BarColumn).isEqualTo("foo").build(), is(fooExpectedSql));

        generator = new OracleSqlGenerator();
        String barExpectedSql = "select * from barTableName where ColumnA = 'foo';";
        assertThat(generator.selectAll().from(BarTable.tableName).where(BarTable.ColumnA).isEqualTo("foo").build(), is(barExpectedSql));
    }

    @Test
    public void shouldComposeOrderByWIthMultipleOrderers(){
        String expectedOrderBy = "order by ColumnA, ColumnB;";
        assertThat(generator.orderBy(BarTable.ColumnA, BarTable.ColumnB).build(), is(expectedOrderBy));
    }

    @Test
    public void shouldMultiSelectMultiWhereUnionAll(){
//    select a, b from d, e where g = h and j = k union all
        String expected = "select ColumnA, ColumnB from barTableName where ColumnA = 'foo' and ColumnB = 'bar' union all;";
        assertThat(generator.select(BarTable.ColumnA, BarTable.ColumnB).from(BarTable.tableName).where(BarTable.ColumnA).isEqualTo("foo").and(BarTable.ColumnB).isEqualTo("bar").unionAll().build(), is(expected));
    }

    @Test
    public void shouldAddAllColumnsInTableEnum(){
        String expected = "select ColumnA, ColumnB from barTableName;";
        assertThat(generator.selectAllByName(BarTable.values()).from(BarTable.tableName).build(), is(expected));
    }
}
