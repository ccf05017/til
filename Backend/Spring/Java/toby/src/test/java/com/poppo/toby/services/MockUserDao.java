package com.poppo.toby.services;

import com.poppo.toby.domain.User;
import com.poppo.toby.userDao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class MockUserDao implements UserDao {
    private List<User> users;
    private List<User> updated = new ArrayList<>();

    public MockUserDao(final List<User> users) {
        this.users = users;
    }

    public List<User> getUpdated() {
        return this.updated;
    }

    @Override
    public void add(final User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User get(final String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> getAll() {
        return this.users;
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(final User user) {
        updated.add(user);
    }
}
