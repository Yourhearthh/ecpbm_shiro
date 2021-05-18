package com.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pojo.OrderInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 13:59
 * @version: 1.0
 */
@Repository
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    Page<OrderInfo> getOrderInfoPage(Page<OrderInfo> orderInfoPage, String id, String uid, String status, String orderTimeFrom, String orderTimeTo);

    void deleteOrder(List<String> stringList);
}
