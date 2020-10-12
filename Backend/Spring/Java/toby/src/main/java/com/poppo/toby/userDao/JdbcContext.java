package com.poppo.toby.userDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class JdbcContext {
    private DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makePreparedStatement(connection);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {

                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    public void executeQuery(final String query) throws SQLException {
        this.workWithStatementStrategy(connection -> connection.prepareStatement(query));
    }

    public void executeQueryWithArgs(final String query, final String... args) throws SQLException {
        List<String> arguments = Arrays.asList(args);

        this.workWithStatementStrategy((connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (String argument : arguments) {
                preparedStatement.setString(arguments.indexOf(argument) + 1, argument);
            }

            return preparedStatement;
        }));
    }
}
