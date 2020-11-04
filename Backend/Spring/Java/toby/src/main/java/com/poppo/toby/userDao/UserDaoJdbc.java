package com.poppo.toby.userDao;

import com.poppo.toby.domain.Level;
import com.poppo.toby.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public UserDaoJdbc(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(User user) {
        this.jdbcTemplate.update(
                "insert into users(id, name, password, level, login, recommend) values(?, ?, ?, ?, ?, ?)",
                user.getId(), user.getName(), user.getPassword(), user.getLevel().toInt(),
                user.getLogin(), user.getRecommend()
                );
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
                "select * from users where id = ?",
                new Object[] {id},
                this::mapUser
        );
    }

    public Integer getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
                "select * from users order by id",
                this::mapUser
        );
    }

    private User mapUser(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setLevel(Level.of(resultSet.getInt("level")));
        user.setLogin(resultSet.getInt("login"));
        user.setRecommend(resultSet.getInt("recommend"));

        return user;
    }
}
