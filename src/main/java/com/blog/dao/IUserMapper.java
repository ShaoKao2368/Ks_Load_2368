package com.blog.dao;

import com.blog.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * USER数据访问接口
 * 一、使用java注解方式
 * 1、4个注解
 * @Insert
 * @Dalete
 * @Update
 * @Select
 * 2、优点
 * 简单
 * 3、缺点
 * 不能使用动态sql
 * @Athor 钟
 * @Version 1.0
 * 2020.6.30
 */
@Mapper
@Repository
public interface IUserMapper {
    /*

     */
    @Results(id="userMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "login_name",property = "login_name"),
            @Result(column = "login_pass",property = "login_pass"),
            @Result(column = "email",property = "email"),
            @Result(column = "valid",property = "valid"),
            @Result(column = "img",property = "img")
    })
    @Select("select *from t_user where login_name=#{loginName}")
    public User findByLoginName(String loginName);//按照登录名查询用户，用于登录验证
    //
    public List<User> findAllUser();//查找所有用户
    public int delUser(User user);//删除指定用户，删除成功返回记录数，删除失败返回0
    public int modifyUser(User user);//修改指定用户，修改成功返回记录数，修改失败返回0
    public List<User> findUser(User user);//多条件查找用户
    public int addUser(User user);//添加用户，注册用户

}
