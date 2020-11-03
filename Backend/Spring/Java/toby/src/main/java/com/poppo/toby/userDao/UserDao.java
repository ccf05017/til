package com.poppo.toby.userDao;

import com.poppo.toby.domain.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    Integer getCount();
}
