package com.kis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kis.entity.User;
import com.kis.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void insertUser(User user) {
        try {
            String pass = encoder.encode(user.getPassword());
            user.setPassword(pass);
            userRepository.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isDuplicateuserMail(String mailAddress) {
        try {
            User user = null;
            user = userRepository.selectuserByMail(mailAddress);

            boolean existinguser = true;
            if (user == null) {
                existinguser = false;
            }
            return existinguser;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isDuplicateuserName(String username) {
        try {
            User user = null;
            user = userRepository.selectUserByUserName(username);

            boolean existinguser = true;
            if (user == null) {
                existinguser = false;
            }
            return existinguser;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public User selectByUser(String username) {
        return userRepository.selectUserByUserName(username);
    }

    @Override
    public User selectById(Integer userId) {
        return userRepository.selectById(userId);
    }

}
