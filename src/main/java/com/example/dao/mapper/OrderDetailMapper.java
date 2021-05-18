package com.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.OrderDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 13:55
 * @version: 1.0
 */
@Repository
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    void deleteOrderDetail(List<String> stringList);
}
