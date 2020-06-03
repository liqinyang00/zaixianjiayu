package com.ed.service;

import com.ed.mapper.UserMapper;

import com.ed.model.CourseEntity;
import com.ed.model.Order;
import com.ed.model.ShoppingEntity;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserServiceImpl implements UserService {


    @Resource
    private UserMapper userMapper;

    @PostMapping("/selectCourse")
    @ResponseBody
    @Override
    public Map<String, Object> selectCourse(Integer page, Integer rows) {
        long coursetotal = userMapper.selectCourseCount();
        List<CourseEntity> courseList = userMapper.selectCourse((page-1)*rows,rows);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("total", coursetotal);
        dataMap.put("rows", courseList);
        return dataMap;
    }

    @PostMapping("/selectCourseCourseid")
    @ResponseBody
    @Override
    public String selectCourseCourseid(Integer courseid) {
        try{
            CourseEntity courseEntities =  userMapper.selectCourseCourseid(courseid).get(0);
            userMapper.addShopping(courseEntities);
            return  "100";
        }catch (Exception e){
            e.printStackTrace();
            return "500";
        }

    }

    @PostMapping("/delectShopping")
    @ResponseBody
    @Override
    public void delectShopping(Integer shoppingid) {
        userMapper.delectShopping(shoppingid);
    }

    @PostMapping("/selectShopping")
    @ResponseBody
    @Override
    public Map<String, Object> selectShopping(Integer page, Integer rows) {
        long shoppingtotal = userMapper.selectShoppingCount();
        List<ShoppingEntity> shoppingList = userMapper.selectShopping((page-1)*rows,rows);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("total", shoppingtotal);
        dataMap.put("rows", shoppingList);
        return dataMap;
    }

    @RequestMapping("/zhiFu")
    @ResponseBody
    @Override
    public CourseEntity getOrderById(String courseid) {
        return userMapper.getOrderById(courseid);
    }

    @PostMapping("/searchCourse")
    @ResponseBody
    @Override
    public List<CourseEntity> searchCourse(CourseEntity course) {
        return userMapper.searchCourse(course);
    }

    @RequestMapping("/zhiFu2")
    @ResponseBody
    @Override
    public void addOrder(String out_trade_no, Double total_amount, String subject) {
        userMapper.addOrder(out_trade_no,total_amount,subject);
    }

    @PostMapping("/orderList")
    @ResponseBody
    @Override
    public List<Order> selectOrderList() {
        return userMapper.selectOrderList();
    }



}
