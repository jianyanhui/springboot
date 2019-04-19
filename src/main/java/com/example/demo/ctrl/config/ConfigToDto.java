package com.example.demo.ctrl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置文件映射对象
 * @author DELL
 */
@Data
@Component  //声明是一个bean
@PropertySource("classpath:my.properties")   //指定文件名
@ConfigurationProperties(prefix = "obj")  //指定前缀
public class ConfigToDto {

    private String name;
    private String age;
    // 集合必须初始化，如果找不到就是空集合，会报错
    private List<String> className = new ArrayList<String>();

}
