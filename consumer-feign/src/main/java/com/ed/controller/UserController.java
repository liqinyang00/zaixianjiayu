package com.ed.controller;

import com.ed.common.CommonConf;
import com.ed.model.User;
import com.ed.service.UserService;
import com.ed.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class UserController {

    @Autowired
    private UserService userservice;

    @Autowired
    private RedisUtil redisUtil;


    @GetMapping("/")
    @ResponseBody
    public List<User> text(){
        List<User>  list = (List<User>) redisUtil.get(CommonConf.TEXT);

        if (list == null){

            list = userservice.text();
            redisUtil.set(CommonConf.TEXT,list);
           //⏳设置 过期时间
            redisUtil.expire(CommonConf.TEXT,15,TimeUnit.SECONDS);

        }
       long expire = redisUtil.getExpire(CommonConf.TEXT,TimeUnit.SECONDS);
        System.out.println("倒计时!"+expire);
        return list;
    }

    @GetMapping("/toMain")
    public String toMain(){
        return "hello";
    }


}
