package com.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pojo.SysRole;
import com.example.pojo.UserInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 14:03
 * @version: 1.0
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    Page<UserInfo> getUserInfoPage(String userName, String sex, Page<UserInfo> dtoPage);

    // 更新客户状态
//    @Update("update user_info set status=#{flag} where id in (${ids})")
    void updateState(List<String> ids, int flag);

    @Select("select * from user_info where userName = #{userName}")
    UserInfo findByUsername(String userName);

}
