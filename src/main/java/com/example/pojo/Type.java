package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 13:38
 * @version: 1.0
 */
@Data
@ToString
@TableName("type" )
public class Type implements Serializable {

    private static final long serialVersionUID =  1495331397904264136L;
    @TableId
    private int id; // 产品类型编号
    @TableField("name")
    private String name; // 产品类型名称

}
