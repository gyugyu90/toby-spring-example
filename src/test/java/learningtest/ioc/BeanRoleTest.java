package learningtest.ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanRoleTest {
    @Test
    public void name() {
        ApplicationContext context = new GenericXmlApplicationContext(BeanRoleTest.class, "/beanrole.xml");
        SimpleConfig sc = context.getBean(SimpleConfig.class);
        sc.hello1.sayHello();
    }
}
