package com.blog.dao;

import com.blog.model.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 钟
 * 2020.7.7
 */
@Mapper
@Repository
public interface IArticleMapper {
    //发表博文
    public boolean publishArticle(Article article);
    //查找所有博文
    public List<Article> findAllArticles();
    //查找博文
    public List<Article> findArticle(Article article);
}
