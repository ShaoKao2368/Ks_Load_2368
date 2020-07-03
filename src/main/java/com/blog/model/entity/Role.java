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

    public Integer getId(){
        return id;
    }
}
