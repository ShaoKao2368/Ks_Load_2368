package com.blog.service.impl;

import com.blog.dao.IArticleMapper;
import com.blog.model.entity.Article;
import com.blog.service.IArticleService;
import com.blog.util.Page;
import com.blog.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 博文服务层接口实现类
 */
@Transactional
@Service
public class IArticleServiceImpl implements IArticleService {
    @Autowired
    private IArticleMapper iArticleMapper;//注入博文数据访问接口
    @Autowired
    private RedisTemplate redisTemplate;//注入redis接口做数据缓存

    @Override
    public String publishArticle(Article article) {
        boolean flag = iArticleMapper.publishArticle(article);
        if (flag) {
            //缓存当前博文
            List<Article> list = iArticleMapper.findArticle(article);
            //清除掉前边缓存的数据
            Object obj = redisTemplate.opsForValue().get("article_list");
            if (obj != null) {
                redisTemplate.delete("article_list");
            }
            //重新缓存数据
            redisTemplate.opsForValue().set("article_list", list);
            return "发布成功";
        } else {
            return "发布失败";
        }
    }

    @Override
    public List<Article> findAllArticles() {
        List<Article> list = iArticleMapper.findAllArticles();
        if (list != null && list.size() > 0) {
            //清除掉前边缓存的数据
            Object obj = redisTemplate.opsForValue().get("article_list");
            if (obj != null) {
                redisTemplate.delete("article_list");
            }
            //重新缓存数据
            redisTemplate.opsForValue().set("article_list", list);
        }
        return list;
    }

    @Override
    public List<Article> findArticle(Article article) {
        return iArticleMapper.findArticle(article);
    }

    //分页查询
    @Override
    public Pager findArticlePage(Article article, Page page) {
        Pager pager=new Pager();
        //查出总记录数
        Integer totalRecords = iArticleMapper.countArticle();
        //page对象永远不会为null,只能通过其属性来判断，严格讲所有属性都要判断，只处做简化处理
        Page page1 = null;//page与page1是2个不同的对象
        if (page.getCurrentPage() == null) {
            page1 = new Page(totalRecords, 0);
        }else{
            page1=new Page(totalRecords,page.getCurrentPage());
        }
        //从当前页面的第一条记录开查，查记录数为页面大小
        List<Article> list = iArticleMapper.findArticlePage(article,page1.getCurrentPageFirst(),page1.getPageSize());
        pager.setPage(page1);
        pager.setList(list);
        return pager;
    }
    //修改博文
    @Override
    public boolean modifyArticle(Article article) {
        boolean flag = iArticleMapper.modifyArticle(article);
        if (flag) {
            //缓存当前博文
            List<Article> list = iArticleMapper.findArticle(article);
            //清除掉前边缓存的数据
            Object obj = redisTemplate.opsForValue().get("article_list");
            if (obj != null) {
                redisTemplate.delete("article_list");
            }
            //重新缓存数据
            redisTemplate.opsForValue().set("article_list", list);
            return true;
        } else {
            return false;
        }
    }
    //删除博文
    @Override
    public Integer delArticle(Integer id) {
        Integer num= iArticleMapper.delArticle(id);
        if(num==1){
            //清除缓存中数据
            Article article=new Article();
            article.setId(id);
            List<Article> list = iArticleMapper.findArticle(article);
            //清除掉前边缓存的数据
            Object obj = redisTemplate.opsForValue().get("article_list");
            if (obj != null) {
                redisTemplate.delete("article_list");
            }
            //重新缓存数据
            redisTemplate.opsForValue().set("article_list", list);
        }
        return num;
    }
    //审核博文
    @Override
    public boolean auditArticle(String status,Integer id) {
        return iArticleMapper.auditArticle(status,id);
    }
    //查看博文
    @Override
    public Article findArticleById(Integer id) {
        return iArticleMapper.findArticleById(id);
    }

    @Override
    public List<Article> findNewTop10() {
        return iArticleMapper.findNewTop10();
    }
}
