package test;

import org.junit.Test;
import src.OracleSqlGenerator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OracleSqlGeneratorTest {

    @Test
    public void generatorShouldWriteSelectAllStatement() {
        OracleSqlGenerator generator = new OracleSqlGenerator();
        String tableName = "myTable";
        assertThat(generator.selectAll().from(tableName).toString(), is("select * from " + tableName + ";"));
    }

//    test ending with/out semicolons?
}
