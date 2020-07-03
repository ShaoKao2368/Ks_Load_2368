package com.blog.web;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
public class UserController {
    @RequestMapping("/myLogin")
    public String myLogin(){
        System.out.println("登陆成功");
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());
        Collection collection=(Collection)userDetails.getAuthorities();
        Iterator<GrantedAuthority> iterator=collection.iterator();
        List<String> list=new ArrayList<>();
        while (iterator.hasNext()){
            GrantedAuthority grantedAuthority= iterator.next();
            list.add(grantedAuthority.getAuthority());
            System.out.println(grantedAuthority.getAuthority());
        }
        if (list!=null && list.contains("ROLE_ADMIN")){
            return "admin/home";
        }else{
            return "guest/index";
        }
    }
}
