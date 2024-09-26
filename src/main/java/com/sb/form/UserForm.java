package com.sb.form;

import java.sql.Timestamp;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserForm {
    private Integer id;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @Email
    @Size(min = 4, max = 100)
    private String mailAddress;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "アカウント名は半角英数字のみ使用できます")
    @Size(min = 5, max = 100)
    private String username;

    @Size(min = 5, max = 100)
    private String password;

    private Timestamp createdDate;

}
