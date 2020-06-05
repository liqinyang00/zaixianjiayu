package com.ed.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.ed.common.CommonConf;
import com.ed.model.*;
import com.ed.util.CheckImgUtil;
import com.ed.service.*;
import com.ed.util.CheckSumBuilder;
import com.ed.util.HttpClientUtil;
import com.ed.utils.AlipayConfig;
import com.ed.utils.RedisConstant;
import com.ed.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import java.util.concurrent.TimeUnit;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/toMain")
    public String toMain(HttpServletRequest request, HttpServletResponse response) {
        String username = (String) request.getSession().getAttribute("username");
        request.getSession().setAttribute("value", username);
        return "hello";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @GetMapping("/toRegister")
    public String toRegister() {

        return "register";
    }

    @RequestMapping("getCode")
    //验证码
    public void getCode(HttpServletRequest request, HttpServletResponse response) {

        CheckImgUtil.buildCheckImg(request, response);
    }

   /* @GetMapping("/session")
    public String session(HttpServletRequest request,HttpServletResponse response){
      *//*  String username = (String) request.getSession().getAttribute("username");
        request.getSession().setAttribute("value", username);*//*
        return "hello";
    }*/

    //登录
    @RequestMapping("/success")
    @ResponseBody
    public HashMap successful(String username, String userpassword, String code, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String realCode = request.getSession().getAttribute("checkcode").toString().toLowerCase();

        HashMap<String, Object> mape = new HashMap<>();

        UserModel loginName = userService.Succ(username);
        if (realCode.equals(code.toLowerCase())) {
            if (loginName != null) {
                if (loginName.getUserpassword().equals(userpassword)) {
                    mape.put("cd", 1);
                    mape.put("msg", "登录成功");
                    request.getSession().setAttribute("username", loginName.getUsername());
                } else {
                    mape.put("cd", 2);
                    mape.put("msg", "密码错误");
                }
            } else {
                mape.put("cd", 3);
                mape.put("msg", "用户名错误");
            }
        } else {
            mape.put("cd", 4);
            mape.put("msg", "验证码错误");
        }

        return mape;
    }

    //注册
    @RequestMapping(value = {"/reg", "/reg1"})
    @ResponseBody
    public HashMap reg(UserModel user) {
        UserModel reuser = userService.reg(user.getUsername);
        HashMap<String, Object> msg = new HashMap<>();
        if (reuser != null) {
            msg.put("cod", 5);
            msg.put("msg", "用户已存在");
        } else {

            userService.addUser(user);
        }
        return msg;
    }

    @RequestMapping("/shoujidenglu")
    public String shoujidenglu() {
        return "shoujidenglu";
    }

    @RequestMapping("/getphoneCode")
    @ResponseBody
    public Map getphoneCode(String phone) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            if (redisTemplate.hasKey(CommonConf.SMS_LOCK + phone)) {
                result.put("code", 2);
                result.put("msg", "1分钟内不能重复获取");
                return result;
            }
            String nonceNum = UUID.randomUUID().toString().replace("-", "");
            String timeMil = String.valueOf((new Date()).getTime() / 1000L);
            int authCode = (int)((Math.random()*9+1)*100000);

            HashMap<String, Object> params = new HashMap<>();
            params.put("mobile", phone);
            params.put("authCode", authCode);

            HashMap<String, Object> headerParam = new HashMap<>();
            headerParam.put("Content-Type", CommonConf.CONTENT_TYPE);
            headerParam.put("AppKey", CommonConf.APP_KEY);
            headerParam.put("Nonce", nonceNum);
            headerParam.put("CurTime", timeMil);
            headerParam.put("CheckSum", CheckSumBuilder.getCheckSum(CommonConf.APP_SECRET, nonceNum, timeMil));
            String resultJson = HttpClientUtil.post3(CommonConf.SERVER_URL, params, headerParam);
            JSONObject parseObject = JSONObject.parseObject(resultJson);
            int code = parseObject.getIntValue("code");

            if (code == 200) {
                redisTemplate.opsForValue().set(CommonConf.SMS_CODE + phone, String.valueOf(authCode), 5, TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(CommonConf.SMS_LOCK + phone, "LOCK", 1, TimeUnit.MINUTES);
                result.put("code", 0);
                result.put("msg", "发送成功");
                return result;
            } else {
                result.put("code", 1);
                result.put("msg", "发送失败");
                return result;
            }

        } catch (Exception e) {
            result.put("code", 1);
            result.put("msg", "发送失败");
            return result;
        }

    }

    @RequestMapping("/phoneLogin")
    @ResponseBody
    public HashMap<String, Object> phoneLogin(String phone, String phoneCode, HttpServletRequest request) {

        HashMap<String, Object> result = new HashMap<>();
        //判断缓存中是否有该账号的验证码
        if (!redisTemplate.hasKey(CommonConf.SMS_CODE + phone)) {
            result.put("code", 1);
            result.put("msg", "验证码已过期，请重新获取");
            return result;
        }
        String cachecode = redisTemplate.opsForValue().get(CommonConf.SMS_CODE + phone);
        if (!cachecode.equals(phoneCode)) {
            result.put("code", 2);
            result.put("msg", "验证码错误，请重新输入");
            return result;
        }
        UserModel username = userService.fingName(phone);
        if (username == null) {
            result.put("code", 3);
            result.put("msg", "用户不存在");
            return result;
        }
        request.getSession().setAttribute("username", username.getUsername());
        result.put("code", 0);
        result.put("msg", "登录成功");
        return result;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String username = (String) request.getSession().getAttribute("username");

        if (username != null) {
            request.getSession().removeAttribute("username");
        }
        return "redirect:toMain";
    }

    @GetMapping("/toShoppingCart")
    public String toShoppingCart(){
        return "shoppingCart";
    }

    @GetMapping("/toShiZhanKeCheng")
    public String toShiZhanKeCheng(){

        /*ModelAndView mav = new ModelAndView();
        List<TypeEntity> typeList = userService.selectCourseType();
        mav.addObject("typeList", typeList);
        mav.setViewName("shiZhanKeCheng");*/
        return "shiZhanKeCheng";
    }

    @GetMapping("/selectCourseType")
    public ModelAndView selectCourseType(String name){

        ModelAndView mav = new ModelAndView();
        mav.addObject("name",name);//
        // 传值
        mav.setViewName("Search for courses2");//返回页面searchCourse2

        return mav;
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

    @PostMapping("/selectCourse")
    @ResponseBody
    public Map<String, Object> selectCourse(@RequestParam Integer page,@RequestParam Integer rows){
        Map<String, Object> resultMap = userService.selectCourse(page, rows);
        return resultMap;
    }

    @PostMapping("/selectMianfCourse")
    @ResponseBody
    public Map<String, Object> selectMianfCourse(@RequestParam Integer page,@RequestParam Integer rows){
        Map<String, Object> resultMap = userService.selectMianfCourse(page, rows);
        return resultMap;
    }

    @PostMapping("/selectCourseCourseid")
    @ResponseBody
    public String selectCourseCourseid(@RequestParam Integer courseid,HttpServletRequest request){
        String username = (String )request.getSession().getAttribute("username");
        UserEntity user = userService.userList(username);
        Integer userid = user.getUserid();
        return userService.selectCourseCourseid(courseid,userid);
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

    @PostMapping("/searchCourse")
    @ResponseBody
    public List<CourseEntity> searchCourse(CourseEntity course){
        if(!StringUtils.isEmpty(course.getCoursetitle())){
            List<CourseEntity> courseEntities = userService.searchCourse(course);
            return courseEntities;
        }else {
            return null;
        }
    }

    @PostMapping("/searchCourse2")
    @ResponseBody
    public List<CourseEntity> searchCourse2(CourseEntity course){
        if(!StringUtils.isEmpty(course.getName())){

            List<CourseEntity> courseEntities = userService.selectCourseType(course.getName());

           /* List<CourseEntity> courseEntities = userService.searchCourse(course);*/
            return courseEntities;
        }else {
            return null;
        }
    }

    @RequestMapping(value = {"/zhiFu","/zhifu2","/userList"})
    @ResponseBody
    public String zhiFu(@RequestParam String courseid,HttpServletRequest request) throws Exception{
        CourseEntity course = userService.getOrderById(courseid);

        String username = (String )request.getSession().getAttribute("username");
        UserEntity user = userService.userList(username);
        Integer userid = user.getUserid();

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
        userService.addOrder(out_trade_no,total_amount,subject,userid);


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

    //新上课程
    @RequestMapping("/newteachwell")
    @ResponseBody
    public Map<String, Object> newteachwell(Integer page, Integer rows){

        Map<String, Object> map = userService.newteachwell(page, rows);

        return map;
    }

    //热销课程
    @RequestMapping("/popularcourses")
    @ResponseBody
    public Map<String, Object> popularcourses(Integer page, Integer rows){

        Map<String, Object> map = userService.popularcourses(page, rows);

        return map;
    }

    @RequestMapping("/selectSlideshow")
    @ResponseBody
    public List<Slideshow> selectSlideshow(){
       return userService.selectSlideshow();
    }

}

