package com.ed.mapper;

import com.ed.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select count(1) from 1908_course")
    long selectCourseCount();

    @Select("select * from 1908_course limit #{page},#{rows}")
    List<CourseEntity> selectCourse(@Param("page") Integer page, @Param("rows")Integer rows);

    @Select("select * from 1908_course where courseid = #{value}")
    List<CourseEntity> selectCourseCourseid(Integer courseid);

    @Insert("insert into t_shopping(shoppingcourseimg,shoppingcoursetitle,shoppingcourseid,shoppingcourseprice) " +
            "values(#{courseEntities.courseimg},#{courseEntities.coursetitle},#{courseEntities.courseid},#{courseEntities.courseprice})")
    void addShopping(@Param("courseEntities") CourseEntity courseEntities);

    @Delete("delete from t_shopping where shoppingid=#{shoppingid}")
    void delectShopping(Integer shoppingid);

    @Select("select count(1) from t_shopping")
    long selectShoppingCount();

    @Select("select * from t_shopping limit #{page},#{rows}")
    List<ShoppingEntity> selectShopping(@Param("page") Integer page, @Param("rows")Integer rows);

    @Select("select c.courseid,c.coursetitle,c.courseprice from 1908_course c where c.courseid=#{value}")
    CourseEntity getOrderById(String courseid);

    @Select("<script> " +
            "SELECT * FROM `1908_course`" +
            " WHERE 1=1 " +
            "<if test='coursetitle != null and coursetitle.length > 0'> " +
            "and LOWER(coursetitle) like concat('%',#{coursetitle},'%') " +
            "</if>"  +
            "</script>" )
    List<CourseEntity> searchCourse(CourseEntity course);

    @Insert("insert into t_order (ordernumber,orderdate,ordertitle,orderprice) values (#{out_trade_no},now(),#{subject},#{total_amount})")
    void addOrder(@Param("out_trade_no")String out_trade_no, @Param("total_amount")Double total_amount, @Param("subject")String subject);

    @Select("select * from t_order")
    List<Order> selectOrderList();
}
