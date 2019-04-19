package com.example.demo.ctrl.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
/*swagger说明：教程：
https://github.com/aalansehaiyang/technology-talk/blob/master/other/swagger.md
http://www.cnblogs.com/java-zhao/p/5348113.html
@Api：用在类上，说明该类的作用
@ApiOperation：用在方法上，说明方法的作用
@ApiImplicitParams：用在方法上包含一组参数说明
@ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
        paramType：参数放在哪个地方
        header-->请求参数的获取：@RequestHeader
query-->请求参数的获取：@RequestParam
path（用于restful接口）-->请求参数的获取：@PathVariable
body（不常用）
        form（不常用）
        name：参数名
        dataType：参数类型
        required：参数是否必须传
        value：参数的意思
        defaultValue：参数的默认值
@ApiResponses：用于表示一组响应
@ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
        code：数字，例如400
        message：信息，例如"请求参数没填好"
        response：抛出异常的类
@ApiModel：描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，请求参数无法使用@ApiImplicitParam注解进行描述的时候）
@ApiModelProperty：描述一个model的属性
        以上这些就是最常用的几个注解了。

        测试：

启动服务，浏览器输入"http://localhost:8080/swagger-ui.html"
*/
@RestController
@RequestMapping("/user")
@Api("SwaggerController相关api")
public class SwaggerController {

    @ApiOperation(value="获取用户信息",notes="注意事项")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="username",dataType="String",required=true,value="用户的姓名",defaultValue="zhaojigang"),
            @ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="用户的密码",defaultValue="wangna")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value="/getUser",method= RequestMethod.GET)
    public String getUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        return "成功哈哈哈哈";
    }

}
