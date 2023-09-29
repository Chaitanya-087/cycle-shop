package com.spring.learningRest.business;

import lombok.Data;

@Data
public class LoginBody {
    private String username;
    private String password;
}
