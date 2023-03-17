package todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import todolist.domain.User;
import todolist.services.interfaces.IUserService;

@Service
public class UserService implements IUserService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createUser(User user) {
        String query = "INSERT INTO dbo.Users VALUES(" + user.getId() + ",'" + user.getEmail() + "','" + user.getPassword() + "')";
        return jdbcTemplate.update(query);
    }

    @Override
    public User getUser(long id) {
        String query = "SELECT * FROM dbo.Users WHERE Users.Id = ?";
        return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(User.class), id);
    }

    @Override
    public int updateUser(User updateUser, long id) {
        String query = "UPDATE dbo.Users SET Email = '" + updateUser.getEmail() + "', Password = '" + updateUser.getPassword() +
                       "' WHERE id =" + id;
        return jdbcTemplate.update(query);
    }

    @Override
    public int deleteUser(long id) {
        String query = "DELETE FROM dbo.USERS WHERE id = " + id;
        return jdbcTemplate.update(query);
    }

    @Override
    public void createTableUser() {
        jdbcTemplate.execute("IF OBJECT_ID('dbo.Users', 'U') IS NOT NULL DROP TABLE dbo.Users");
        jdbcTemplate.execute("CREATE TABLE Users(Id BIGINT, Email VARCHAR(30), Password VARCHAR(30))");
    }
}
