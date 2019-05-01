package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        // D 사의 독자적인 방법으로 Connection을 생성하는 코드
        Class.forName("com.mysql.cj.jdbc.Driver"); // com.mysql.jdbc.Driver => com.mysql.cj.jdbc.Driver 로 쓰는 것 권장
        return DriverManager.getConnection("jdbc:mysql://localhost/tobyspring?serverTimezone=Asia/Seoul", "root", "password");
    }
}
