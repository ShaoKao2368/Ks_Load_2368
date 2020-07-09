package com.blog.dao;

import com.blog.model.entity.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 博文数据访问接口
 * @Author zql
 * @Version 1.0
 * @Date 2020.7.9
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
    //查记录总数
    @Select("select count(*) from t_article")
    public Integer countArticle();
    //分页查找
    List<Article> findArticlePage(@Param("article") Article article, @Param("first") Integer currentPageFirst, @Param("last") Integer currentPageLast);
    //修改博文
    boolean modifyArticle(Article article);
    //删除博文
    @Delete("delete from t_article where id=#{id}")
    Integer delArticle(Integer id);
    //审核博文
    @Update("update t_article set status=#{status} where id=#{id}")
    boolean auditArticle(@Param("status") String status, @Param("id") Integer id);
    //查看博文
    //比较复杂，需要关联查询类型和用户信息，所以只能到mapper.xml文件中去写查询
    Article findArticleById(Integer id);
}
