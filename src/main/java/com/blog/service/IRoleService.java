package com.blog.service;

import com.blog.model.entity.User;

import javax.management.relation.Role;
import java.util.List;

/*
角色服务类
*@Author hedingjun
* *Version 1.0
* 2020-7-3
 */
public interface IRoleService {
    public List<Role> findRoleByLoginUser(User user);
}
