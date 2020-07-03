package com.blog.model.entity;

import java.util.Date;

/**
 * 用户实体类user
 *
 * @Aurhor钟巧利
 * @Version 1.0
 * 2020.6.30
 */
public class User {
    private Integer id;//主键
    private String name;//用户名
    private String loginName;//登录名
    private String loginPass;//登陆密码
    private String email;//注册邮箱
    private Date registerTime;//注册时间
    private boolean valid;//有效性，0无效，1有效
    private String img;//头像
    }
