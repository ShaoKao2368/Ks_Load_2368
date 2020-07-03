package com.blog.service;

import com.blog.model.entity.Role;
import com.blog.model.entity.User;

import java.util.List;

/*
角色服务类
*@Author lwy
* *Version 1.0
* 2020-7-3
 */
public interface IRoleService {
    public List<Role> findRoleByLoginUser(User user);
}
