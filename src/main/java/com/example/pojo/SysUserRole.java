package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-06-03 16:17
 * @version: 1.0
 */
@Data
@ToString
@TableName("sys_user_role" )
public class SysUserRole {
    @ApiModelProperty(value = "用户id")
    @TableField("uid")
    private int uid;

    @ApiModelProperty(value = "用户id")
    @TableField("role_id")
    private int roleId;
}
