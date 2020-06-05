package com.ed.service;

import com.ed.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient("provider")
public interface UserService {

    @GetMapping("/")
    @ResponseBody
    List<User> text();

}
