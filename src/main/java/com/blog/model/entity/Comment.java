package com.blog.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论实体类
 * zql
 * 2020.7.9
 */
public class Comment implements Serializable {
    private Integer id;//主键
    private Article article;//关联的博文
    private String content;//评论内容
    private Date commentTime;//评论时间
    private String ip;//评论人ip
    private String status;//审核状态
    private User user;//评论人



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", article=" + article +
                ", content='" + content + '\'' +
                ", commentTime=" + commentTime +
                ", ip='" + ip + '\'' +
                ", status='" + status + '\'' +
                ", user=" + user +
                '}';
    }
}
