package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 14:01
 * @version: 1.0
 */
@Data
@ToString
@TableName("user_info" )
public class UserInfo implements Serializable {

    private static final long serialVersionUID =  1495331397904264136L;

    @ApiModelProperty(value = "主键")
    @TableId
    private int uid;

    @ApiModelProperty(value = "用户名")
    @TableField("userName")
    private String userName;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "真实名称")
    @TableField("realName")
    private String realName;

    @ApiModelProperty(value = "性别")
    @TableField("sex")
    private String sex;

    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "注册日期")
    @TableField("regDate")
    private String regDate;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private int status;

    @ApiModelProperty(value = "状态")
    @TableField("salt")
    private String salt; // 加密密码的盐

    @ManyToMany(fetch = FetchType.EAGER) // 立即从数据库中进行加载数据;
    @JoinTable(name = "SysUserRole", joinColumns = {@JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    @TableField(exist = false)
    private List<SysRole> roleList; // 一个用户具有多个角色

    /**
     * 密码盐，重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解
     *
     * @return
     */
    public String getCredentialsSalt() {
        return this.userName + this.salt;
    }
}
