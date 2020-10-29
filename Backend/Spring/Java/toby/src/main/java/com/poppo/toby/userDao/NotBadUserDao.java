package com.poppo.toby.userDao;

import com.poppo.toby.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NotBadUserDao {
    private JdbcTemplate jdbcTemplate;

    public NotBadUserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

//    public void add(User user) throws SQLException {
//        jdbcContext.executeQueryWithArgs(
//                "insert into users(id, name, password) values(?,?,?)",
//                user.getId(), user.getName(), user.getPassword());
//    }

    public void add(User user) {
        this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
                user.getId(), user.getName(), user.getPassword());
    }

//    public void deleteAll() throws SQLException {
//        jdbcContext.executeQuery("delete from users");
//    }

    // createPreparedStatement 사용 버전
//    public void deleteAll() {
//        this.jdbcTemplate.update(
//                (Connection connection) -> connection.prepareStatement("delete from users")
//        );
//    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

//    public User get(String id) throws SQLException {
//        Connection connection = dataSource.getConnection();
//
//        PreparedStatement preparedStatement = connection.prepareStatement(
//                "select * from users where id = ?");
//
//        preparedStatement.setString(1, id);
//
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        User user = null;
//        if (resultSet.next()) {
//            user = new User();
//            user.setId(resultSet.getString("id"));
//            user.setId(resultSet.getString("name"));
//            user.setId(resultSet.getString("password"));
//        }
//
//        resultSet.close();
//        preparedStatement.close();
//        connection.close();
//
//        if (user == null) {
//            throw new EmptyResultDataAccessException(1);
//        }
//
//        return user;
//    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
                "select * from users where id = ?",
                new Object[] {id},
                (ResultSet resultSet, int rowNum) -> {
                    User user = new User();
                    user.setId(resultSet.getString("id"));
                    user.setName(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                }
        );
    }

//    public int getCount() throws SQLException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = dataSource.getConnection();
//            preparedStatement = connection.prepareStatement("select count(*) from users");
//            resultSet = preparedStatement.executeQuery();
//
//            resultSet.next();
//            int count = resultSet.getInt(1);
//
//            return count;
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            if (resultSet != null) {
//                try {
//                    resultSet.close();
//                } catch (SQLException e) {
//
//                }
//            }
//            if (preparedStatement != null) {
//                try {
//                    preparedStatement.close();
//                } catch (SQLException e) {
//
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//
//                }
//            }
//        }
//    }

//    public int getCount() throws SQLException {
//        // 이 부분 warnning에 대해 아볼 것
//        return this.jdbcTemplate.query(
//                (Connection connection) -> connection.prepareStatement("select count(*) from users"),
//                (ResultSet resultSet) -> {
//                    resultSet.next();
//                    return resultSet.getInt(1);
//                }
//        );
//    }

    public Integer getCount() throws SQLException {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
                "select * from users order by id",
                (ResultSet resultSet, int rowNum) -> {
                    User user = new User();
                    user.setId(resultSet.getString("id"));
                    user.setPassword(resultSet.getString("password"));
                    user.setName(resultSet.getString("name"));
                    return user;
                }
        );
    }
}
