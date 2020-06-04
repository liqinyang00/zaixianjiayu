package com.ed.service;

import com.ed.model.CourseEntity;
import com.ed.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("provider")
public interface UserService {

    @PostMapping("/selectCourse")
    @ResponseBody
    Map<String, Object> selectCourse(@RequestParam Integer page,@RequestParam Integer rows);

    @PostMapping("/selectCourseCourseid")
    @ResponseBody
    String selectCourseCourseid(@RequestParam Integer courseid);

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
    List<CourseEntity> searchCourse(CourseEntity course);

    @RequestMapping("/zhiFu2")
    @ResponseBody
    void addOrder(@RequestParam String out_trade_no,@RequestParam Double total_amount,@RequestParam String subject);

    @PostMapping("/orderList")
    @ResponseBody
    List<Order> selectOrderList();

}
