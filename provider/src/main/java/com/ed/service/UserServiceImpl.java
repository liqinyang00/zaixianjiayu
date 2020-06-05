package com.ed.service;

import com.ed.mapper.UserMapper;
import com.ed.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    @ResponseBody
    @Override
    public List<User> text() {

        return userMapper.text();
    }

}
