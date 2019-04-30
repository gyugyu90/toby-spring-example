package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {

    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver"); // com.mysql.jdbc.Driver => com.mysql.cj.jdbc.Driver 로 쓰는 것 권장
        return DriverManager.getConnection("jdbc:mysql://localhost/tobyspring?serverTimezone=Asia/Seoul", "root", "password");
    }

}
