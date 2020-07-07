package com.blog.service;

import com.blog.model.entity.Article;

import java.util.List;

/**
 * 博文服务层接口
 * @Author 钟
 * @Version 1.0
 * @Date 2020.7.7
 */
public interface IArticleService {
    //发表博文
    String publishArticle(Article article);
    //查找所有博文
    List<Article> findAllArticles();
    //查找博文
    List<Article> findArticle(Article article);
}
