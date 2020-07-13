package com.blog.service;

import com.blog.model.entity.Article;
import com.blog.util.Page;
import com.blog.util.Pager;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * 博文服务层接口
 * @Author zql
 * @Version 1.0
 * @Date 2020.7.9
 */
public interface IArticleService {
    //发表博文
    String publishArticle(Article article);
    //查找所有博文
    List<Article> findAllArticles();
    //查找博文
    List<Article> findArticle(Article article);


    //分页面查询ArticleServiceImpl
    Pager<Article> findArticlePage(Article article, Page page);
    //修改博文
    boolean modifyArticle(Article article);
    //删除博文
    @Delete("delete from t_article where id=#{id}")
    Integer delArticle(Integer id);
    //审核博文
    boolean auditArticle(String status, Integer id);
    //查看博文
    Article findArticleById(Integer id);

    List<Article> findNewTop10();
}
