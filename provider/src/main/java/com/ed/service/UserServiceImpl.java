package com.ed.service;

import com.ed.mapper.UserMapper;

import com.ed.repository.UserRepository;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import com.ed.model.*;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;

@RestController
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;


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
        /*for (CourseEntity cour : courseList) {
            CourseEntity course = new CourseEntity();
            course.setCourseid(cour.getCourseid());
            course.setCoursetitle(cour.getCoursetitle());
            course.setAutherimg(cour.getAutherimg());
            course.setAuthername(cour.getAuthername());
            course.setCoursecontext(cour.getCoursecontext());
            course.setCoursenumber(cour.getCoursenumber());
            course.setCourseprice(cour.getCourseprice());
            course.setCourseimg(cour.getCourseimg());
            course.setCoursetype(cour.getCoursetype());
            course.setName(cour.getName());
            userRepository.save(course);

        }*/
        Map<String, Object> dataMap = new HashMap<String, Object>();
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
    @RequestMapping("/souSuo")
    @ResponseBody
    @Override
    public List<CourseEntity> souSuo(@RequestParam String titleName) {
        Client client = elasticsearchTemplate.getClient();
        // 定义查询对象，指定索引名称和类型名称，也可以定义query查询
        SearchRequestBuilder searchRequestBuilder = client
                .prepareSearch("1908_user_index")
                .setTypes("1908_user_type")
                .setQuery(QueryBuilders.matchQuery("coursetitle", titleName));

        // 获取高亮对象
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("coursetitle");
        searchRequestBuilder.highlighter(highlightBuilder);

        // 获取查询返回的对象
        SearchResponse searchResponse = searchRequestBuilder.get();
        // 通过SearchResponse对象获取命中的结果集
        SearchHits hits = searchResponse.getHits();
        // 命中的结果集获取iterator迭代器
        Iterator<SearchHit> iterator = hits.iterator();
        List<CourseEntity> courseList = new ArrayList<>();
        while (iterator.hasNext()) {
            SearchHit hit = iterator.next();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            CourseEntity course = new CourseEntity();
            course.setCourseid(Integer.valueOf(sourceAsMap.get("courseid").toString()));
            Map<String, HighlightField> fields = hit.getHighlightFields();
            HighlightField highlightField = fields.get("coursetitle");
            course.setCoursetitle(highlightField.getFragments()[0].toString());
            course.setCourseimg(sourceAsMap.get("courseimg").toString());
            course.setCourseprice(Double.valueOf(sourceAsMap.get("courseprice").toString()));
            course.setCoursenumber(Integer.valueOf(sourceAsMap.get("coursenumber").toString()));
            course.setCoursecontext(sourceAsMap.get("coursecontext").toString());
            course.setAuthername(sourceAsMap.get("authername").toString());
            course.setAutherimg(sourceAsMap.get("autherimg").toString());
            courseList.add(course);


        }
        return courseList;
    }

}