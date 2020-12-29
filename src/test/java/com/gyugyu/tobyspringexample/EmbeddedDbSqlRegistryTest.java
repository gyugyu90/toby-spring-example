package com.gyugyu.tobyspringexample;

import org.junit.After;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import user.sqlservice.SqlUpdateFailureException;
import user.sqlservice.UpdatableSqlRegistry;
import user.sqlservice.updatable.EmbeddedDbSqlRegistry;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest{

    EmbeddedDatabase db;

    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {

        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:/sqlRegistrySchema.sql")
                .build();

        EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
        embeddedDbSqlRegistry.setDataSource(db);

        return embeddedDbSqlRegistry;
    }

    @After
    public void tearDown() {
        db.shutdown();
    }

    @Test
    public void transactionalUpdate() {
        checkFind("SQL1", "SQL2", "SQL3");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("KEY1", "Modified");
        sqlMap.put("KEY9999!@#$", "Modified9999");

        try {
            sqlRegistry.update(sqlMap);
            fail();
        } catch (SqlUpdateFailureException ex) {
            ex.printStackTrace();
        }
        
        checkFind("SQL1", "SQL2", "SQL3");
    }
}
