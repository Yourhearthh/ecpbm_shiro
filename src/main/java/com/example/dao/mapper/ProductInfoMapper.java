package com.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.ProductInfo;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 13:53
 * @version: 1.0
 */
@Repository
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {
    void deleteProduct(List<String> ids, int flag);

    // 添加商品
    @Insert("insert into product_info(code,name,tid,brand,pic,num,price,intro,status) "
            + "values(#{code},#{name},#{tid},#{brand},#{pic},#{num},#{price},#{intro},#{status})")
    void addProductInfo(ProductInfo productInfo);

    void deleteProductById(List<String> list);
}
