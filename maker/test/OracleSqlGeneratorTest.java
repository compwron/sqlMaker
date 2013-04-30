package test;

import org.junit.Before;
import org.junit.Test;
import src.OracleSqlGenerator;

import java.util.ArrayList;

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
        assertThat(generator.selectAll().from(FooTable.TableName).toString(), is("select * from myTableName;"));
    }

    @Test
    public void shouldSelectWhereColumnValueIs1(){
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("columnName");
        assertThat(generator.selectAll().from(FooTable.TableName).where(FooTable.BarColumn).is(1).toString(), is("select * from myTableName where BarColumn is 1;"));
    }
}
