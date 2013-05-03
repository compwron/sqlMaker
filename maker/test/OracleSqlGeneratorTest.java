package test;

import org.junit.Before;
import org.junit.Test;
import src.OracleSqlGenerator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OracleSqlGeneratorTest {
    OracleSqlGenerator generator;

    @Before
    public void setUp(){
        generator = new OracleSqlGenerator();
    }

    @Test
    public void generatorShouldWriteSelectAllStatement() {
        assertThat(generator.selectAll().from(FooTable.TableName).build(), is("select * from myTableName;"));
    }

    @Test
    public void shouldSelectWhereColumnValueIs1(){
        assertThat(generator.selectAll().from(FooTable.TableName).where(FooTable.BarColumn).is(1).build(), is("select * from myTableName where BarColumn is 1;"));
    }

    @Test
    public void shouldSelectWhereColumnValueIsAString(){
        assertThat(generator.selectAll().from(FooTable.TableName).where(FooTable.BarColumn).is("a value").build(), is("select * from myTableName where BarColumn is 'a value';"));
    }

    @Test
    public void shouldSelectMultipleColumns(){
        assertThat(generator.select(FooTable.BarColumn, FooTable.BazColumn).from(FooTable.TableName).build(), is("select BarColumn, BazColumn from myTableName;"));
    }

    @Test
    public void shouldUpdateTable1WithDataFromTable2(){
        assertThat(generator.update(BarTable.Tablename, BarTable.ColumnB).with('b').where(BarTable.ColumnA).is('a'), is("update barTableName ColumnB with 'b' where barTableName.columnA is 'a';"));
    }
}
