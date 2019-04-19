package com.example.demo.ctrl.dto;

import lombok.Data;

@Data
public class User {
    private String username;
    private String sex;
    private String password;
    private String age;
    private School school;
}
