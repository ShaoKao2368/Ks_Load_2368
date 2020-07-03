package com.blog.service.impl;

import com.blog.dao.IRoleMapper;
import com.blog.model.entity.Role;
import com.blog.model.entity.User;
import com.blog.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * 角色管理服务实现类
 * lwy
 */
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleMapper iRoleMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Role> findRoleByLoginUser(User user) {
        //先从缓存获取角色
        Object obj = redisTemplate.opsForValue().get(user.getLoginName() + "_roles");

        if (obj != null) {
            return (List<Role>) obj;
        } else {
            List<Role> list = iRoleMapper.findRoleByLoginUser(user);
            if (list != null && list.size() > 0) {
                //加入缓存
                redisTemplate.opsForValue().set(user.getLoginName() + "_roles",list);
            }
            return list;
        }
    }
}
