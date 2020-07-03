package com.blog.model.entity;

import java.io.Serializable;

/**
 * 角色实体类
 * @Author LiWenyu
 * @Version1.0
 * 2020-7-3
 * lwy
 */
public class Role implements Serializable {
    private Integer id;//主键
    private String role;//角色名

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
