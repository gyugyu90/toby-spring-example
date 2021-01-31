package user.service;

import org.springframework.beans.factory.annotation.Autowired;
import user.domain.LoginUser;

import java.util.Date;

public class LoginService {

    @Autowired
    LoginUser loginUser; // 스코프에 따라서 다른 오브젝트로 연결되는 프록시가 주입된다.

    public void login(Login login) {
        loginUser.setLoginId(login.id);
        loginUser.setLoginTime(new Date());
    }

    static class Login {
        String id;
    }
}