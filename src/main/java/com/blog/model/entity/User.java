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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loginName='" + loginName + '\'' +
                ", loginPass='" + loginPass + '\'' +
                ", email='" + email + '\'' +
                ", registerTime=" + registerTime +
                ", valid=" + valid +
                ", img='" + img + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
