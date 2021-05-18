package com.example.controller;

import com.example.dao.service.TypeServiceImpl;
import com.example.pojo.BaseResponse;
import com.example.pojo.Type;
import com.example.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 13:42
 * @version: 1.0
 */
@RestController
@RequestMapping("/type")
@Api(tags = "商品类型")
public class TypeController {
    @Autowired
    private TypeServiceImpl typeService;

    @GetMapping("/getType/{flag}")
    public List<Type> getType(@PathVariable("flag") Integer flag) {
        List<Type> typeList = typeService.list();
        if (flag == 1) {
            Type t = new Type();
            t.setId(0);
            t.setName("请选择...");
            typeList.add(0, t);
        }
        return typeList;
    }

    @ApiOperation("获取全部商品类型")
    @GetMapping("/getTypes")
    public BaseResponse<List<Type>> getTypes() {
        List<Type> typeList = typeService.list();
        return BaseResponse.success(typeList);
    }

    @ApiOperation("添加商品类型")
    @PostMapping("/addType")
    public BaseResponse<Type> addType(@RequestBody Type type) {
        int t = typeService.addType(type);
        if (t > 0) {
            return BaseResponse.success(ResultCode.SUCCESS);
        } else {
            return BaseResponse.success(ResultCode.FAILED);
        }
    }
}
