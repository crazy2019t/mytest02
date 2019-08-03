package com.itheima.dao;

import com.itheima.domain.User;

public interface IUserDao {
    User checkUsername(String username);

    boolean addUser(User user);

    User findUserByCode(String code);

    void update(User user);
}
