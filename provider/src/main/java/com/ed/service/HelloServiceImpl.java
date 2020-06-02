package com.ed.service;


import com.ed.mapper.HelloMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloServiceImpl implements Service {

    @Autowired
    private HelloMapper helloMapper;

}
