package com.ed.service;

import org.springframework.stereotype.Controller;

@Controller
public class UserServiceImpl implements UserService {

    @Override
    public String hello() {
        return "成功！！！";
    }

}
