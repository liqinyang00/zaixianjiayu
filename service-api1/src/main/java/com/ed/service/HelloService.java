package com.ed.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("provider")
public interface HelloService {

    @RequestMapping("/")
    String hello();
}
