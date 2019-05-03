package com.gyugyu.tobyspringexample;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import user.dao.DaoFactory;
import user.dao.UserDao;
import user.domain.User;

import java.sql.SQLException;
import java.util.UUID;

public class UserDaoTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

//        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        UserDao dao = context.getBean("userDao", UserDao.class);
        System.out.println(dao.get("whiteship").getName());

        User user1 = new User();
        String uuid = UUID.randomUUID().toString().substring(0,10);
        user1.setId(uuid);
        user1.setName("규규");
        user1.setPassword("password");

        dao.add(user1);

        User user2 = dao.get(uuid);

        if(!user1.getName().equals(user2.getName())) {
            System.out.println("테스트 실패 (name)");
        } else if(!user1.getPassword().equals(user2.getPassword())) {
            System.out.println("테스트 실패 (password)");
        } else {
            System.out.println("조회 테스트 성공");
        }

    }

}
