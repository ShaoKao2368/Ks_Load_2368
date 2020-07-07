package com.blog.config;

import com.blog.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.concurrent.BrokenBarrierException;

/**
 * Web安全配置
 * sk
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/guest/**","/","/toLogin","/static/**","/js/**").permitAll()   //guest下所有的内容都可以访问
                .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")  //admin下内容仅admin可访问
                .anyRequest().authenticated();
        http.headers().contentTypeOptions().disable();
        http.csrf().disable();//防止跨站点请求伪造
        http.headers().frameOptions().sameOrigin();

        http.formLogin()
            .loginPage("/toLogin")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/myLogin")
                .defaultSuccessUrl("/myLogin")
            .failureUrl(("/toLogin?error"));
    }

    //重写以下方法，选择授权认证方式，内存身份验证，JDBC认证还是UserDetail认证
    //此项目使用UserDetail认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        super.configure(web);
        web.ignoring().antMatchers("/static/**");
    }
}
