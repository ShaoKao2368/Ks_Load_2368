package com.blog;

import com.blog.model.entity.Article;
import com.blog.model.entity.Comment;
import com.blog.model.entity.User;
import com.blog.service.ICommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * zql
 * 2020.7.9
 */
@SpringBootTest
public class CommentTest {
    @Autowired
    private ICommentService iCommentService;
    @Test
    void addComment(){
        Comment comment=new Comment();
        Article article=new Article();
        article.setId(1);
        comment.setArticle(article);
        comment.setCommentTime(new Date());
        comment.setContent("这是一个测试评论");
        comment.setStatus("1");
        User user=new User();
        user.setId(1);
        comment.setUser(user);
        Integer n= iCommentService.addComment(comment);
        System.out.println(n==1?"添加成功":"添加失败");
    }
    @Test
    void findCommentByArticleId(){
        List<Comment> list=iCommentService.findCommentByArticleId(1);
        for(Comment comment:list){
            System.out.println(comment.toString());
        }
    }
    @Test
    void findCommentByCommentId(){
        Comment comment =iCommentService.findCommentByCommentId(1);
        System.out.println(comment.toString());
    }
    @Test
    void auditComment(){
        Comment comment=new Comment();
        comment.setId(1);
        comment.setStatus("-1");
        Integer n =iCommentService.auditComment(comment);
        System.out.println(n==1?"审核成功":"审核失败");
    }
    @Test
    void delComment(){
        Boolean flag=iCommentService.delComment(1);
        System.out.println(flag?"删除成功":"删除失败");

    }

}
