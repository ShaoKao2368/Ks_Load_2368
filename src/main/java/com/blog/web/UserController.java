package com.blog.web;

import com.blog.dao.IUserMapper;
import com.blog.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 用户相关控制器
 *
 * @Author zql
 * @Version 1.0
 * ate 2020.7.9
 */
@Controller
public class UserController {
    @Autowired
    private IUserMapper iUserMapper;

    @RequestMapping("/myLogin")
    public String myLogin(HttpServletRequest request) {
        System.out.println("登陆成功");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());
        User user = iUserMapper.findByLoginName(userDetails.getUsername());
        request.getSession().setAttribute("user", user);
        Collection conllection = (Collection) userDetails.getAuthorities();
        Iterator<GrantedAuthority> iterator = conllection.iterator();
        List<String> list = new ArrayList<String>();
        while (iterator.hasNext()) {
            GrantedAuthority grantedAuthority = iterator.next();
            list.add(grantedAuthority.getAuthority());
            System.out.println(grantedAuthority.getAuthority());
        }
        if (list != null && list.contains("ROLE_ADMIN")) {
            return "/admin/home";
        } else {
            return "/guest/index";
        }
    }
    //用户个人信息,用户和管理员使用一个后台，不分开做，在页面中做权限控制
    @RequestMapping("/user/home")
    public String backHome(HttpServletRequest request){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());
        User user = iUserMapper.findByLoginName(userDetails.getUsername());
        request.getSession().setAttribute("user", user);
        return "/admin/home";
    }

}
