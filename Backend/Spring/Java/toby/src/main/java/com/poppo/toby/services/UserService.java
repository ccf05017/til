package com.poppo.toby.services;

import com.poppo.toby.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {
    void add(User user);
    void upgradeLevels();
}
