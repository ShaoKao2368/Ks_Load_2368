package com.blog.service.impl;

import com.blog.model.entity.Role;
import com.blog.model.entity.User;
import com.blog.service.IRoleService;
import com.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserDetailsService接口实现
 * 钟
 * 2020.7.3
 */
@Service
public class UserDetailsServiceImpl {
    @Autowired
    private IUserService iUserService;//注入用户服务接口
    @Autowired
    private IRoleService iRoleService;//注入角色服务接口
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException{
        User user =iUserService.findByLoginName(s);//根据登录名查用户信息
        if(user!=null){
            List<Role> list=iRoleService.findRoleByLoginUser(user);//根据用户查角色
            StringBuffer roles=new StringBuffer();//用于保存角色名
            for (Role r:list){
                roles.append(r.getRole());
            }
            
        }
    }
}
