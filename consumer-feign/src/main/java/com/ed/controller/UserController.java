package com.ed.controller;

import com.ed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userservice;

    @RequestMapping("/")
    public String hello(){
        return userservice.hello();
    }

    @GetMapping("/toMain")
    public String toMain(){
        return "hello";
    }


}
