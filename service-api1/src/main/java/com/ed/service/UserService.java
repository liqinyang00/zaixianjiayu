package com.ed.service;


import com.ed.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ed.model.CourseEntity;
import com.ed.model.Order;
import com.ed.model.Slideshow;
import com.ed.model.UserModel;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

import com.ed.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("provider")
public interface UserService {

   /* @GetMapping("/")
    @ResponseBody
    List<User> text();*/

   /* @RequestMapping("/")
    String hello();
    */

    @RequestMapping("/success")
    @ResponseBody
    UserModel Succ( @RequestParam("username") String username);

    @RequestMapping("/reg")
    @ResponseBody
    UserModel reg(@RequestParam("username") String username);
    @RequestMapping("/reg1")
    @ResponseBody
    void addUser(UserModel user);

    @RequestMapping("/phoneLogin")
    @ResponseBody
    UserModel fingName(@RequestParam String phone);

    @PostMapping("/selectCourse")
    @ResponseBody
    Map<String, Object> selectCourse(@RequestParam Integer page, @RequestParam Integer rows);

    @PostMapping("/selectCourseCourseid")
    @ResponseBody
    String selectCourseCourseid(@RequestParam Integer courseid,@RequestParam Integer userid);

    @PostMapping("/delectShopping")
    @ResponseBody
    void delectShopping(@RequestParam Integer shoppingid);

    @PostMapping("/selectShopping")
    @ResponseBody
    Map<String, Object> selectShopping(@RequestParam Integer page,@RequestParam Integer rows);

    @RequestMapping("/zhiFu")
    @ResponseBody
    CourseEntity getOrderById(@RequestParam String courseid);

    @PostMapping("/searchCourse")
    @ResponseBody
    List<CourseEntity> searchCourse(@RequestBody CourseEntity course);

    @RequestMapping("/zhiFu2")
    @ResponseBody
    void addOrder(@RequestParam String out_trade_no,@RequestParam Double total_amount,@RequestParam String subject,@RequestParam Integer userid);

    @PostMapping("/orderList")
    @ResponseBody
    List<Order> selectOrderList();

    @PostMapping("/selectMianfCourse")
    @ResponseBody
    Map<String, Object> selectMianfCourse(@RequestParam Integer page, @RequestParam Integer rows);

    @RequestMapping("/selectCourseType")
    @ResponseBody
    List<CourseEntity> selectCourseType(@RequestParam String name);

    @RequestMapping("/newteachwell")
    @ResponseBody
    Map<String, Object> newteachwell(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows);

    @RequestMapping("/popularcourses")
    @ResponseBody
    Map<String, Object> popularcourses(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows);

    @RequestMapping("/selectSlideshow")
    @ResponseBody
    List<Slideshow> selectSlideshow();
    @RequestMapping("/userList")
    @ResponseBody
    UserEntity userList(@RequestParam String username);

    @RequestMapping("/selectCourseList")
    @ResponseBody
    CourseEntity selectCourseList(@RequestParam Integer courseid);

    /*@RequestMapping("/toShiZhanKeCheng")
    List<TypeEntity> selectCourseType();*/
}
