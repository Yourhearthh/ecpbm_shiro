package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 13:40
 * @version: 1.0
 */
@Data
@ToString
@TableName("order_info" )
public class OrderInfo implements Serializable {

    private static final long serialVersionUID =  1495331397904264136L;
    @ApiModelProperty(value = "主键")
    @TableId
    private Integer Id;

    @ApiModelProperty(value = "用户id")
    @TableField("uid")
    private int uid;

    @ApiModelProperty(value = "订单状态（已付款，未付款）")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "订单创建时间")
    @TableField("ordertime")
    private String ordertime;

    @ApiModelProperty(value = "订单金额")
    @TableField("orderprice")
    private double orderprice;

//    @ApiModelProperty(value = "用户")
//    @TableField(exist = false)
//    private UserInfo ui;
}
