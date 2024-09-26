package com.sb.repository;

import org.apache.ibatis.annotations.Mapper;

import com.sb.entity.User;

@Mapper
public interface UserRepository {

    public User selectuserByMail(String mailAddress);

    public User selectUserByUserName(String username);

    public void insertUser(User user);

    public User selectByUser(String username);

    public User selectById(Integer userId);

}
