package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication //springboot开启自动配置核心注解
@EnableScheduling //定时任务
@EnableSwagger2    //启动swagger注解，引入了一个注解@EnableSwagger2来启动swagger注解。（启动该注解使得用在controller中的swagger注解生效，覆盖的范围由@ComponentScan的配置来指定，这里默认指定为根路径"com.xxx.firstboot"下的所有controller）
@EnableFeignClients  // 开启Feign的支持功能
public class SpringbootDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDemoApplication.class, args);
	}

}
