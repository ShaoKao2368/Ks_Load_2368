package com.blog.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 博文实体类
 * @Author 钟
 * @Version 1.0
 * @Date 2020.7.7
 */
public class Article implements Serializable {
    private Integer id;
    private String title;
    private String content;
    private Type type;
    private User user;
    private Date publishTime;
    private Date modifyTime;
    private String status;
    private String enableComment;
    private String img;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnableComment() {
        return enableComment;
    }

    public void setEnableComment(String enableComment) {
        this.enableComment = enableComment;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", user=" + user +
                ", publishTime=" + publishTime +
                ", modifyTime=" + modifyTime +
                ", status='" + status + '\'' +
                ", enableComment='" + enableComment + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
