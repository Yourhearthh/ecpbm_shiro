package com.example.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 14:04
 * @version: 1.0
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDetailDto {
    @ApiModelProperty("客户名称")
    private String userName;

    @ApiModelProperty("订单金额")
    private double totalMoney;

    @ApiModelProperty(value = "订单日期")
    private String orderTime;

    @ApiModelProperty(value = "订单状态")
    private String status;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "单价")
    private double price;

    @ApiModelProperty(value = "数量")
    private int num;

    @ApiModelProperty(value = "小计")
    private double total;
}
