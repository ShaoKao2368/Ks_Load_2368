package com.blog.pblog;

import com.blog.model.entity.Article;
import com.blog.service.IArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ArticleTest {
    @Autowired
    private IArticleService iArticleService;
    @Test
    void findNewTop(){
        List<Article> list = iArticleService.findNewTop10();
        for (Article a:list) {
            System.out.println(a.toString());
        }
    }
}
