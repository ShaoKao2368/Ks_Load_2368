package com.blog.web;

import com.blog.dao.IUserMapper;
import com.blog.model.entity.Article;
import com.blog.model.entity.Type;
import com.blog.model.entity.User;
import com.blog.service.IArticleService;
import com.blog.service.ITypeService;
import com.blog.util.ImageBase64Utils;
import com.blog.util.Page;
import com.blog.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private IUserMapper iUserMapper;
    @Autowired
    private IArticleService iArticleService;
    @Qualifier("typeServiceImpl")
    @Autowired
    private ITypeService iTypeService;

    @RequestMapping("/guest/detail")
    public String detail(String article_id) {
        System.out.println("文章id" + article_id);
        return "/guest/article-detail";
    }

    @RequestMapping("/guest/findmore")
    public String findMore() {
        return "guest/allArticleList";
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping("/user/toPublishArticle")
    public String toPublishArticle(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());
        User user = iUserMapper.findByLoginName(userDetails.getUsername());
        request.getSession().setAttribute("user", user);
        //查一下文章类型
        List<Type> list = iTypeService.findType();
        request.setAttribute("typeList", list);
        return "article/publishArticle";
    }

    //发布博文
    @PostMapping("/user/publishArticle")
    public ModelAndView publishArticle(HttpServletRequest request, Article article, MultipartFile imgFile, String typeid) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = iUserMapper.findByLoginName(userDetails.getUsername());
        Article article1 = new Article(); //注意是2个对象，与article不是同一个对象
        //防止重复提交
        article1.setTitle(article.getTitle());
        article1.setContent(article.getContent());
        List<Article> list = iArticleService.findArticle(article);//不能出现相同标题和内容完全相同的文章
        if (list != null && list.size() > 0) {
            return new ModelAndView("forward:/user/listArticle");//如果已经存在此博文，直接跳转到列表页面，不再执行保存操作
        }
        article.setPublishTime(new Date());
        article.setStatus("0");//0为未审核，1为审核通过，-1为审核拒绝
        article.setUser(user);
        //先接收并保存上传的图片然后再做转换
        InputStream in = imgFile.getInputStream();
        String path = System.getProperty("user.dir");
        String filename = path + File.separator + imgFile.getOriginalFilename();//获取文件名
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);//取后缀名,不要"."
        File file = new File(filename);//创建文件对象
        OutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer);
        }
        out.flush();
        in.close();
        out.close();
        String base64 = ImageBase64Utils.encodeImgageToBase64(file, suffix);
        article.setImg(base64);//把编码后的数据保存到博文对象的img字段
        //处理完毕后，删除文件
        if (file.exists()) {
            file.delete();//删除保存的临时文件
        }
        Type type = new Type();
        type.setId(Integer.parseInt(typeid));
        article.setType(type);
        String msg = iArticleService.publishArticle(article);
        request.setAttribute("publishMsg", msg);
        return new ModelAndView("forward:/user/listArticle");
    }

    /**
     * 根据用户查询博文
     * 使用了分页查询
     *
     * @param request
     * @param requestPage 请求的当前页面编号
     * @return
     */
    @RequestMapping("/user/listArticle")
    public String listArticleByUser(HttpServletRequest request, Integer requestPage) {
        Article article = new Article();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = iUserMapper.findByLoginName(userDetails.getUsername());
        article.setUser(user);
        //List<Article> list=iArticleService.findArticle(article);//查全部
        Page page = new Page();
        if (requestPage != null) {
            page.setCurrentPage(requestPage);
        }
        //分页查询
        Pager pager = iArticleService.findArticlePage(article, page);
        request.setAttribute("pager", pager);
        return "admin/listArticle";
    }

    /**
     * 前台使用ajax调用
     *
     * @param requestPage
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/ajax/listArticle")
    @ResponseBody //使用@ResponseBody返回的是JSON对象，前台使用Jquery的each方法进行遍历
    public Pager listArticleByUser(Integer requestPage, HttpServletResponse response) throws IOException {
        Article article = new Article();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = iUserMapper.findByLoginName(userDetails.getUsername());
        article.setUser(user);
        Page page = new Page();
        if (requestPage != null) {
            page.setCurrentPage(requestPage);
        }
        //分页查询
        Pager<Article> pager = iArticleService.findArticlePage(article, page);
        return pager;
    }

    //跳转到修改博文页面，携带数据article
    @RequestMapping("/user/toModifyArticle")
    public String toModifyArticle(HttpServletRequest request, Integer id) {
        Article article = new Article();
        article.setId(id);
        List<Article> list = iArticleService.findArticle(article);
        if (list != null && list.size() > 0) {
            article = list.get(0);
            article.setImg("data:image/jpg;base64," + article.getImg());
            request.setAttribute("article", list.get(0));
        } else {
            request.setAttribute("article", article);
        }
        List<Type> typeList = iTypeService.findType();
        request.setAttribute("typeList", typeList);
        return "article/modifyArticle";
    }

    //修改博文
    @RequestMapping("/user/modifyArticle")
    public ModelAndView modifyArticle(HttpServletRequest request, Article article, MultipartFile imgFile, String typeid) throws IOException {
        Article article1 = new Article(); //注意是2个对象，与article不是同一个对象
        //防止重复提交
        article1.setContent(article.getContent());
        article.setModifyTime(new Date());//设置修改时间
        //先接收并保存上传的图片然后再做转换
        if (imgFile != null && !imgFile.getOriginalFilename().equals("")) {
            InputStream in = imgFile.getInputStream();
            String path = System.getProperty("user.dir");
            String filename = path + File.separator + imgFile.getOriginalFilename();//获取文件名
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);//取后缀名,不要"."
            File file = new File(filename);//创建文件对象
            OutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer);
            }
            out.flush();
            in.close();
            out.close();
            String base64 = ImageBase64Utils.encodeImgageToBase64(file, suffix);
            article.setImg(base64);//把编码后的数据保存到博文对象的img字段
            //处理完毕后，删除文件
            if (file.exists()) {
                file.delete();//删除保存的临时文件
            }
        }
        Type type = new Type();
        type.setId(Integer.parseInt(typeid));
        article.setType(type);
        // String msg = iArticleService.publishArticle(article);//与发表博文仅此方法不同
        boolean flag = iArticleService.modifyArticle(article);
        String msg = flag ? "修改成功" : "修改失败";
        request.setAttribute("modifyMsg", msg);
        return new ModelAndView("forward:/user/listArticle");
    }
    @RequestMapping("//user/delArticle")
    public ModelAndView delArticle(HttpServletRequest request,Integer id){
        Integer num=iArticleService.delArticle(id);
        if(num==1){
            request.setAttribute("delMsg","删除成功");
        }else{
            request.setAttribute("delMsg","删除失败");
        }
        return new ModelAndView("forward:/user/listArticle");
    }
    //跳转到审核博文页面,只有管理员才可以调用此方法
    @RolesAllowed("ROLE_ADMIN")
    @RequestMapping("/admin/toAuditArticle")
    public String toAuditArticle(HttpServletRequest request,Integer requestPage){
        Article article = new Article();
        Page page = new Page();
        if (requestPage != null) {
            page.setCurrentPage(requestPage);
        }
        //分页查询
        Pager pager = iArticleService.findArticlePage(article, page);
        List<Article> list= iArticleService.findAllArticles();
        pager.setList(list);
        request.setAttribute("pager", pager);
        return "article/auditArticle";
    }
    //审核博文
    @RolesAllowed("ROLE_ADMIN")
    @RequestMapping("/admin/auditArticle")
    public String auditArticle(HttpServletRequest request,String status,Integer id){
        boolean flag=false;
        if(status!=null&&!status.equals("")){
          flag=  iArticleService.auditArticle(status,id);
        }
        String msg=flag?"审核成功":"审核失败";
       request.setAttribute("auditMsg",msg);
        //分页查询
        Pager pager = iArticleService.findArticlePage(new Article(), null);
        List<Article> list= iArticleService.findAllArticles();
        pager.setList(list);
        request.setAttribute("pager", pager);
       return "article/auditArticle";
    }
    //查看博文
    @RolesAllowed("ROLE_ADMIN")
    @RequestMapping("/admin/showArticle")
    public String showArticle(HttpServletRequest request,Integer id){
        Article article=iArticleService.findArticleById(id);
        //处理一下图片
        article.setImg("data:image/jpg;base64," + article.getImg());
        request.setAttribute("article",article);
        return "article/show";
    }
}
