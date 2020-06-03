package com.ed.mapper;

import com.ed.model.UserModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from t_user where username=#{username}")
    UserModel Succ(String username);
    @Select("select * from t_user where username=#{username}")
    UserModel reg(String username);
    @Insert("insert into t_user (userpassword,username,userimage,phone) values (#{userpassword},#{username},'123',#{phone})")
    @SelectKey( statement = "select last_insert_id()", keyProperty = "userid", before = false, resultType = Integer.class)
    void addUser(UserModel user);
    @Select("select * from t_user where phone=#{phone}")
    UserModel fingName(String phone);
}
