package com.example.demo.ctrl.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.ctrl.config.http.restTemplate.HttpClient;
import com.example.demo.ctrl.config.http.restTemplate.PortalHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回字符串
 */
@RestController
@Slf4j
public class DemoController {
    @Autowired
    private HttpClient httpClient;



    /**
     * http调接口测试>..
     * @return
     */
    @RequestMapping("/httpTest")
    public String httpTest(){
        JSONObject json=null;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("id","12345");
        PortalHttpRequest portalHttpRequest=PortalHttpRequest.buildPost("http://localhost:8080/getName", map);
        portalHttpRequest.setMediaType(MediaType.APPLICATION_JSON_UTF8);
        String result=httpClient.exec(portalHttpRequest,String.class);
        log.info("http调接口测试出参："+result);
        return result;
    }


    @RequestMapping(value = "/getName",method = RequestMethod.POST)
    public String getName(){
        return "我的名字是小明！";
    }

}
