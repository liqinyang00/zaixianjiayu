package com.ed.Service;

import com.ed.service.HelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloServiceImpl implements HelloService {

    @RequestMapping("/")
    @Override
    public String hello() {

        return "成功";
    }
}
