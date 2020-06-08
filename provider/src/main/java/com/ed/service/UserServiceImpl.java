package com.ed.service;

import com.ed.mapper.UserMapper;
import com.ed.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import com.ed.model.*;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.HashMap;

import java.util.Map;

@RestController
public class UserServiceImpl implements UserService {

    @Autowired

    @Resource
    private UserMapper userMapper;


    @Override
    public String hello() {
        return null;
    }

    @Override
    @RequestMapping("/success")
    @ResponseBody
    public UserModel Succ(@RequestParam("username") String username) {
        return userMapper.Succ(username);
    }

    @Override
    @RequestMapping("/reg")
    @ResponseBody
    public UserModel reg(@RequestParam("username") String username) {
        return userMapper.reg(username);
    }

    @RequestMapping("/reg1")
    @ResponseBody
    public void addUser(@RequestBody UserModel user) {
        userMapper.addUser(user);
    }

    @Override
    @RequestMapping("/phoneLogin")
    @ResponseBody
    public UserModel fingName(@RequestParam String phone) {
        return userMapper.fingName(phone);
    }

    @PostMapping("/selectCourse")
    @ResponseBody
    @Override
    public Map<String, Object> selectCourse(Integer page, Integer rows) {
        long coursetotal = userMapper.selectCourseCount();
        List<CourseEntity> courseList = userMapper.selectCourse((page - 1) * rows, rows);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("total", coursetotal);
        dataMap.put("rows", courseList);
        return dataMap;
    }

    @PostMapping("/selectCourseCourseid")
    @ResponseBody
    @Override
    public String selectCourseCourseid(Integer courseid, Integer userid) {
        try {
            CourseEntity courseEntities = userMapper.selectCourseCourseid(courseid).get(0);
            userMapper.addShopping(courseEntities, userid);
            userMapper.updateCourse(courseid);
            return "100";
        } catch (Exception e) {
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
    public Map<String, Object> selectShopping(Integer page, Integer rows,Integer userid) {
        long shoppingtotal = userMapper.selectShoppingCount();
        List<ShoppingEntity> shoppingList = userMapper.selectShopping((page-1)*rows,rows,userid);
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
    public void addOrder(String out_trade_no, Double total_amount, String subject,Integer userid) {
        userMapper.addOrder(out_trade_no,total_amount,subject,userid);
    }

    @PostMapping("/orderList")
    @ResponseBody
    @Override
    public List<Order> selectOrderList() {
        return userMapper.selectOrderList();
    }

    @PostMapping("/selectMianfCourse")
    @ResponseBody
    @Override
    public Map<String, Object> selectMianfCourse(Integer page, Integer rows) {
        long coursetotal = userMapper.selectMianfCourseCount();
        List<CourseEntity> courseList = userMapper.selectMianfCourse((page-1)*rows,rows);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("total", coursetotal);
        dataMap.put("rows", courseList);
        return dataMap;
    }

    @RequestMapping("/selectCourseType")
    @ResponseBody
    @Override
    public List<CourseEntity> selectCourseType(@RequestParam String name) {

        return userMapper.selectCourseType(name);
    }
    @RequestMapping("/selectSlideshow")
    @ResponseBody
    @Override
    public List<Slideshow> selectSlideshow() {
        return userMapper.selectSlideshow();
    }
    @RequestMapping("/userList")
    @ResponseBody
    @Override
    public UserEntity userList(String username) {
        return userMapper.userList(username);
    }

    @RequestMapping("/newteachwell")
    @ResponseBody
    @Override
    public Map<String, Object> newteachwell(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows) {
        long coursetotal = userMapper.newteachwellCount();
        List<CourseEntity> courseList = userMapper.newteachwell((page-1)*rows,rows);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("total", coursetotal);
        dataMap.put("rows", courseList);
        return dataMap;
    }

    @RequestMapping("/popularcourses")
    @ResponseBody
    @Override
    public Map<String, Object> popularcourses(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows) {
        List<CourseEntity> list = userMapper.popularcoursesCount();
        long coursetotal = list.size();
        List<CourseEntity> courseList = userMapper.popularcourses((page-1)*rows,rows);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("total", coursetotal);
        dataMap.put("rows", courseList);
        return dataMap;
    }

    @RequestMapping("/selectCourseList")
    @ResponseBody
    @Override
    public List<CourseEntity> selectCourseList(@RequestParam Integer courseid) {

        List<CourseEntity> list = userMapper.selectCourseList(courseid);

        return list;
    }

    @RequestMapping("/toXiangQing1")
    @ResponseBody
    @Override
    public CourseEntity queryCourseList(@RequestParam Integer courseid) {
        return userMapper.queryCourseList(courseid);
    }
}