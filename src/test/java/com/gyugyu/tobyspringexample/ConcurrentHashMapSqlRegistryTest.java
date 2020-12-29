package com.gyugyu.tobyspringexample;

import user.sqlservice.ConcurrentHashMapRegistry;
import user.sqlservice.UpdatableSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest{

    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        return new ConcurrentHashMapRegistry();
    }

}
