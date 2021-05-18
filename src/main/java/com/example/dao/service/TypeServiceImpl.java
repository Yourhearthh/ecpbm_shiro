package com.example.dao.service;

import com.example.dao.mapper.TypeMapper;
import com.example.pojo.Type;
import com.example.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 13:43
 * @version: 1.0
 */
@Component
public class TypeServiceImpl extends BaseServiceImpl<TypeMapper, Type> {
    @Autowired
    TypeMapper typeMapper;


    public int addType(Type type) {
        return typeMapper.addType(type);
    }
}
