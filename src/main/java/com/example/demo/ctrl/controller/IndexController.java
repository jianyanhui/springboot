package com.example.demo.ctrl.controller;

import com.example.demo.ctrl.config.ConfigToDto;
import com.example.demo.ctrl.dto.User;
import com.example.demo.ctrl.util.LogToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
//@Slf4j
public class IndexController {
    @Autowired
    private Environment env;
    @Value("${spring.thymeleaf.prefix}")
    private String  ss;
    @Autowired
    private User user;//读取配置文件测试

    @Autowired
    private ConfigToDto configToDto;//读取配置文件转dto测试

    @RequestMapping("/index")
    public String index(){
        String s=env.getProperty("spring.thymeleaf.prefix","");
        System.out.println("s结果为："+s);
        System.out.println("ss结果为："+ss);
        return "test";//返回html文件名
    }

    @RequestMapping("/vueTest")
    public String test(){
//        log.debug("进入test方法");
        return "vue";//返回html文件名
    }

    @RequestMapping("/vueTest2")
    public String testConfig(){
        String a="123";
        String name=user.getUsername();
        System.out.println("获取的配置项为："+ LogToString.objectToString(user));
        System.out.println("获取的配置项转dto为："+ LogToString.objectToString(configToDto));
        return "vue";//返回html文件名
    }

    /**
     * thymeleaf返回视图
     * @param model
     * @return
     */
    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        User u=new User();
        u.setUsername("马先生");
        u.setSex("男");
        model.addAttribute("uuuser",u);

        List<User> listUser=new ArrayList<User>();
        for (int i = 0; i <3 ; i++) {
            User u1=new User();
            u1.setUsername("马先生"+i);
            u1.setSex("男"+i);
            listUser.add(u1);
        }
        model.addAttribute("listUser",listUser);

        return "thymeleaf";//返回html文件名
    }

}
