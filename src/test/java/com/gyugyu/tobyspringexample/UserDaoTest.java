package com.gyugyu.tobyspringexample;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import user.dao.DaoFactory;
import user.dao.UserDao;
import user.domain.User;

import java.sql.SQLException;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserDaoTest {

    @Test
    public void addAndGet() throws SQLException {

//        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        UserDao dao = context.getBean("userDao", UserDao.class);

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        User user1 = new User();
        String uuid = UUID.randomUUID().toString().substring(0,10);
        user1.setId(uuid);
        user1.setName("규규");
        user1.setPassword("password");

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        User user2 = dao.get(uuid);

        assertThat(user2.getName(), is(user1.getName()));
        assertThat(user2.getPassword(), is(user1.getPassword()));

    }

}
