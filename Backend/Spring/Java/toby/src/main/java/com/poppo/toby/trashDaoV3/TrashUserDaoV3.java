package com.poppo.toby.trashDaoV3;

import com.poppo.toby.domain.User;

import java.sql.*;

public class TrashUserDaoV3 {
    private ConnectionMaker connectionMaker;

    public TrashUserDaoV3() {
        // 클래스 안에 구체적인 클래스 이름이 드러난다.
        // 뭘 써야할지 알고 있어야 되기 때문에 의존적이다.
        this.connectionMaker = new NConnectionMaker();
    }

    public void add(User user) throws SQLException {
        Connection connection = connectionMaker.makeConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into users(id, name, password) values(?,?,?)");

        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    public User get(String id) throws SQLException {
        Connection connection = connectionMaker.makeConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from users where id = ?");

        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return user;
    }
}
