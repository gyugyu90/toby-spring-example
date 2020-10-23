package user.sqlservice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapRegistry implements UpdatableSqlRegistry {

    private Map<String, String> sqlMap = new ConcurrentHashMap<>();


    @Override
    public void update(String key, String sql) throws SqlUpdateFailureException {
        if (sqlMap.get(key) == null) throw new SqlUpdateFailureException(key + "에 해당하는 SQL을 찾을 수 없습니다.");

        sqlMap.put(key, sql);
    }

    @Override
    public void update(Map<String, String> sqlMap) throws SqlUpdateFailureException {
        for (Map.Entry<String, String> entry : sqlMap.entrySet()) {
            this.sqlMap.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void registerSql(String key, String sql) {
        sqlMap.put(key, sql);
    }

    @Override
    public String findSql(String key) throws SqlNotFoundException {

        String sql = sqlMap.get(key);
        if (sql == null) throw new SqlNotFoundException(key + "를 이용해서 SQL을 찾을 수 없습니다.");
        return sql;
    }
}
