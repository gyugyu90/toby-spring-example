package learningtest.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class SimpleConfig {

    @Autowired
    Hello1 hello1;

    @Bean
    Hello1 hello1() {
        return new Hello1();
    }

    public class Hello1 {
        @PostConstruct
        public void init() {
            System.out.println("hello1 init");
        }

        public void sayHello() {
            System.out.println("I say hellooooo");
        }
    }

}
