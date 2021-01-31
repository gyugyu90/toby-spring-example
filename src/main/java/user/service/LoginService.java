package user.service;

import org.springframework.beans.factory.annotation.Autowired;
import user.domain.LoginUser;

import javax.inject.Provider;
import java.util.Date;

public class LoginService {

    @Autowired
    Provider<LoginUser> loginUserProvider;

    public void login(Login login) {
        LoginUser loginUser = loginUserProvider.get();
        loginUser.setLoginId(login.id);
        loginUser.setLoginTime(new Date());
    }


    static class Login {
        String id;
    }
}
