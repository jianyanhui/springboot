package com.example.demo.ctrl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//swagger的configuration
@Configuration
@EnableSwagger2
public class Swagger2 {
/*通过createRestApi函数来构建一个DocketBean
* 函数名，可以随意命名
* */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())//调研apiInfo方法，创建一个apiInfo实例，里面是展示文档页面信息内容
                .enable(true)//是否开启swagger
                .select()
                //控制暴露出去的路径下的实例
                //如果某个接口不想暴露，可以使用以下注解
                //@ApiIgnore,这样就不会暴露在Swagger2页面下
                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
                .paths(PathSelectors.any())
                .build();
    }
//构建api文档的详细函数信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("服务:发布为daocke镜像,权限管理，用户管理，页面管理，日志 后台 APIs")
                //描述
                .description("服务:发布为daocke镜像,权限管理，用户管理，页面管理，日志 后台")
                //.termsOfServiceUrl("http://192.168.1.198:10070/platformgroup/ms-admin")
                //创建人
                .contact("程序猿")
                //版本号
                .version("1.0")
                .build();
    }

}
