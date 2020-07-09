package com.blog.service.impl;

import com.blog.dao.ICommentMapper;
import com.blog.model.entity.Comment;
import com.blog.service.ICommentService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
评论管理服务实现类
2020-7-9
lwy
 */
public class ICommentServiceImple {
    @Service
    public class CommentImpl implements ICommentService {
        @Autowired
        private ICommentMapper iCommentMapper;

        //只查审核通过的评论
        @Override
        public List<Comment> findCommentByArticleId(Integer articleId) {
            return iCommentMapper.findCommentByArticleId(articleId);
        }

        @Override
        public Comment findCommentByCommentId(Integer commentId) {
            return iCommentMapper.findCommentByCommentId(commentId);
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

        //查全部评论
        @Override
        public List<Comment> findAllCommentByArticleId(Integer articleId) {
            return iCommentMapper.findAllCommentByArticleId(articleId);
        }

        //分页查询
        @Override
        public Pager findCommentPage(Page page) {
            Pager pager = new Pager();
            //查询总记录数
            Integer totalRecords = iCommentMapper.countArticle();
            //page对象永远不会为null，只能通过其属性来判断，严格讲所有属性都要判断
            Page page1 = null;
            if (page==null||page.getCurrentPage() ==null){
                page1 = new Page(totalRecords,page.getCountColumn());
            }
            //从当前页面的第一行记录开查，
            List<Comment> list = iCommentMapper.findArticlePage(page1.getCurrentPageFrist(),page1.getPageSize());
            pager.setPage(page1);
            pager.setList(list);
            return pager;
        }

    }
}
