package com.ed.service;

import com.ed.mapper.UserMapper;
import com.ed.model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public String hello() {
        return "成功！！！";
    }

    @Override
    @RequestMapping("/success")
    @ResponseBody
    public UserModel Succ( @RequestParam("username")  String username) {
        return userMapper.Succ(username);
    }

    @Override
    @RequestMapping("/reg")
    @ResponseBody
    public UserModel reg( @RequestParam("username") String username) {
        return userMapper.reg(username);
    }
    @RequestMapping("/reg1")
    @ResponseBody
    public void addUser  (@RequestBody UserModel user) {
        userMapper.addUser(user);
    }

    @Override
    @RequestMapping("/phoneLogin")
    @ResponseBody
    public UserModel fingName(@RequestParam String phone) {
        return userMapper.fingName(phone);
    }

}
