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
@TableName("product_info" )
public class ProductInfo implements Serializable {

    private static final long serialVersionUID =  1495331397904264136L;

    @ApiModelProperty(value = "主键")
    @TableId
    private int id; // 商品编号

    @ApiModelProperty(value = "商品编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "商品名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "商品类型")
    @TableField("tid")
    private int tid;

    @ApiModelProperty(value = "商品品牌")
    @TableField("brand")
    private String brand;

    @ApiModelProperty(value = "商品小图")
    @TableField("pic")
    private String pic;

    @ApiModelProperty(value = "商品数量")
    @TableField("num")
    private int num;

    @ApiModelProperty(value = "商品价格")
    @TableField("price")
    private double price;

    @ApiModelProperty(value = "商品介绍")
    @TableField("intro")
    private String intro;

    @ApiModelProperty(value = "商品状态")
    @TableField("status")
    private int status;
}
