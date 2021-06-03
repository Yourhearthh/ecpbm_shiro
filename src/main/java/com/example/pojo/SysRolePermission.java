package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-06-03 16:19
 * @version: 1.0
 */
@Data
@ToString
@TableName("sys_role_permission" )
public class SysRolePermission {
    @ApiModelProperty(value = "用户id")
    @TableField("role_id")
    private int roleId;

    @ApiModelProperty(value = "用户id")
    @TableField("permission_id")
    private int permissionId;
}
