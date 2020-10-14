package user.dao;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import user.domain.Level;
import user.domain.User;
import user.exception.DuplicateUserIdException;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class UserDaoJdbc implements UserDao {

    private String sqlAdd;
    private String sqlGet;
    private String sqlDeleteAll;
    private String sqlGetCount;
    private String sqlGetAll;
    private String sqlUpdate;

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper = (rs, i) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setLevel(Level.valueOf(rs.getInt("level")));
        user.setLogin(rs.getInt("login"));
        user.setRecommend(rs.getInt("recommend"));
        user.setEmail(rs.getString("email"));
        return user;
    };

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setSqlAdd(String sqlAdd) {
        this.sqlAdd = sqlAdd;
    }

    public void setSqlGet(String sqlGet) {
        this.sqlGet = sqlGet;
    }

    public void setSqlDeleteAll(String sqlDeleteAll) {
        this.sqlDeleteAll = sqlDeleteAll;
    }

    public void setSqlGetCount(String sqlGetCount) {
        this.sqlGetCount = sqlGetCount;
    }

    public void setSqlGetAll(String sqlGetAll) {
        this.sqlGetAll = sqlGetAll;
    }

    public void setSqlUpdate(String sqlUpdate) {
        this.sqlUpdate = sqlUpdate;
    }

    public void add(final User user) throws DuplicateUserIdException {

        try {
            jdbcTemplate.update(
                    sqlAdd,
                    user.getId(), user.getName(), user.getPassword(),
                    user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail());
        } catch (DuplicateKeyException e) {
            throw new DuplicateUserIdException(e); // 예외 전환.. 원인이 되는 예외를 중첩
        }
    }

    public User get(String id){
        return jdbcTemplate.queryForObject(sqlGet, new Object[]{id}, userMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update(sqlDeleteAll);
    }

    public int getCount() {
        return jdbcTemplate.query(connection -> connection.prepareStatement(sqlGetCount), resultSet -> {
            resultSet.next();
            return resultSet.getInt(1);
        });
        // queryForInt는 없어졌나..
    }

    public List<User> getAll() {
        return jdbcTemplate.query(sqlGetAll, userMapper);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(
                sqlUpdate,
                user.getName(), user.getPassword(),
                user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
                user.getId());
    }
}
