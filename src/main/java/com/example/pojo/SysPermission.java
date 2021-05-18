package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 14:24
 * @version: 1.0
 */
@Data
@ToString
@TableName("sys_permission" )
public class SysPermission implements Serializable {

    private static final long serialVersionUID =  1495331397904264136L;

    @ApiModelProperty(value = "主键")
    @TableId
    private Integer id;//主键.

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;//名称.

    @ApiModelProperty(value = "资源类型")
    @TableField("resourceType")
    private String resourceType;//资源类型，[menu|button]

    @ApiModelProperty(value = "资源路径")
    @TableField("url")
    private String url;//资源路径.

    @ApiModelProperty(value = "权限字符串")
    @TableField("permission")
    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view

    @ApiModelProperty(value = "父编号")
    @TableField("parentId")
    private Long parentId; //父编号

    @ApiModelProperty(value = "父编号列表")
    @TableField("parentIds")
    private String parentIds; //父编号列表

    @ApiModelProperty(value = "")
    @TableField("available")
    private Boolean available = Boolean.FALSE;

    @ManyToMany
    @JoinTable(name = "SysRolePermission", joinColumns = {@JoinColumn(name = "permissionId")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    @TableField(exist = false)
    private List<SysRole> roles;
}
