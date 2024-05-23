package com.example.NewsApplication.entity;

import com.example.NewsApplication.constants.StringConstants;
import com.example.NewsApplication.exception.InValidPasswordException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = StringConstants.USERNAME_SHOULD_NOT_EMPTY)
    private String username;


    @Column(unique = true)
    @NotBlank(message = StringConstants.EMAIL_SHOULD_NOT_EMPTY)
    private String email;

    @Column(unique = true)
    @Pattern(regexp = StringConstants.REG_MOB,message = StringConstants.INVALID_MOB)
    private String mobileNo;

    @NotBlank(message = StringConstants.PASSWORD_REQUIRED)
//    @Pattern(regexp = StringConstants.PASSWORD_PATTERN, message = StringConstants.PASSWORD_VIOLATION)
    private String password;

    private String roles;
//
    public void setPassword(String password) {
        if (isValidPassword(password)) {
            this.password=password;
        }
        else {
            throw new InValidPasswordException(StringConstants.PASSWORD_VIOLATION);

        }
    }

    private boolean isValidPassword(String password) {
        return password.matches(StringConstants.PASSWORD_PATTERN);
    }
}

