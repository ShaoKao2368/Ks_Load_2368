package com.blog.service.impl;

import com.blog.model.entity.Role;
import com.blog.model.entity.User;
import com.blog.service.IRoleService;
import com.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

/**
 * UserDetailsService接口实现
 * 钟
 * 2020.7.3
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
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

            List<SimpleGrantedAuthority> simpleGrantedAuthorities = Arrays.asList(roles).stream()
                    .map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(user.getLoginName(),user.getLoginPass(),simpleGrantedAuthorities);
            
        }
        throw  new UsernameNotFoundException("用户名不存在");
    }
}
