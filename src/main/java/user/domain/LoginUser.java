package user.domain;

import lombok.Data;
import org.springframework.context.annotation.Scope;

import java.util.Date;

@Scope("session")
@Data
public class LoginUser {
    private String loginId;
    private String name;
    private Date loginTime;
}
