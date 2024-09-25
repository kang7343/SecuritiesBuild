package com.kis.service;

import com.kis.entity.User;

public interface UserService {

    public void insertUser(User user);

    boolean isDuplicateuserMail(String mailAddress);

    boolean isDuplicateuserName(String username);

    User selectByUser(String username);

    User selectById(Integer userId);

}
