package user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class NUserDao extends UserDao {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // N사 DBConnection 생성 코드
        return null;
    }
}
