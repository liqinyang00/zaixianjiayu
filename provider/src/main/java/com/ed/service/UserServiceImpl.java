package com.ed.service;

import com.ed.mapper.UserMapper;
import com.ed.model.Slideshow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public String hello() {
        return "成功！！！";
    }



}
