package com.sb.helper;

import java.sql.Timestamp;

import com.sb.entity.User;
import com.sb.form.UserForm;

public class UserHelper {

    public static User convert(UserForm userForm) {
        User user = new User();
        user.setName(userForm.getName());
        user.setMailAddress(userForm.getMailAddress());
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        return user;
    }

    public static void setTimestamp(User user) {
        user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
    }
}
