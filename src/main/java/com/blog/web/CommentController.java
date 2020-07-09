package com.blog.web;

import com.blog.dao.IUserMapper;
import com.blog.model.entity.Article;
import com.blog.model.entity.Comment;
import com.blog.model.entity.User;
import com.blog.service.ICommentService;
import com.blog.util.Page;
import com.blog.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 博文评论
 * zql
 * 2020.7.9
 */
@Controller
public class CommentController {
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private IUserMapper iUserMapper;
    //评论
    @RequestMapping("/user/comment")
    public ModelAndView comment(Comment comment, Integer articleId, HttpServletRequest request){
        comment.setStatus("0");//默认为0，1审核通过，-1审核拒绝
        comment.setCommentTime(new Date());//评论时间
        UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=iUserMapper.findByLoginName(userDetails.getUsername());
        comment.setUser(user);//评论用户
        Article article= new Article();//关联的博文
        article.setId(articleId);
        comment.setArticle(article);
        String ip=request.getServerName();
        comment.setIp(ip);
        Integer n=iCommentService.addComment(comment);
        String commentMsg=n == 1?"评论成功":"评论失败";
        request.setAttribute("commentMsg",commentMsg);
        return new ModelAndView("forward:/");//评论成功跳回首页

    }
    //管理评论页面仅操作员可操作
    @RolesAllowed("/ROLE_ADMIN")
    @RequestMapping("/admin/toAuditComment")
    public String toAuditComment(HttpServletRequest request,Integer requestPage){
        Comment comment=new Comment();
        Page page=new Page();
        if (requestPage !=null){
            page.setCurrentPage(requestPage);
        }
        //分页查询
        Pager pager =iCommentService.findCommentPage(page);
        request.setAttribute("page",pager);
        return "/comment/auditComment";
    }
    //审核评论
    @RequestMapping("/admin/auditComment")
    public ModelAndView auditComment(String status,Integer id,HttpServletRequest request){
        Comment comment =new Comment();
        comment.setStatus(status);
        comment.setId(id);
        Integer n=iCommentService.auditComment(comment);
        String auditMsg =n==1?"审核成功":"审核失败";
        request.setAttribute("auditMsg",auditMsg);
        return new ModelAndView("forward:/admin/toAuditComment");//在转回评论列表页面

    }
    //删除评论
    @RequestMapping("/admin/delComment")
    public ModelAndView delComment(Integer id, HttpServletRequest request){
        boolean flag=iCommentService.delComment(id);
        String delMsg=flag?"删除成功":"删除失败";
        request.setAttribute("delMsg",delMsg);
        return new ModelAndView("forward:/admin/toAuditComment");//在转回评论列表页面
    }
}
