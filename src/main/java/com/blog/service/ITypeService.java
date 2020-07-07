package com.blog.service;

import com.blog.model.entity.Type;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 博文类型服务类
 * @Author 钟
 * @Version 1.0
 * @Date 2020.7.7
 */
@Service
public interface ITypeService {
    public List<Type> findType();
}
