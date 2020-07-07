package com.blog.service.impl;

import com.blog.dao.ITypeMapper;
import com.blog.model.entity.Type;
import com.blog.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 博文类型服务接口实现类
 * @author  钟
 * @Version 1.0
 * @Date 2020.7.7
 */
@Service
@Transactional
public class ITypeServiceImpl implements ITypeService {
    @Autowired
    private ITypeMapper iTypeMapper;//注入博文类型访问接口
    @Override
    public List<Type> findType() {
        return iTypeMapper.findType();
    }
}
