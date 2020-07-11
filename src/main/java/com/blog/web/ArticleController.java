package com.blog.web;

import com.blog.dao.IUserMapper;
import com.blog.model.entity.Article;
import com.blog.model.entity.Type;
import com.blog.model.entity.User;
import com.blog.service.IArticleService;
import com.blog.service.ITypeService;
import com.blog.util.ImageBase64Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private IUserMapper iUserMapper;
    @Autowired
    private IArticleService iArticleService;
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
    @RequestMapping("/user/publisArticle")
    public String publishArticle(HttpServletRequest request, Article article, MultipartFile imgFile, String typeid) throws IOException {
//        article.setPublishTime(new Date());
//        article.setStatus("1");
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = iUserMapper.findByLoginName(userDetails.getUsername());
//        article.setUser(user);
//        InputStream in = imgFile.getInputStream();
//        ByteBuffer nbf = ByteBuffer.allocate((int) imgFile.getSize());
//        byte[] array = new byte[1024];
//        int offset = 0;
//        int length = 0;
//        while ((length = in.read(array)) > 0) {
//            if (length != 1024) {
//                nbf.put(array, 0, length);
//            } else {
//                nbf.put(array);
//            }
//            offset += length;
//            break;
//        }
//        in.close();
//        byte[] content = nbf.array();
//        article.setImg(content.toString());
//
//        Type type = new Type();
//        type.setId(Integer.parseInt(typeid));
//        article.setType(type);
//        String msg = iArticleService.publishArticle(article);
//        request.setAttribute("publishMsg", msg);
//        return "article/publishArticle";

        //修改后
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = iUserMapper.findByLoginName(userDetails.getUsername());
        Article article1 = new Article();
        //防止重复提交
        article1.setTitle(article.getTitle());
        article1.setContent(article.getContent());
        List<Article> list = iArticleService.findArticle(article);//不能出现内容和标题完全相同的文章
        if (list != null && list.size() > 0) {
            return new ModelAndView("forward:/user/listArticle");
        }
        article.setPublishTime(new Date());
        article.setStatus("0");
        article.setUser(user);
        //先接受并保存上传的图片然后再做转换
        InputStream in = imgFile.getInputStream();
        String path = System.getProperty("user.dir");
        String filename = path + File.separator + imgFile.getOriginalFilename();    //获取文件名
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);  //取前缀名，不要“.”
        File file = new File(filename); //创建文件对象
        OutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer);
        }
        out.flush();
        in.close();
        out.close();
        String base64 = ImageBase64Utils.encodeImgageToBase64(file,suffix);
        article.setImg(base64); //把编码后的数据保存到博文对象的img字段
        if (file.exists()) {
            file.delete();      //删除保存的临时文件
        }
        Type type = new Type();
        type.setId(Integer.parseInt(typeid));
        article.setType(type);
        String msg = iArticleService.publishArticle(article);
        request.setAttribute("publishMsg" , msg);
        return new ModelAndView("forward:/user/listArticle");
    }

}
