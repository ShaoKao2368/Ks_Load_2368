package com.blog.service.impl;

import com.blog.dao.IRoleMapper;
import com.blog.model.entity.Role;
import com.blog.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*角色管理服务
2020.7-3
lwy
 */
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleMapper iRoleMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Role> findRoleByLog
}
