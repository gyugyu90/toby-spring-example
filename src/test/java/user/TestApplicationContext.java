package user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import user.UserServiceTest.TestUserService;
import user.service.DummyMailSender;
import user.service.UserService;

@Configuration
@Profile("test")
public class TestApplicationContext {

    @Bean
    public UserService testUserService() {
        return new TestUserService();
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }

}
