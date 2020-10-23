package com.gyugyu.tobyspringexample;

import org.junit.Before;
import org.junit.Test;
import user.sqlservice.ConcurrentHashMapRegistry;
import user.sqlservice.SqlNotFoundException;
import user.sqlservice.SqlUpdateFailureException;
import user.sqlservice.UpdatableSqlRegistry;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ConcurrentHashMapSqlRegistryTest {

    UpdatableSqlRegistry sqlRegistry;

    @Before
    public void setUp() {
        sqlRegistry = new ConcurrentHashMapRegistry();
        sqlRegistry.registerSql("KEY1", "SQL1");
        sqlRegistry.registerSql("KEY2", "SQL2");
        sqlRegistry.registerSql("KEY3", "SQL3");
    }

    @Test
    public void find() {
        checkFindResult("SQL1", "SQL2", "SQL3");
    }

    private void checkFindResult(String expected1, String expected2, String expected3) {
        assertThat(sqlRegistry.findSql("KEY1"), is(expected1));
        assertThat(sqlRegistry.findSql("KEY2"), is(expected2));
        assertThat(sqlRegistry.findSql("KEY3"), is(expected3));
    }

    @Test(expected = SqlNotFoundException.class)
    public void unknownKey() {
        sqlRegistry.findSql("SQL991230");
    }

    @Test
    public void updateSingle() {
        sqlRegistry.update("KEY2", "Modified2");
        checkFindResult("SQL1", "Modified2", "SQL3");
    }


    @Test
    public void updateMulti() {

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("KEY1", "Modified1");
        sqlMap.put("KEY3", "Modified3");


        sqlRegistry.update(sqlMap);
        checkFindResult("Modified1", "SQL2", "Modified3");

    }

    @Test(expected = SqlUpdateFailureException.class)
    public void updateWithNotExistingKey() {
        sqlRegistry.update("SQL123123", "Modified2");
    }
}
