package com.blog.service.impl;

import com.blog.dao.IUserMapper;
import com.blog.model.entity.User;
import com.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User 服务接口实现类
 * zzk
 */

//添加事务控制
@Transactional
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper iUserMapper;  //注入数据访问层接口
    @Autowired
    private RedisTemplate redisTemplate;    //注入redis接口做数据缓存

    @Override
    public User findByLoginName(String loginName){
        Object obj = redisTemplate.opsForValue().get(loginName);
        if (obj != null) {
            return (User)obj;
        } else {
            User user = iUserMapper.findByLoginName(loginName);
            if (user != null) {
                redisTemplate.opsForValue().set(user.getLoginName(),user);
                return user;
            } else {
                return null;
            }
        }
    }

    @Override
    public List<User> findAllUser(){
        Object obj = redisTemplate.opsForValue().get("allUser");
        if (obj != null) {
            return (List<User>) obj;
        } else {
            List<User> list = iUserMapper.findAllUser();
            if (list != null && list.size() > 0) {
                redisTemplate.opsForValue().set("allUser",list);
            }
            return list;
        }
    }

    @Override
    public String delUser(User user){
        Integer num  = iUserMapper.delUser(user);
        if (num != 0) {

            Object obj = redisTemplate.opsForValue().get("allUser");
            if (obj != null) {
                List<User> list = (List<User>) obj;

                list.stream().map((u) -> !u.getLoginName().equals(u.getLoginName())).collect(Collectors.toList());
                redisTemplate.opsForValue().set("allUser",list);
            }

            Object obj2 = redisTemplate.opsForValue().get(user.getLoginName() + "_list");
            if (obj2 != null) {
                List<User> list2 = (List<User>) obj2;
                list2.stream().map((u) -> !u.getLoginName().equals(u.getLoginName())).collect(Collectors.toList());
                redisTemplate.opsForValue().set(user.getLoginName() + "_list" ,list2);
            }
            redisTemplate.delete(user.getLoginName());
        }
        return num == 0 ? "删除失败" : "删除成功";
    }

    @Override
    public String modifyUser(User user){
        Integer num = iUserMapper.modifyUser(user);
        if (num != 0) {
            Object obj = redisTemplate.opsForValue().get("allUser");
            if (obj != null) {
                List<User> list = (List<User>) obj;
                for (User u : list) {
                    if (u.getLoginName().equals(user.getLoginName())) {
                        list.remove(u);
                        list.add(user);
                    }
                }
                redisTemplate.opsForValue().set("allUser",list);
            }
            Object obj2 = redisTemplate.opsForValue().get(user.getLoginName() + "_list");
            if (obj2 != null) {
                List<User> list2 = (List<User>) obj2;
                for (User u : list2) {
                    if (u.getLoginName().equals(user.getLoginName())) {
                        list2.remove(u);
                        list2.add(user);
                    }
                }
                redisTemplate.opsForValue().set(user.getLoginName() + "_list" , list2);
            }
            redisTemplate.opsForValue().set(user.getLoginName(),user);
        }
        return num == 0 ? "修改失败" : "修改成功";
    }

    @Override
    public List<User> findUser(User user){
        Object obj = redisTemplate.opsForValue().get(user.getLoginName() + "_list");
        if (obj != null) {
            return (List<User>) obj;
        } else {
            List<User> list = iUserMapper.findUser(user);
            if (list != null && list.size() > 0) {
                redisTemplate.opsForValue().set(user.getLoginName() + "_list" ,list);
            }
            return list;
        }
    }

    @Override
    public String addUser(User user){
        User oldUser = iUserMapper.findByLoginName(user.getLoginName());
        if (oldUser != null) {
            return "已经存在同名用户，不能添加！";
        } else {
            Integer num = iUserMapper.addUser(user);
            if (num != 0) {
                User newUser = iUserMapper.findByLoginName(user.getLoginName());
                Object obj = redisTemplate.opsForValue().get("allUser");
                if (obj != null) {
                    List<User> list = (List<User>) obj;
                    list.add(newUser);
                    redisTemplate.opsForValue().set("allUser",list);
                }
                Object obj2 = redisTemplate.opsForValue().get(newUser.getLoginName() + "_list");
                if (obj2 != null) {
                    List<User> list2 = (List<User>) obj2;
                    list2.add(newUser);
                    redisTemplate.opsForValue().set(newUser.getLoginName() + "_list",list2);
                }
                redisTemplate.opsForValue().set(newUser.getLoginName(),newUser);
            }
            return num == 0 ? "添加失败" : "添加成功";
        }
    }
}
