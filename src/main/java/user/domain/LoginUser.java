package user.domain;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.Date;

@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class LoginUser {
    private String loginId;
    private String name;
    private Date loginTime;
}
