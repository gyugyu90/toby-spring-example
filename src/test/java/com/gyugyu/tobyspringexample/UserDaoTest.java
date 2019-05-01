package com.gyugyu.tobyspringexample;

import user.dao.DaoFactory;
import user.dao.UserDao;

import java.sql.SQLException;

public class UserDaoTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserDao dao = new DaoFactory().userDao();
        System.out.println(dao.get("whiteship").getName());

    }

}
