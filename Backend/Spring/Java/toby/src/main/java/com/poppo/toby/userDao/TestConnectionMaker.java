package com.poppo.toby.userDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnectionMaker implements ConnectionMaker {
    @Override
    public Connection makeConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:h2:tcp://localhost/~/data/testdb", "sa", "");
    }
}
