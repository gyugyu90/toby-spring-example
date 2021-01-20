package learningtest.ioc;

import learningtest.ioc.bean.AnnotatedHello;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class RegisteringBeansTest {
    @Test
    public void registerBean() {
        StaticApplicationContext ac = new StaticApplicationContext();
        ac.registerSingleton("hello1", Hello.class);

        Hello hello1 = ac.getBean("hello1", Hello.class);
        assertThat(hello1, is(notNullValue()));

        // <bean class="learningtest.ioc.Hello" /> 에 해당하는 메타 정보
        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);

        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        ac.registerBeanDefinition("hello2", helloDef);

        Hello hello2 = ac.getBean("hello2", Hello.class);
        assertThat(hello2.sayHello(), is("Hello Spring"));

        assertThat(hello1, is(not(hello2)));
        assertThat(ac.getBeanFactory().getBeanDefinitionCount(), is(2));
    }

    @Test
    public void registerBeanWithDependency() {
        StaticApplicationContext ac = new StaticApplicationContext();

        ac.registerBeanDefinition("printer", new RootBeanDefinition(StringPrinter.class));

        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));

        ac.registerBeanDefinition("hello", helloDef);

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }

    @Test
    public void genericApplicationContext() {
        GenericApplicationContext ac = new GenericApplicationContext();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
        reader.loadBeanDefinitions("classpath:/genericApplicationContext.xml");
        ac.refresh();

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertThat(ac.getBean("printer").toString(), is("Hello Summer"));
    }

    @Test
    public void testContextInheritance() {
        GenericApplicationContext parent = new GenericXmlApplicationContext("classpath:/parentContext.xml");
        GenericApplicationContext child = new GenericApplicationContext(parent);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
        reader.loadBeanDefinitions("classpath:/childContext.xml");
        child.refresh();

        Printer printer = child.getBean("printer", Printer.class);
        assertThat(printer, is(notNullValue()));

        Hello hello = child.getBean("hello", Hello.class);
        assertThat(hello, is(notNullValue()));

        hello.print();
        assertThat(printer.toString(), is("Hello Child"));
    }

    @Test
    public void simpleBeanScanning() {
        ApplicationContext context = new AnnotationConfigApplicationContext("learningtest.ioc");
        AnnotatedHello hello = context.getBean("annotatedHello", AnnotatedHello.class);

        assertThat(hello, is(notNullValue()));
    }

    @Test
    public void simpleBeanScanningFromConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AnnotatedHelloConfig.class);
        AnnotatedHello hello = context.getBean("annotatedHello", AnnotatedHello.class);

        assertThat(hello, is(notNullValue()));

        AnnotatedHelloConfig config = context.getBean("annotatedHelloConfig", AnnotatedHelloConfig.class);
        assertThat(config, is(notNullValue()));
    }

    @Test
    public void singletonScope() {

        ApplicationContext context = new AnnotationConfigApplicationContext(SingletonBean.class, SingletonClientBean.class);

        Set<SingletonBean> beans = new HashSet<>(); // set은 중복을 허용하지 않으므로 같은 오브젝트는 여러 번 추가해도 한 개만 남는다.

        beans.add(context.getBean(SingletonBean.class));
        beans.add(context.getBean(SingletonBean.class)); // DL에서 싱글톤 확인
        assertThat(beans.size(), is(1));

        beans.add(context.getBean(SingletonClientBean.class).bean1);
        beans.add(context.getBean(SingletonClientBean.class).bean2); // DI에서 싱글톤 확인
        assertThat(beans.size(), is(1));

    }

    static class SingletonBean {}

    static class SingletonClientBean {
        @Autowired SingletonBean bean1;
        @Autowired SingletonBean bean2;
    }

    @Test
    public void prototypeScope() {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                PrototypeBean.class,
                PrototypeClientBean.class
        );

        Set<PrototypeBean> bean = new HashSet<>();

        bean.add(context.getBean(PrototypeBean.class));
        assertThat(bean.size(), is(1));
        bean.add(context.getBean(PrototypeBean.class));
        assertThat(bean.size(), is(2));

        bean.add(context.getBean(PrototypeClientBean.class).bean1);
        assertThat(bean.size(), is(3));
        bean.add(context.getBean(PrototypeClientBean.class).bean2);
        assertThat(bean.size(), is(4));

    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    static class PrototypeBean {}

    static class PrototypeClientBean {
        @Autowired PrototypeBean bean1;
        @Autowired PrototypeBean bean2;
    }
}