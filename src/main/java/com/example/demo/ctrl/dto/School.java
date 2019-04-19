package com.example.demo.ctrl.dto;

import lombok.Data;

@Data
public class School {
    private String name;
    private String address;
    private String mySchoolisTest;
    private String schoolage;
    private String envTestName;
    private String chang;//常量
    private com.example.demo.ctrl.config.ConfigToDto ConfigToDto;
}
