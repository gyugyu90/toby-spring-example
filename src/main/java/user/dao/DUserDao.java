package user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DUserDao extends UserDao {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // D사 DBConnection 생성 코드
        return null;
    }
}
