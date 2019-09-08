package com.gyugyu.tobyspringexample;

import learningtest.spring.factorybean.Message;
import learningtest.spring.factorybean.MessageFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/FactoryBeanTest-context.xml")
public class FactoryBeanTest {

    @Autowired
    ApplicationContext context;

    @Test
    public void getMessageFromFactoryBean() {
        Object message = context.getBean("message");

        assertThat(message, instanceOf(Message.class));
        assertThat(((Message)message).getText(), is("Factory Bean hhh"));
    }

    @Test
    public void getFactoryBean() throws Exception {
        Object factory = context.getBean("&message");
        assertThat(factory, instanceOf(MessageFactoryBean.class));
    }
}
