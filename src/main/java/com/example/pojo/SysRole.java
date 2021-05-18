package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 14:18
 * @version: 1.0
 */
@Data
@ToString
@TableName("sys_role" )
public class SysRole implements Serializable {

    private static final long serialVersionUID =  1495331397904264136L;

    @ApiModelProperty(value = "主键")
    @TableId
    private Integer id; // 编号

    @ApiModelProperty(value = "角色")
    @TableField("role")
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description; // 角色描述,UI界面显示使用

    @ApiModelProperty(value = "是否可用")
    @TableField("available")
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户

    // 用户 - 角色关系定义;
    @ManyToMany
    @JoinTable(name = "SysUserRole", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "uid")})
    @TableField(exist = false)
    private List<UserInfo> userInfos;// 一个角色对应多个用户

    //角色 -- 权限关系：多对多关系;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SysRolePermission", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    @TableField(exist = false)
    private List<SysPermission> permissions;
}
