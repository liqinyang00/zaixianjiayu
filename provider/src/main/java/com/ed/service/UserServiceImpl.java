package com.ed.service;

import com.ed.mapper.UserMapper;
import com.ed.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /*@GetMapping("/")
    @ResponseBody
    @Override
    public List<User> text() {

        return userMapper.text();
    }*/


    @RequestMapping("/success")
    @ResponseBody
    @Override
    public UserModel Succ(@RequestParam("username")  String username) {
        return userMapper.Succ(username);
    }


    @RequestMapping("/reg")
    @ResponseBody
    @Override
    public UserModel reg( @RequestParam("username") String username) {
        return userMapper.reg(username);
    }

    @RequestMapping("/reg1")
    @ResponseBody
    public void addUser  (@RequestBody UserModel user) {
        userMapper.addUser(user);
    }


    @RequestMapping("/phoneLogin")
    @ResponseBody
    @Override
    public UserModel fingName(@RequestParam String phone) {
        return userMapper.fingName(phone);
    }

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

   /* @RequestMapping("/toShiZhanKeCheng")
    @Override
    public List<TypeEntity> selectCourseType() {

        return userMapper.selectCourseType();
    }*/
}
