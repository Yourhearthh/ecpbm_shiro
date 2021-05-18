package com.example.service;

import com.example.dao.mapper.UserInfoMapper;
import com.example.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 14:15
 * @version: 1.0
 */
@Service
public class UserService {
    @Autowired
    UserInfoMapper userInfoMapper;

    public UserInfo findByUsername(String userName) {
        return userInfoMapper.findByUsername(userName);
    }



}
