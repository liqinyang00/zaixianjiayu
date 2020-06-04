package com.ed.controller;

import com.alibaba.fastjson.JSONObject;
import com.ed.common.CommonConf;
import com.ed.model.UserModel;
import com.ed.service.UserService;
import com.ed.util.CheckImgUtil;
import com.ed.util.CheckSumBuilder;
import com.ed.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class UserController {

    @Autowired
    private UserService userservice;
    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("/")
    public String hello() {
        return userservice.hello();
    }

    @GetMapping("/toMain")
    public String toMain(HttpServletRequest request, HttpServletResponse response) {
        String username = (String) request.getSession().getAttribute("username");
        request.getSession().setAttribute("value", username);
        return "hello";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @GetMapping("/toRegister")
    public String toRegister() {

        return "register";
    }

    @RequestMapping("getCode")
//验证码
    public void getCode(HttpServletRequest request, HttpServletResponse response) {

        CheckImgUtil.buildCheckImg(request, response);
    }

   /* @GetMapping("/session")
    public String session(HttpServletRequest request,HttpServletResponse response){
      *//*  String username = (String) request.getSession().getAttribute("username");
        request.getSession().setAttribute("value", username);*//*
        return "hello";
    }*/


    //登录
    @RequestMapping("/success")
    @ResponseBody
    public HashMap successful(String username, String userpassword, String code, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String realCode = request.getSession().getAttribute("checkcode").toString().toLowerCase();

        HashMap<String, Object> mape = new HashMap<>();

        UserModel loginName = userservice.Succ(username);
        if (realCode.equals(code.toLowerCase())) {

            if (loginName != null) {
                if (loginName.getUserpassword().equals(userpassword)) {

                    mape.put("cd", 1);
                    mape.put("msg", "登录成功");
                    request.getSession().setAttribute("username", loginName.getUsername());
                } else {
                    mape.put("cd", 2);
                    mape.put("msg", "密码错误");


                }
            } else {
                mape.put("cd", 3);
                mape.put("msg", "用户名错误");


            }

        } else {
            mape.put("cd", 4);
            mape.put("msg", "验证码错误");


        }


        return mape;


    }

    //注册
    @RequestMapping(value = {"/reg", "/reg1"})
    @ResponseBody
    public HashMap reg(UserModel user) {
        UserModel reuser = userservice.reg(user.getUsername());
        HashMap<String, Object> msg = new HashMap<>();
        if (reuser != null) {
            msg.put("cod", 5);
            msg.put("msg", "用户已存在");
        } else {

            userservice.addUser(user);
        }
        return msg;
    }

    @RequestMapping("/shoujidenglu")
    public String shoujidenglu() {
        return "shoujidenglu";
    }

    @RequestMapping("/getphoneCode")
    @ResponseBody
    public Map getphoneCode(String phone) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            if (redisTemplate.hasKey(CommonConf.SMS_LOCK + phone)) {
                result.put("code", 2);
                result.put("msg", "1分钟内不能重复获取");
                return result;
            }
            String nonceNum = UUID.randomUUID().toString().replace("-", "");
            String timeMil = String.valueOf((new Date()).getTime() / 1000L);
            int authCode = (int)((Math.random()*9+1)*100000);

            HashMap<String, Object> params = new HashMap<>();
            params.put("mobile", phone);
            params.put("templateid", CommonConf.TEMPLATEID);
            params.put("authCode", authCode);

            HashMap<String, Object> headerParam = new HashMap<>();
            headerParam.put("Content-Type", CommonConf.CONTENT_TYPE);
            headerParam.put("AppKey", CommonConf.APP_KEY);
            headerParam.put("Nonce", nonceNum);
            headerParam.put("CurTime", timeMil);
            headerParam.put("CheckSum", CheckSumBuilder.getCheckSum(CommonConf.APP_SECRET, nonceNum, timeMil));
            String resultJson = HttpClientUtil.post3(CommonConf.SERVER_URL, params, headerParam);
            JSONObject parseObject = JSONObject.parseObject(resultJson);
            int code = parseObject.getIntValue("code");

            if (code == 200) {
                redisTemplate.opsForValue().set(CommonConf.SMS_CODE + phone, authCode, 5, TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(CommonConf.SMS_LOCK + phone, "LOCK", 1, TimeUnit.MINUTES);
                result.put("code", 0);
                result.put("msg", "发送成功");
                return result;
            } else {
                result.put("code", 1);
                result.put("msg", "发送失败");
                return result;
            }

        } catch (Exception e) {
            result.put("code", 1);
            result.put("msg", "发送失败");
            return result;
        }

    }

    @RequestMapping("/phoneLogin")
    @ResponseBody
    public HashMap<String, Object> phoneLogin(String phone, String phoneCode, HttpServletRequest request) {

        HashMap<String, Object> result = new HashMap<>();
        //判断缓存中是否有该账号的验证码
        if (!redisTemplate.hasKey(CommonConf.SMS_CODE + phone)) {
            result.put("code", 1);
            result.put("msg", "验证码已过期，请重新获取");
            return result;
        }
        String cachecode = redisTemplate.opsForValue().get(CommonConf.SMS_CODE + phone).toString();
        if (!cachecode.equals(phoneCode)) {
            result.put("code", 2);
            result.put("msg", "验证码错误，请重新输入");
            return result;
        }
        UserModel username = userservice.fingName(phone);
        if (username == null) {
            result.put("code", 3);
            result.put("msg", "用户不存在");
            return result;
        }
        request.getSession().setAttribute("username", username.getUsername());
        result.put("code", 0);
        result.put("msg", "登录成功");
        return result;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String username = (String) request.getSession().getAttribute("username");

        if (username != null) {
            request.getSession().removeAttribute("username");
        }
        return "login";
    }
}