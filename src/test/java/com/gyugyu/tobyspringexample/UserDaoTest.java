package com.gyugyu.tobyspringexample;

import user.dao.ConnectionMaker;
import user.dao.DConnectionMaker;
import user.dao.UserDao;

import java.sql.SQLException;

public class UserDaoTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ConnectionMaker connectionMaker = new DConnectionMaker();
        UserDao dao = new UserDao(connectionMaker); // 사용할 ConnectionMaker 타입의 오브젝트 제공.. 의존관계 설정 효과
    }

}
