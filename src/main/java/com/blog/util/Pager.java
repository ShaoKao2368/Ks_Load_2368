package com.blog.util;
import java.io.Serializable;
import java.util.List;

/**
 * 分页工具,泛型类
 * Pager对象中封装了2个成员
 * 1、任何对象组成的List
 * 2、Page对象IArticleService
 * @Author zql
 * @Version 1.0
 * @Date 2020.7.9
 */
public class Pager<T> implements Serializable {
    private  List<T> list;
    private Page page;
    public Pager() {}

    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
    public Page getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = page;
    }
}
