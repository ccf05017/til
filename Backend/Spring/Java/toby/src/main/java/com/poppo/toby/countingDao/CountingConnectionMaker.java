package com.poppo.toby.countingDao;

import com.poppo.toby.trashDaoV3.ConnectionMaker;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
    int count = 0;
    private ConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(ConnectionMaker connectionMaker) {
        this.realConnectionMaker = connectionMaker;
    }

    public int getCount() {
        return count;
    }

    @Override
    public Connection makeConnection() throws SQLException {
        this.count++;
        return realConnectionMaker.makeConnection();
    }
}
