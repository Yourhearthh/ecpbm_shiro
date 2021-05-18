package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dao.mapper.OrderDetailMapper;
import com.example.dao.mapper.OrderInfoMapper;
import com.example.dao.service.OrderDetailServiceImpl;
import com.example.dao.service.OrderInfoServiceImpl;
import com.example.dao.service.ProductInfoServiceImpl;
import com.example.dao.service.UserInfoServiceImpl;
import com.example.dto.OrderInfoDetailDto;
import com.example.pojo.OrderDetail;
import com.example.pojo.OrderInfo;
import com.example.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 14:00
 * @version: 1.0
 */
@Service
public class OrderService {
    @Autowired
    OrderInfoServiceImpl orderInfoService;
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    OrderDetailServiceImpl orderDetailService;
    @Autowired
    ProductInfoServiceImpl productInfoService;
    @Autowired
    OrderDetailMapper orderDetailMapper;

    /**
     * 订单分页列表
     * @param pageNum
     * @param pageSize
     * @param id
     * @param userName
     * @param status
     * @param orderTimeFrom
     * @param orderTimeTo
     * @return
     */
    public Page<OrderInfo> getOrderPage(Integer pageNum, Integer pageSize, String id, String userName, String status, String orderTimeFrom, String orderTimeTo) {
        LambdaQueryWrapper<OrderInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (id != null) {
            queryWrapper.eq(OrderInfo::getId, id);
        }
        if (userName != null) {
            UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().eq("userName", userName));
            queryWrapper.eq(OrderInfo::getUid, userInfo.getUid());
        }
        if (status != null) {
            queryWrapper.eq(OrderInfo::getStatus, status);
        }
        if (orderTimeFrom != null) {
            queryWrapper.gt(OrderInfo::getOrdertime, orderTimeFrom);
        }
        if (orderTimeTo != null) {
            queryWrapper.lt(OrderInfo::getOrdertime, orderTimeTo);
        }
        Page<OrderInfo> infoPage = orderInfoService.page(new Page<OrderInfo>(pageNum, pageSize), queryWrapper);
        return infoPage;
    }

    /**
     * 订单分页列表
     * @param pageNum
     * @param pageSize
     * @param id
     * @param userName
     * @param status
     * @param orderTimeFrom
     * @param orderTimeTo
     * @return
     */
    public Page<OrderInfo> getOrderInfoPage(Integer pageNum, Integer pageSize, String id, String userName, String status, String orderTimeFrom, String orderTimeTo) {
        if (userName != null) {
            UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().eq("userName", userName));
            int uid = userInfo.getUid();
            Page<OrderInfo> orderInfoPage = orderInfoMapper.getOrderInfoPage(new Page<OrderInfo>(pageNum, pageSize), id, String.valueOf(uid), status, orderTimeFrom, orderTimeTo);
            return orderInfoPage;
        } else {
            String uid1 = null;
            Page<OrderInfo> orderInfoPage1 = orderInfoMapper.getOrderInfoPage(new Page<OrderInfo>(pageNum, pageSize), id, uid1, status, orderTimeFrom, orderTimeTo);
            return orderInfoPage1;
        }

    }

    /**
     * 获取订单
     * @param id
     * @return
     */
    public OrderInfo getOrderById(Integer id) {
        return orderInfoService.getById(id);
    }

    /**
     * 获取订单详细信息
     * @param id
     * @return
     */
    public OrderInfoDetailDto getOrderDetailById(Integer id) {
        OrderInfoDetailDto detailDto = new OrderInfoDetailDto();
        OrderInfo orderInfo = this.getOrderById(id);
        detailDto.setUserName(userInfoService.getById(orderInfo.getUid()).getUserName());
        detailDto.setOrderTime(orderInfo.getOrdertime());
        detailDto.setStatus(orderInfo.getStatus());
        //查询订单详细
        OrderDetail orderDetail = orderDetailService.getOne(new QueryWrapper<OrderDetail>().eq("oid", orderInfo.getId()));
        detailDto.setNum(orderDetail.getNum());
        String pName = productInfoService.getById(orderDetail.getPid()).getName();
        detailDto.setProductName(pName);
        double price = productInfoService.getById(orderDetail.getPid()).getPrice();
        detailDto.setPrice(price);
        detailDto.setTotal(orderDetail.getNum() * price);
        detailDto.setTotalMoney(orderDetail.getNum() * price);
        return detailDto;
    }

    /**
     * 根据id删除订单
     * @param ids
     */
    @Transactional
    public void deleteOrder(String ids) {
        List<String> stringList = new ArrayList<>();
        String[] idStr = ids.split(",");
        Collections.addAll(stringList, idStr);
        List<OrderDetail> orderDetails = orderDetailService.list(new QueryWrapper<OrderDetail>().in("oid", stringList));
        if (orderDetails.size() > 0) {
            orderDetailMapper.deleteOrderDetail(stringList);
        }
        List<OrderInfo> orders = orderInfoService.listByIds(stringList);
        if (orders.size() > 0) {
            orderInfoMapper.deleteOrder(stringList);
        }
    }
}
