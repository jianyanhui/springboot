package com.example.demo.ctrl.controller;

import com.example.demo.ctrl.dto.User;
import com.example.demo.ctrl.util.LogToString;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//@Api将一个Controller标注为Swagger资源，tags:api分组标签，表示为同类api，如果tags没定义则value代替tags，如果两个都定义了则tags会替换value
@Api(value="测试Swagger",tags="测试Swagger详细描述")
@RestController //@ResponseBody注解无法返回视图jsp或html，但会把返回值变成json
public class SwaggerController1 {
//springboot接收参数的方式：https://blog.csdn.net/csyy140225/article/details/82998664
    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    @ApiOperation(value="获取用户详细信息,path获取入参", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String",paramType="path")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getUserById (@PathVariable(value="id",required = true) String id){
        System.out.println("getUserById获取所有入参id："+ id);
        return "哈哈";
    }

    //@ApiOperation注解说明方法的作用描述，value：对操作简单说明，notes：对操作详细说明
    @ApiOperation(value="获取用户信息,多个String做入参",notes="注意事项")
    @ApiImplicitParams({//入参数数组描述
            //@ApiImplicitParam,name:参数名称，value:参数简短描述,defaultValue:参数默认值，required：是否必传，dataType：参数类型（可为类名或基本数据类型），
            // paramType：参数请求类型，可选值有path（用@PathVariable注解接收参数）、query（@RequestParam）、body（@RequestBody）、header（@RequestHeader）、cookie（@CookieValue）、form（@RequestParam）
            @ApiImplicitParam(paramType="query",name="username",dataType="String",required=true,value="用户的姓名",defaultValue="zhaojigang"),
            @ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="用户的密码",defaultValue="wangna")
    })
    @ApiResponses({//响应数组
            //@ApiResponse,code:返回码，message：返回信息文本描述
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value="/getUser",method= RequestMethod.GET)//请求信息
    /*
    * springboot接收参数的几种方式
    * 第一类参数：
    * 1：@PathVariable:获取路径参数，取 url/{id}这种形式
    * 2：@RequestParam:获取查询参数，即url?name=这种形式或form表单形式
    * 第二类：Body参数
    * 1：@RequestBody:用于接收对象参数，比例Map、json、自定义对象,但不能接收form表单参数
    * 第三类：请求头及Cookie参数
    * 1: @RequestHeader:获取请求头参数
    * 2：@CookieValue:获取Cookie参数
    * */
    public String getUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println("getUser获取所有入参username："+ username+",password:"+password);
        return "404";
    }



    //@ApiOperation注解说明方法的作用描述，value：对操作简单说明，notes：对操作详细说明
    @ApiOperation(value="获取用户信息1，对象做入参",notes="注意事项1")
    @ApiImplicitParams({//入参数数组描述
            //@ApiImplicitParam,name:参数名称，value:参数简短描述,defaultValue:参数默认值，required：是否必传，dataType：参数类型（可为类名或基本数据类型），
            // paramType：参数请求类型，可选值有path（用@PathVariable注解接收参数）、query（@RequestParam）、body（@RequestBody）、header（@RequestHeader）、cookie（@CookieValue）、form（@RequestParam）
            @ApiImplicitParam(paramType="body",name="user",dataType="User",required=true,value="用户的姓名",defaultValue="zhaojigang")
    })
    @ApiResponses({//响应数组
            //@ApiResponse,code:返回码，message：返回信息文本描述
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value="/getUser1",method= RequestMethod.POST,consumes = {"application/json;charset=utf-8"})//请求信息
    public String getUser1(@RequestBody User user) {
        System.out.println("demo1获取所有对象入参request："+ LogToString.objectToString(user));
        return "成功哈哈哈哈1";
    }

    //@ApiOperation注解说明方法的作用描述，value：对操作简单说明，notes：对操作详细说明
    @ApiOperation(value="获取用户信息，批量获取post入参",notes="注意事项")
    @ApiImplicitParams({//入参数数组描述
            //@ApiImplicitParam,name:参数名称，value:参数简短描述,defaultValue:参数默认值，required：是否必传，dataType：参数类型（可为类名或基本数据类型），
            // paramType：参数请求类型，可选值有path（用@PathVariable注解接收参数）、query（@RequestParam）、body（@RequestBody）、header（@RequestHeader）、cookie（@CookieValue）、form（@RequestParam）
            @ApiImplicitParam(paramType="query",name="username",dataType="String",required=true,value="用户的姓名",defaultValue="zhaojigang"),
            @ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="用户的密码",defaultValue="wangna")
    })
    @ApiResponses({//响应数组
            //@ApiResponse,code:返回码，message：返回信息文本描述
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value="/demo1",method= RequestMethod.POST)//请求信息
    //request获取参数
    public String demo1(HttpServletRequest request) {
        Map<String,String[]> map=request.getParameterMap();//批量获取参数
        Map<String,Object> map2=new HashMap<String,Object>();
        for (Map.Entry<String,String[]> entry:map.entrySet()){
            for (String s:entry.getValue()){
                map2.put(entry.getKey(),s);
            }
        }
        System.out.println("demo1获取所有post入参request："+ LogToString.objectToString(map2));
        return "demo1成功哈哈哈哈1";
    }


    //@ApiOperation注解说明方法的作用描述，value：对操作简单说明，notes：对操作详细说明
    @ApiOperation(value="获取用户信息，批量获取get入参",notes="注意事项")
    @ApiImplicitParams({//入参数数组描述
            //@ApiImplicitParam,name:参数名称，value:参数简短描述,defaultValue:参数默认值，required：是否必传，dataType：参数类型（可为类名或基本数据类型），
            // paramType：参数请求类型，可选值有path（用@PathVariable注解接收参数）、query（@RequestParam）、body（@RequestBody）、header（@RequestHeader）、cookie（@CookieValue）、form（@RequestParam）
            @ApiImplicitParam(paramType="query",name="username",dataType="String",required=true,value="用户的姓名",defaultValue="zhaojigang"),
            @ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="用户的密码",defaultValue="wangna")
    })
    @ApiResponses({//响应数组
            //@ApiResponse,code:返回码，message：返回信息文本描述
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value="/demo2",method= RequestMethod.GET)//请求信息
    //request获取参数
    public String demo2(HttpServletRequest request) {
        Map<String,String[]> map=request.getParameterMap();//批量获取参数
        Map<String,Object> map2=new HashMap<String,Object>();
        for (Map.Entry<String,String[]> entry:map.entrySet()){
            for (String s:entry.getValue()){
                map2.put(entry.getKey(),s);
            }
        }
        System.out.println("demo2获取所有get入参request："+ LogToString.objectToString(map2));
        return "demo2成功哈哈哈哈1";
    }


    //@ApiOperation注解说明方法的作用描述，value：对操作简单说明，notes：对操作详细说明
    @ApiOperation(value="获取用户信息,批量获取json入参",notes="注意事项")
    @ApiImplicitParams({//入参数数组描述
            //@ApiImplicitParam,name:参数名称，value:参数简短描述,defaultValue:参数默认值，required：是否必传，dataType：参数类型（可为类名或基本数据类型），
            // paramType：参数请求类型，可选值有path（用@PathVariable注解接收参数）、query（@RequestParam）、body（@RequestBody）、header（@RequestHeader）、cookie（@CookieValue）、form（@RequestParam）
            @ApiImplicitParam(paramType="query",name="username",dataType="String",required=true,value="用户的姓名",defaultValue="zhaojigang"),
            @ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="用户的密码",defaultValue="wangna")
    })
    @ApiResponses({//响应数组
            //@ApiResponse,code:返回码，message：返回信息文本描述
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value="/demo3",method= RequestMethod.POST,produces = "application/json")//请求信息
    //request获取参数
    public String demo3(HttpServletRequest request,@RequestBody HashMap<String,Object> reqMap) {//@RequestParam Map<String,Object> reqMap,
        System.out.println("demo3获取所有入参reqMap："+ LogToString.objectToString(reqMap));//指定json传参用map接收数据
        Map<String,String[]> map=request.getParameterMap();//批量获取@RequestParam参数
        Map<String,Object> map2=new HashMap<String,Object>();
        for (Map.Entry<String,String[]> entry:map.entrySet()){
            for (String s:entry.getValue()){
                map2.put(entry.getKey(),s);
                break;
            }
        }
        System.out.println("demo3获取所有入参request："+ LogToString.objectToString(map2));
        return "demo3成功哈哈哈哈1";
    }

    /**
     * swagger注解放在入参dto上
     * @param u
     * @return
     */
    @ApiOperation(value="获取用户信息,批量获取json入参",notes="注意事项")
    @RequestMapping(value="/demo4",method= RequestMethod.GET)//请求信息
    public String demo4(User u){

        return "成功";
    }

}
