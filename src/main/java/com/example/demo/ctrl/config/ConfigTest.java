package com.example.demo.ctrl.config;

import com.example.demo.ctrl.dto.School;
import com.example.demo.ctrl.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/*springboot读取配置文件的几种方式
教程：https://www.cnblogs.com/duanxz/p/4520627.html
配置项映射成dto
使用@Value+@PropertySource读取其它配置文件（多个）内容
* */

//@PropertySource指定读取的自定义配置文件，默认的application.properties文件可不需要指定
@PropertySource({"classpath:my.properties","classpath:config.properties"})
@Configuration
public class ConfigTest {
    @Autowired
    private Environment env;

    @Value("测试常量")
    private String chang;

    @Autowired
    private ConfigToDto ConfigToDto;//读取配置文件转dto测试

    @Value("${my.username}")  //读取默认application.properties文件
    private String username;
    @Value("${my.sex}")
    private String sex;
    @Value("${my.school.name}")
    private String schoolName;
    @Value("${my.school.address}")
    private String schoolAddress;
    @Value("${my.school.age:默认学习年龄}")  //冒号间隔设置默认值
    private String schoolage;

    @Value("${mySchoolis.test}")  //指定读取的自定义配置文件my.properties
    private String mySchoolisTest;

    @Bean
    public User autoUser(){//通过dto塞值提取出一个单例的bean供外部调用
        User u=new User();
        u.setUsername(username);
        u.setSex(sex);
        School s=new School();
        s.setName(schoolName);
        s.setAddress(schoolAddress);
        s.setMySchoolisTest(mySchoolisTest);
        s.setSchoolage(schoolage);
        s.setEnvTestName(env.getProperty("env.test.name","通过env获取配置文件测试默认值"));
        s.setChang(chang);
        s.setConfigToDto(ConfigToDto);
        u.setSchool(s);
        return u;
    }
}
