package com.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.SysPermission;
import com.example.pojo.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-06-03 16:25
 * @version: 1.0
 */
@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
//    @Select("select * from sys_permission where id in (select permission_id from sys_role_permission where role_id in <foreach collection=\\\"list\\\" index = \\\"index\\\" item = \\\"id\\\" open= \\\"(\\\" separator=\\\",\\\" close=\\\")\\\"> \n" +
//            "#{list} \n" +
//            "</foreach>)")
    List<SysPermission> selectByRoleId(List<Integer> list);
}
