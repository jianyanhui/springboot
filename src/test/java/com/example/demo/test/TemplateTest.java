package com.example.demo.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//测试类模板
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TemplateTest {
    @Autowired
    private Environment env;
    @Value("${spring.thymeleaf.prefix}")
    private String  ss;
    @Before
    public void init(){
        System.out.println("----------开始进入测试--------------");
    }

    @After
    public void after(){
        System.out.println("----------结束测试--------------");
    }


    @Test
    public void enter(){
        System.out.println("----------测试中--------------");
        String s=env.getProperty("spring.thymeleaf.prefix","");
        System.out.println("s结果为："+s);
        System.out.println("ss结果为："+ss);
    }

//    public static void main(String[] args) {
//        System.out.println();
//    }
}
