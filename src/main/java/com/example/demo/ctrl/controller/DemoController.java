package com.example.demo.ctrl.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.ctrl.config.annotation.HandlerComponent;
import com.example.demo.ctrl.config.bean.beanConfig.PropertiesConfig;
import com.example.demo.ctrl.config.handler.OrderedHandler;
import com.example.demo.ctrl.config.http.feign.UserService;
import com.example.demo.ctrl.config.http.restTemplate.HttpClient;
import com.example.demo.ctrl.config.http.restTemplate.PortalHttpRequest;
import com.example.demo.ctrl.config.redis.RedisService;
import com.example.demo.ctrl.config.threadTest.Thread.ReadThread;
import com.example.demo.ctrl.dto.Author;
import com.example.demo.ctrl.dto.ContextBean;
import com.example.demo.ctrl.dto.HandlerConstants;
import com.example.demo.ctrl.exception.BusinessException;
import com.example.demo.ctrl.exception.CommonResultCode;
import com.example.demo.ctrl.test.order.BeanInterface;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
    //fegin调接口测试
    @Autowired
    private UserService userService;
    //获取java配置项
    @Autowired
    private PropertiesConfig propertiesConfig;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisService redisService;
    @Autowired
    private List<BeanInterface> beanList;
    @Autowired
    private Map<String, BeanInterface> beanMap;

    @Autowired
    private  OrderedHandler<ContextBean> handlerChain;
    /**
     * bean容器
     */
    @Autowired
    private ApplicationContext applicationContext;
    //获取properties配置项http.config.icore.hostUrl，冒号后面指定默认值
    @Value("${http.config.icore.hostUrl:默认值} : 默认值" )
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
        //fegin调接口方式
        String result2=userService.getUserinfo("11223");
        log.info("fegin调接口测试出参："+result2);
        return result;
    }


    @RequestMapping(value = "/getName")
    public String getName(){

        log.info("获取properties配置项http.config.icore.hostUrl："+icoreUrl);
        String name=(String)(propertiesConfig.getUtil().getOrDefault("name","名字"));
        log.info("获取properties配置项nme:"+name);
        return "我的名字是小明！";
    }

    /**
     * 抛异常练习
     * @return
     */
    @RequestMapping(value = "/exception")
    public String exception(){
        throw new BusinessException(CommonResultCode.LOGIN_FAIL);
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
     * 测试redis
     * @return
     */

    @RequestMapping("/redisTest2")
    public String redisTest2() throws InterruptedException {
        String mobile="13314567896";
        Author a=new Author();
        a.setUid("123456789");
        a.setMobile(mobile);
        redisService.getAuthorInfo2(mobile,a);
        return  "Redis2调用成功";
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

    /**
     * 打日志练习
     * @return
     */
    @RequestMapping(value = "/logTest")
    public String logTest(){
        log.info("主方法");
        HashMap<Integer, Integer> hashMap = new HashMap();
        /** 多线程编辑100次*/
        for (int i = 0; i < 2; i++) {
            new Thread(new ReadThread(MDC.get("F"))).start();
        }


        return "打多线程日志练习";
    }

    /**
     * handler练习，策略模式应用
     * @return
     */
    @RequestMapping(value = "/handler")
    @HandlerComponent(threadHandler= HandlerConstants.handler) //策略模式应用,触发切面
    public String handler(){
        ContextBean t=ContextBean.getThreadContextBean();
        log.info("请求入参："+t);
        //开始执行handler链
        handlerChain.handle(t);
        return "handler练习";
    }
}
