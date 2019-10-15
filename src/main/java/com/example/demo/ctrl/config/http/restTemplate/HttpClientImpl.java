package com.example.demo.ctrl.config.http.restTemplate;

import com.example.demo.ctrl.exception.BusinessException;
import com.example.demo.ctrl.exception.CommonResultCode;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**测试http调接口，使用RestTemplate*/
@Data
@Slf4j
@Component
public class HttpClientImpl implements HttpClient{
    @Autowired
    protected RestTemplate restTemplate;

    //支持post/get调用
    public <T> T exec(@NonNull PortalHttpRequest request,Class<T> clazz){

        log.info("调接口输入URL："+request.getUrl());
        T result=null;
        long startTime=System.currentTimeMillis();
        //统一参数处理
        HttpEntity requestEntity=getRequestEntity(request);
        //循环调用多次，避免错误
        for (int i = 0; i <3 ; i++) {

            try {
                if(requestEntity!=null){
                    log.info("调用url:{} ,请求参数：{}",request.getUrl(),requestEntity.getBody());
                }
                //调用http接口
                ResponseEntity responseEntity=restTemplate.exchange(request.getUrl(),request.getMethod(),requestEntity,clazz);
                result=(T)responseEntity.getBody();
            }catch (Exception e){
                //错误时继续重试
                if(3>i){
                    log.warn("调用异常，requestURL：{} 再次调用 重试{}次 错误信息{}",request.getUrl(),i+1,e);
                    continue;
                }
                //多次重试失败，抛出异常
                log.error("调用异常，requestURL：{} 用时{}ms 错误信息{}",request.getUrl(),(System.currentTimeMillis()-startTime),e);
                throw e;
            }
            break;
        }

        if(result==null){
            log.error("调用异常，requestURL：{} 返回结果为空",request.getUrl());
            throw new BusinessException(CommonResultCode.HTTP_ERROR);
        }
        log.info("调用原始结果requestURL：{}，result:{}",request.getUrl(),result);
        log.info("调用结束，requestURL：{} 用时{}ms",request.getUrl(),(System.currentTimeMillis()-startTime));

        return  result;
    }

    //请求参数处理
    protected HttpEntity getRequestEntity(PortalHttpRequest request){
        HttpHeaders headers=new HttpHeaders();
        if(null!=request.getMediaType()){
            headers.setContentType(request.getMediaType());
        }
        if(null!=request.getHeaders()){
            headers.addAll(request.getHeaders());
        }
        //post请求参数特殊处理
        if(HttpMethod.POST.equals(request.getMethod())){
            switch (request.getPostOptionType()){
                case DEFAULT:
                        return new HttpEntity(request.getParamMap(),headers);
                case OBJECT_PARAM:
                        return new HttpEntity(request.getObj(),headers);
            }
        }

        //如果为get方法则返回空
        return null;
    }
}
