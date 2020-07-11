package com.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("/guest/index");
        registry.addViewController("/error").setViewName("/guest/error");
        registry.addViewController("/toLogin").setViewName("/guest/login");
        registry.addViewController("/guest/about").setViewName("/guest/about");
        registry.addViewController("/guest/contact").setViewName("/guest/contact");
        //registry.addViewController("/user/toPublishArticle").setViewName("/article/publishArticle");
        registry.addViewController("/admin/toAuditArticle").setViewName("/article/auditArticle");
        registry.addViewController("/user/toModifyArticle").setViewName("/article/modifyArticle");
        registry.addViewController("/user/toDelArticle").setViewName("/article/delArticle");
        registry.addViewController("/admin/toAuditComment").setViewName("/comment/auditComment");
        registry.addViewController("//user/toDelComment").setViewName("/comment/delComment");
        registry.addViewController("/admin/readCounts").setViewName("/statistic/readCounts");
        registry.addViewController("/admin/replyCounts").setViewName("/statistic/replyCounts");
        registry.addViewController("/admin/resetPass").setViewName("/admin/resetPass");
        registry.addViewController("/user/modifyPass").setViewName("/admin/modifyPass");
        registry.addViewController("/admin/userManage").setViewName("/admin/userManage");
    }
}
