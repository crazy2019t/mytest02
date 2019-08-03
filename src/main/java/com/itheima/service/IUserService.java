package com.itheima.service;

import com.itheima.domain.User;
import com.itheima.exception.UsernameException;

public interface IUserService {
    User checkUsername(String username);

    void doRegister(User user) throws Exception;

    void doActive(String code);

    User doLogin(String username, String password) throws Exception;
}
