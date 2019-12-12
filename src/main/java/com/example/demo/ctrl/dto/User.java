package com.example.demo.ctrl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户dto实体")
public class User {
    @ApiModelProperty(name="username",value="用户姓名",dataType = "String",required = false)
    private String username;
    @ApiModelProperty(name="sex",value="性别",dataType = "String",required = false)
    private String sex;
    @ApiModelProperty(name="password",value="密码",dataType = "String",required = false)
    private String password;
    @ApiModelProperty(name="age",value="年龄",dataType = "String",required = false)
    private String age;
    @ApiModelProperty(name="school",value="学校",dataType = "String",required = false)
    private School school;
}
