package com.blog.service;



import com.blog.model.entity.Comment;

import com.blog.util.Pager;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/*
评论服务接口
2020-7-9、lwy
 */
public interface ICommentService {
    //根据博文查询评论，仅审核过了的
    List<Comment> findCommentByArticleId(Integer ArticleId);
    //根据评论id查询评论
    Comment findCommentByCommentId(Integer commentId);
    //添加评论
    Integer addComment(Comment comment);
    //删除评论
    boolean delComment(Integer commentId);
    //审核评论

    @Update("update t_comment set status=#{comment.satus}")
    Integer auditComment(Comment comment);
    //查看所有博文对应的评论
    List<Comment> findAllCommentByArticleId(Integer articleId);
    //分页查询
    Pager findCommentPage(Page page);//(pager包好像有问题)

    List<Comment> findAllCommentByArticle(Integer articleId);

    //分页查询
    Pager findCommentPage(com.blog.util.Page page);
}
