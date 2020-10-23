package user.sqlservice;

import java.util.Map;

public interface UpdatableSqlRegistry extends SqlRegistry {
    void update(String key, String sql) throws SqlUpdateFailureException;

    void update(Map<String, String> sqlMap) throws SqlUpdateFailureException;
}
