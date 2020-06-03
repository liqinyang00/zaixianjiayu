package com.ed.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.ed.model.CourseEntity;
import com.ed.model.Order;
import com.ed.service.UserService;
import com.ed.utils.AlipayConfig;
import com.ed.utils.RedisConstant;
import com.ed.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/toMain")
    public String toMain(){
        return "hello";
    }

    @GetMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    @GetMapping("/toShoppingCart")
    public String toShoppingCart(){
        return "shoppingCart";
    }

    @GetMapping("/toShiZhanKeCheng")
    public String toShiZhanKeCheng(){
        return "shiZhanKeCheng";
    }

    @GetMapping("/toMianFeiKeCheng")
    public String toMianFeiKeCheng(){
        return "mianFeiKeCheng";
    }

    @GetMapping("/toXiangQing1")
    public String toXiangQing1(){
        return "xiangQing1";
    }

    @GetMapping("/toXiangQing2")
    public String toXiangQing2(){
        return "xiangQing2";
    }

    @GetMapping("/toXiangQing3")
    public String toXiangQing3(){
        return "xiangQing3";
    }

    @GetMapping("/toKingZhiWei")
    public String toKingZhiWei(){
        return "kingZhiWei";
    }

    @GetMapping("/toWIKI")
    public String toWIKI(){
        return "WIKI";
    }

    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @PostMapping("/selectCourse")
    @ResponseBody
    public Map<String, Object> selectCourse(@RequestParam Integer page,@RequestParam Integer rows){
        Map<String, Object> resultMap = userService.selectCourse(page, rows);
        return resultMap;
    }

    @PostMapping("/selectCourseCourseid")
    @ResponseBody
    public String  selectCourseCourseid(@RequestParam Integer courseid){
        return userService.selectCourseCourseid(courseid);
    }

    @PostMapping("/selectShopping")
    @ResponseBody
    public  Map<String, Object> selectShopping(@RequestParam Integer page,@RequestParam Integer rows){
        Map<String, Object> resultMap = userService.selectShopping(page, rows);
        return resultMap;
    }

    @PostMapping("/delectShopping")
    @ResponseBody
    public String  delectShopping(@RequestParam Integer shoppingid){
        userService.delectShopping(shoppingid);
        return "0";
    }

    //全局搜索
    @GetMapping("/toSearchforCourses")
    public ModelAndView toSearchforCourses(String keyword){
        ModelAndView mav = new ModelAndView();
        mav.addObject("keyword",keyword);//
        // 传值
        mav.setViewName("Search for courses");//返回页面
        return mav;
    }

    @RequestMapping(value = {"/zhiFu","/zhifu2"})
    @ResponseBody
    public String zhiFu(@RequestParam String courseid) throws Exception{
        CourseEntity course = userService.getOrderById(courseid);


        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        //Integer out_trade_no = course.getCourseid();
        //付款金额，必填
        Double total_amount = course.getCourseprice();
        //订单名称，必填
        String subject = course.getCoursetitle();

        redisUtil.del(RedisConstant.ORDER_LIST);
        userService.addOrder(out_trade_no,total_amount,subject);


       /* //商品描述，可空
        String body = "用户订购商品个数：" + order.getBuyCount();*/
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1c";
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                /*  + "\"body\":\""+ body +"\","*/
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return result;
    }

    /**
     * 同步通知的页面的Controller
     * 我这边就简单的返回了一个页面
     *
     * @throws InterruptedException
     */
    @RequestMapping("return_url")
    public String Return_url() throws InterruptedException {
        return "chenggong";
    }

    @GetMapping("/toOrder")
    public String toOrder(){
        return "dingdan";
    }

    @PostMapping("/orderList")
    @ResponseBody
    public List<Order> selectOrderList(){
        List<Order> orderList= (List<Order>) redisUtil.get(RedisConstant.ORDER_LIST);
        if (orderList == null){
            orderList = userService.selectOrderList();
            redisUtil.set(RedisConstant.ORDER_LIST,orderList);
        }
        return orderList;

    }

    @PostMapping("/searchCourse")
    @ResponseBody
    public List<CourseEntity>  searchCourse(CourseEntity course){
        if(!StringUtils.isEmpty(course.getCoursetitle())){
            List<CourseEntity> courseEntities = userService.searchCourse(course);
            return courseEntities;
        }else {
            return null;
        }

    }

}
