package com.example.demo.ctrl.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.ctrl.config.http.restTemplate.HttpClient;
import com.example.demo.ctrl.config.http.restTemplate.PortalHttpRequest;
import com.example.demo.ctrl.test.order.BeanInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 返回字符串
 */
@RestController
@Slf4j
//@ResponseBody //支持将返回值放在response体内，而不是返回一个页面
public class DemoController {
    @Autowired
    private HttpClient httpClientImpl;
    //引入java方式配置的bean,默认为Bean方法名
    @Autowired
    private HttpClient icoreClient;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private List<BeanInterface> beanList;
    @Autowired
    private Map<String, BeanInterface> beanMap;
    /**
     * bean容器
     */
    @Autowired
    private ApplicationContext applicationContext;
    //获取properties配置项http.config.icore.hostUrl，冒号后面指定默认值
    @Value("${http.config.icore.hostUrl:默认值}")
    private String icoreUrl;


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
        String result=httpClientImpl.exec(portalHttpRequest,String.class);
        log.info("http调接口测试出参："+result);
        return result;
    }


    @RequestMapping(value = "/getName")
    public String getName(){

        log.info("获取properties配置项http.config.icore.hostUrl："+icoreUrl);
        return "我的名字是小明！";
    }

    /**
     * 测试redis
     * @return
     */

    @RequestMapping("/redisTest")
    public String redisTest() throws InterruptedException {
        ValueOperations<String,String> redis=redisTemplate.opsForValue();
        redis.set("name","刘晓晓");
        redis.set("age","22");
        //设置失效时间
        redis.set("aaaa","我的名字是小米",1, TimeUnit.SECONDS);
        Thread.sleep(60000);
        String re=(String)redis.get("name");
        String aaaa=(String)redis.get("aaaa");
        log.info("redis取出结果："+re+"失效值："+aaaa);
        return  "Redis调用成功"+re;
    }


    /**
     * 排序练习，@Order,一般用@Order对list排序
     * 可以看到list的数据反过来了，但是map的数据却没有变。
     * 因为@Order针对数组的。
     * 注解@Order或者接口Ordered的作用是定义Spring IOC容器中Bean的执行顺序的优先级，而不是定义Bean的加载顺序，Bean的加载顺序不受@Order或Ordered接口的影响
     */
    @RequestMapping("/orderTest")
    public String orderTest(){
        if(!CollectionUtils.isEmpty(beanList)) {
            System.out.println("beanList为 : "+beanList);
            for (BeanInterface bean : beanList) {
                System.out.println(bean.getClass().getName());
            }
        }

        if(!CollectionUtils.isEmpty(beanMap)) {
            System.out.println("beanMap为 : "+beanMap);
            for (Map.Entry<String, BeanInterface> entry : beanMap.entrySet()) {
                System.out.println(entry.getKey() + "  " + entry.getValue().getClass().getName());
            }
        }
        return  "排序练习成功";
    }


    /**
     * 容器练习
     * @return
     */
    @RequestMapping(value = "/getContainer")
    public String getContainer(){
        log.info("bean容器练习");
        log.info(applicationContext.getApplicationName());
        log.info("获取"+applicationContext.getBean("icoreClient"));
        log.info("包含"+applicationContext.containsBean("icoreClient"));
        //获取所有bean
        String[] beanNames=applicationContext.getBeanDefinitionNames();
        for (int i = 0; i <beanNames.length ; i++) {
            log.info("所有beanNames:"+beanNames[i]);
        }
        log.info("bean容器练习");

        return "bean容器练习！";
    }

    }
