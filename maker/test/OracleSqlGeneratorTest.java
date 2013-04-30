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
}
