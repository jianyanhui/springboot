package com.example.demo.ctrl.config.http.restTemplate;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Map;
@Getter
@ToString
public class PortalHttpRequest {
    /**
    请求类型：post、get
     */
    private HttpMethod method;
    /**
     请求内容类型：比如application/json，form表单提交之类的
     */
    private MediaType mediaType;
    /**
     * 请求url
     */
    private  String url;
    //支持两种参数,map或object-----------------------
    /**
     * post请求参数类型
     */
    private PostOptionType postOptionType;
    /**
     * 请求参数map
     */
    private Map<String,Object> paramMap;
    /**
     * 请求参数Object
     */
    private Object obj;
    /**
     * 请求头
     */
    private HttpHeaders headers;

    public PortalHttpRequest setMediaType(MediaType mediaType){
        this.mediaType=mediaType;
        return this;
    }
    /**
     * 私有构造get
     * method+url
     * @param method
     * @param url
     */
    private  PortalHttpRequest(HttpMethod method,String url){
        this.method=method;
        this.url=url;
    }
    /**
     * 私有构造post
     * method+url+Map
     * @param method
     * @param url
     */
    private  PortalHttpRequest(HttpMethod method,String url,Map<String,Object> paramMap){
        this.method=method;
        this.url=url;
        this.paramMap=paramMap;
        this.postOptionType=PostOptionType.DEFAULT;
    }
    /**
     * 私有构造post
     * method+url+Object
     * @param method
     * @param url
     */
    private  PortalHttpRequest(HttpMethod method,String url,Object obj){
        this.method=method;
        this.url=url;
        this.obj=obj;
        this.postOptionType=PostOptionType.OBJECT_PARAM;
    }

    public  static PortalHttpRequest buildGet(String url){
        return  new PortalHttpRequest(HttpMethod.GET,url);
    }

    public  static PortalHttpRequest buildPost(String url,Map<String,Object> paramMap){
        return  new PortalHttpRequest(HttpMethod.POST,url,paramMap);
    }

    public  static PortalHttpRequest buildPost(String url,Object obj){
        return  new PortalHttpRequest(HttpMethod.POST,url,obj);
    }
}
