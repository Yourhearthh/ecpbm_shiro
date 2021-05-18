package com.example.controller;

import com.example.dto.OrderInfoDetailDto;
import com.example.pojo.BaseResponse;
import com.example.pojo.OrderInfo;
import com.example.pojo.PageResponse;
import com.example.pojo.ProductInfo;
import com.example.service.OrderService;
import com.example.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 13:58
 * @version: 1.0
 */
@RestController
@RequestMapping("/orderInfo")
@Api(tags = "订单")
public class OrderInfoController {
    @Autowired
    OrderService orderService;

    @ApiOperation("分页查询订单")
    @GetMapping("/getOrderPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "订单编号", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "订单人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "订单状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderTimeFrom", value = "大于订单创建时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderTimeTo", value = "小于订单创建时间", dataType = "String", paramType = "query")
    })
    public PageResponse<List<OrderInfo>> getOrderPage(
            @RequestParam(value = "pageNum",required = false) Integer pageNum,
            @RequestParam(value = "pageSize",required = false) Integer pageSize,
            @RequestParam(value = "id",required = false) String id,
            @RequestParam(value = "userName",required = false) String userName,
            @RequestParam(value = "status",required = false) String status,
            @RequestParam(value = "orderTimeFrom",required = false) String orderTimeFrom,
            @RequestParam(value = "orderTimeTo",required = false) String orderTimeTo
    ) {
        return PageResponse.success(orderService.getOrderPage(pageNum, pageSize, id, userName, status, orderTimeFrom, orderTimeTo));
    }

    @ApiOperation("根据订单id获取订单信息")
    @GetMapping("/getOrderById")
    @ApiImplicitParam(name = "Id", value = "订单id", dataType = "int", paramType = "query")
    public BaseResponse<ProductInfo> getOrderById(Integer Id) {
        return BaseResponse.success(orderService.getOrderById(Id));
    }

    @ApiOperation("根据订单id获取订单详细信息")
    @GetMapping("/getOrderDetailById")
    @ApiImplicitParam(name = "Id", value = "订单id", dataType = "int", paramType = "query")
    public BaseResponse<OrderInfoDetailDto> getOrderDetailById(Integer Id) {
        return BaseResponse.success(orderService.getOrderDetailById(Id));
    }

    @ApiOperation("根据订单id删除订单")
    @GetMapping("/deleteOrder")
    @ApiImplicitParam(name = "Ids", value = "订单id,可传多个（用，号分隔开）", dataType = "String", paramType = "query")
    public BaseResponse deleteOrder(String Ids) {
        try {
            orderService.deleteOrder(Ids);
            return BaseResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.success(ResultCode.FAILED);
        }
    }
}
