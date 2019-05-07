package user.dao;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import user.domain.Level;
import user.domain.User;
import user.exception.DuplicateUserIdException;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoJdbc implements UserDao {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper = (rs, i) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setLevel(Level.valueOf(rs.getInt("level")));
        user.setLogin(rs.getInt("login"));
        user.setRecommend(rs.getInt("recommend"));
        return user;
    };

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) throws DuplicateUserIdException {

        try {
            jdbcTemplate.update("INSERT INTO USERS(id, name, password, level, login, recommend) VALUES(?,?,?,?,?,?)",
                    user.getId(), user.getName(), user.getPassword(),
                    user.getLevel().intValue(), user.getLogin(), user.getRecommend());
        } catch (DuplicateKeyException e) {
            throw new DuplicateUserIdException(e); // 예외 전환.. 원인이 되는 예외를 중첩
        }
    }

    public User get(String id){
        return jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, userMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return jdbcTemplate.query(connection -> connection.prepareStatement("select count(*) from users"), resultSet -> {
            resultSet.next();
            return resultSet.getInt(1);
        });
        // queryForInt는 없어졌나..
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id", userMapper);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(
                "update users set name = ?, password = ?, level = ?, login = ?, recommend = ? where id = ?",
                user.getName(), user.getPassword(),
                user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
                user.getId());
    }
}
