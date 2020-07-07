package com.blog.model.entity;

import java.io.Serializable;

/**
 * 类型实体类
 * @Authoer 钟
 * @Version 1.0
 * @Date 2020.7.7
 */
public class Type implements Serializable {
    private Integer id;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
