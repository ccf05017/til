package com.poppo.toby.trashdaoV2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NUserDao extends TrashUserDaoV2 {
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:h2:tcp://localhost/~/data/toby", "sa", "");
    }
}
