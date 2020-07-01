package com.blog.service.impl;

import com.blog.dao.IUserMapper;
import com.blog.model.entity.User;
import com.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User 服务接口实现类
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
}
