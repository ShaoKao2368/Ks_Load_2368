package com.blog.service.impl;

import com.blog.dao.ICommentMapper;
import com.blog.model.entity.Comment;
import com.blog.service.ICommentService;
import com.blog.util.Page;
import com.blog.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论管理服务实现类
 *  @Author lwy
 *  @Version 1.0
 *  @Date  2020.7.9
 */
@Service
public class ICommentServiceImpl implements ICommentService {
    @Autowired
    private ICommentMapper iCommentMapper;
    //只查看审核通过的评论
    @Override
    public List<Comment> findCommentByArticleId(Integer articleId) {
        return iCommentMapper.findCommentByArticleId(articleId);
    }

    @Override
    public Comment findCommentByCommentId(Integer commentId) {
        return iCommentMapper.findCommentByCommentId(commentId);
    }


    //代码外构造     另加

    @Override
    public List<Comment> findAllCommentByArticleId(Integer articleId) {
        return null;
    }

    @Override
    public Pager findCommentPage(com.github.pagehelper.Page page) {
        return null;
    }


    @Override
    public Integer addComment(Comment comment) {
        return iCommentMapper.addComment(comment);
    }
    @Override
    public boolean delComment(Integer commentId) {
        return iCommentMapper.delComment(commentId);
    }
    @Override
    public Integer auditComment(Comment comment) {
        return iCommentMapper.auditComment(comment);
    }


    @Override
    public List<Comment> findAllCommentByArticle(Integer articleId) {
        return iCommentMapper.findCommentByArticleId(articleId);
    }
    //分页查找
    @Override
    public Pager findCommentPage(Page page) {
        Pager pager=new Pager();
        //查出总记录数
        Integer totalRecords = iCommentMapper.countArticle();
        //page对象永远不会为null，只能通过其属性来判断，严格讲所有属性都要判断，只做简化处理
        Page page1 = null;//page与page1是两个不同的对象
        if (page==null||page.getCurrentPage() == null){
            page1 = new Page(totalRecords, 0);
        }else{
            page1 = new Page(totalRecords,page.getCurrentPage());
        }
        //从当前页面的第一条记录开查，查记录数为页面大小
        List<Comment> list = iCommentMapper.findArticlePage(page1.getCurrentPageFirst(),page1.getPageSize());
        pager.setPage(page1);
        pager.setList(list);
        return pager;
    }
}
