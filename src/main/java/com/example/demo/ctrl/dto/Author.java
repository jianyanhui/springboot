package com.example.demo.ctrl.dto;

import lombok.Data;

/**
 * 登录用户信息
 */
@Data
public class Author {

    private String ip;
    //来源
    private String from;
    //登录账户
    private String uid;
    //登录token
    private String token;
    //登录手机号
    private String mobile;
    //媒体来源
    private String mediaSourse;
    //环境，用于区分web、微信、移动端
    private String environment;

}
