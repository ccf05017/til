package com.poppo.toby.services;

import com.poppo.toby.domain.User;

public interface UserService {
    void add(User user);
    void upgradeLevels();
}
