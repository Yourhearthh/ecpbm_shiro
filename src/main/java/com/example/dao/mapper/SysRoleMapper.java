package com.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.SysRole;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-06-03 16:23
 * @version: 1.0
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
    @Select("select * from sys_role where id in (select role_id from sys_user_role where uid=#{uid})")
    List<SysRole> selectByUid(int uid);
}
