package com.ed.mapper;


import com.ed.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.ed.model.*;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface UserMapper {


    @Select("select * from t_user where username=#{username}")
    UserModel Succ(String username);

    @Select("select * from t_user where username=#{username}")
    UserModel reg(String username);

    @Insert("insert into t_user (userpassword,username,userimage,phone) values (#{userpassword},#{username},'123',#{phone})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "userid", before = false, resultType = Integer.class)
    void addUser(UserModel user);

    @Select("select * from t_user where phone=#{phone}")
    UserModel fingName(String phone);

    @Select("select count(1) from 1908_course")
    long selectCourseCount();

    @Select("select * from 1908_course limit #{page},#{rows}")
    List<CourseEntity> selectCourse(@Param("page") Integer page, @Param("rows") Integer rows);

    @Select("select * from 1908_course where courseid = #{value}")
    List<CourseEntity> selectCourseCourseid(Integer courseid);

    @Insert("insert into t_shopping(shoppingcourseimg,shoppingcoursetitle,shoppingcourseid,shoppingcourseprice,userid) " +
            "values(#{courseEntities.courseimg},#{courseEntities.coursetitle},#{courseEntities.courseid},#{courseEntities.courseprice},#{userid})")
    void addShopping(@Param("courseEntities") CourseEntity courseEntities, @Param("userid") Integer userid);

    @Delete("delete from t_shopping where shoppingid=#{shoppingid}")
    void delectShopping(Integer shoppingid);

    @Select("select count(1) from t_shopping")
    long selectShoppingCount();

    @Select("select * from t_shopping where userid =#{userid} limit #{page},#{rows}")
    List<ShoppingEntity> selectShopping(@Param("page") Integer page, @Param("rows") Integer rows, @Param("userid") Integer userid);

    @Select("select c.courseid,c.coursetitle,c.courseprice from 1908_course c where c.courseid=#{value}")
    CourseEntity getOrderById(String courseid);

    @Select("<script> " +
            "SELECT * FROM `1908_course`" +
            " WHERE 1=1 " +
            "<if test='coursetitle != null and coursetitle.length > 0'> " +
            "and LOWER(coursetitle) like concat('%',#{coursetitle},'%') " +
            "</if>" +
            "</script>")
    List<CourseEntity> searchCourse(CourseEntity course);

    @Insert("insert into t_order (ordernumber,orderdate,ordertitle,orderprice,userid,status) values (#{out_trade_no},now(),#{subject},#{total_amount},#{userid},2)")
    void addOrder(@Param("out_trade_no") String out_trade_no, @Param("total_amount") Double total_amount, @Param("subject") String subject, @Param("userid") Integer userid);

    @Select("select * from t_order")
    List<Order> selectOrderList();

    @Select("select count(1) from 1908_course")
    long selectMianfCourseCount();

    @Select("select * from 1908_course c where c.lock=2")
    List<CourseEntity> selectMianfCourse(Integer page, Integer rows);

    @Select(" select * from 1908_course, 1908_course_type where coursetype = typeid and name = #{name} ")
    List<CourseEntity> selectCourseType(String name);

    @Select("select * from t_slideshow")
    List<Slideshow> selectSlideshow();

    @Select("select * from t_user t where t.username=#{value}")
    UserEntity userList(String username);

    @Update("UPDATE 1908_course c set c.coursenumber =c.coursenumber+1 where c.courseid = #{value}")
    void updateCourse(Integer courseid);

    @Select(" select * from 1908_course c where c.coursetime BETWEEN  date_format(DATE_SUB(curdate(), INTERVAL 1 MONTH),'%Y-%m-%d') AND SYSDATE() ORDER BY c.coursetime desc limit #{page}, #{rows} ")
    List<CourseEntity> newteachwell(Integer page, Integer rows);

    @Select(" select count(1) from 1908_course c where c.coursetime BETWEEN date_format(DATE_SUB(curdate(), INTERVAL 1 MONTH),'%Y-%m-%d') AND SYSDATE() ORDER BY c.coursetime desc ")
    long newteachwellCount();

    @Select(" select * from  1908_course c  ORDER BY c.coursenumber DESC LIMIT 8 ")
    List<CourseEntity> popularcoursesCount();

    @Select(" select * from 1908_course c ORDER BY c.coursenumber DESC LIMIT  #{page}, #{rows} ")
    List<CourseEntity> popularcourses(Integer page, Integer rows);

    @Select(" select * from 1908_course c , t_section s where c.courseid = s.sectionid and c.courseid = #{courseid} ")
    List<CourseEntity> selectCourseList(Integer courseid);

    @Select(" select * from 1908_course where courseid = #{courseid} ")
    CourseEntity queryCourseList(Integer courseid);
}