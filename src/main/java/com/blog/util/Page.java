package com.blog.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 分页工具
 *
 * @Author zql
 * @Version 1.0
 * @Date 2020.7.9
 */
@Component
public class Page implements Serializable {
    //静态变量不能直接注入值,可以通过非静态方法注入值
    private static Integer pageSize;//分页大小
    private Integer currentPage;//当前页号
    private Integer totalPages;//总页数
    private Integer prePage;//前一页
    private Integer nextPage;//后一页
    private Integer firstPage;//第一页
    private Integer lastPage;//最后一页
    private Integer recordsNum;//记录总数
    private Integer currentPageFirst;//当前页面的第一条记录
    private Integer currentPageLast;//当前页面的最后一条记录

    //必须要一个无参的构造函数，否则不能定义有参的构造函数
    public Page() {
    }

    //构造函数获取记录总数和当前页面
    public Page(Integer recordsNum, Integer currentPage) {
        this.recordsNum = recordsNum;
        this.currentPage = currentPage;
        //计算总页数
        totalPages = (int) Math.floor(recordsNum / pageSize)+1;
        firstPage = 0;
        lastPage = totalPages;//最后一页
        Integer num = currentPage;
        //前一页
        prePage = currentPage == 0 ? 0 : --num;
        //后一页
        num = currentPage;
        nextPage = nextPage == totalPages ? nextPage : ++num;
        currentPageFirst =this.currentPage==0?0:this.currentPage*this.pageSize;//查询时包括第一条记录
        currentPageLast = (this.currentPage+1) * this.pageSize;//查询时不包括最后一条记录
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getPrePage() {
        return prePage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public Integer getFirstPage() {
        return firstPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void setRecordsNum(Integer recordsNum) {
        this.recordsNum = recordsNum;
    }

    public Integer getRecordsNum() {
        return recordsNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    //注入属性文件中的页面大小值
    @Value("${page.size}")
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    //当前页面第一条记录
    public Integer getCurrentPageFirst() {
        return currentPageFirst;
    }

    //当前页面最后一条记录
    public Integer getCurrentPageLast() {
        return currentPageLast;
    }


}
