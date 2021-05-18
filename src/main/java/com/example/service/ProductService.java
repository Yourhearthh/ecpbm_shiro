package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dao.mapper.OrderDetailMapper;
import com.example.dao.mapper.ProductInfoMapper;
import com.example.dao.mapper.TypeMapper;
import com.example.dao.service.ProductInfoServiceImpl;
import com.example.pojo.ProductInfo;
import com.example.pojo.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 13:54
 * @version: 1.0
 */
@Service
public class ProductService {
    @Autowired
    ProductInfoMapper productInfoMapper;
    @Autowired
    ProductInfoServiceImpl productInfoService;
    @Autowired
    TypeMapper typeMapper;
    @Autowired
    OrderDetailMapper orderDetailMapper;

    /**
     * 获取商品分页列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @param code
     * @param type
     * @param brand
     * @param priceFrom
     * @param priceTo
     * @return
     */
    public Page<ProductInfo> getProductPage(Integer pageNum, Integer pageSize, String name, String code, String type, String brand, String priceFrom, String priceTo) {
        LambdaQueryWrapper<ProductInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null) {
            queryWrapper.like(ProductInfo::getName, name + "%");
        }
        if (code != null) {
            queryWrapper.eq(ProductInfo::getCode, code);
        }
        if (type != null) {
            Type type1 = typeMapper.selectOne(new QueryWrapper<Type>().eq("name", type));
            queryWrapper.eq(ProductInfo::getTid, type1.getId());
        }
        if (brand != null) {
            queryWrapper.like(ProductInfo::getBrand, brand + "%");
        }
        if (priceFrom != null) {
            queryWrapper.gt(ProductInfo::getPrice, priceFrom);
        }
        if (priceFrom != null) {
            queryWrapper.lt(ProductInfo::getPrice, priceTo);
        }
        Page<ProductInfo> infoPage = productInfoService.page(new Page<ProductInfo>(pageNum, pageSize), queryWrapper);
        return infoPage;
    }

    /**
     * 商品下架
     * @param ids
     * @param flag
     */
    public void deleteProduct(String ids, int flag) {
        List<String> list = new ArrayList<>();
        String[] str = ids.split(",");
        Collections.addAll(list, str);
        productInfoMapper.deleteProduct(list, flag);
    }

    /**
     * 根据商品id获取商品信息
     * @param id
     * @return
     */
    public ProductInfo getProductById(Integer id) {
        return productInfoMapper.selectById(id);
    }

    /**
     * 添加商品
     * @param productInfo
     * @return
     */
    @Transactional
    public void addProduct(ProductInfo productInfo, MultipartFile file, HttpServletRequest request) {
        // 服务器端upload文件夹物理路径
        String path = request.getSession().getServletContext().getRealPath("product_images");
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 实例化一个File对象，表示目标文件（含物理路径）
        assert fileName != null;
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 将上传文件写到服务器上指定的文件
        try {
            file.transferTo(targetFile);
            productInfo.setPic(fileName);
            productInfoMapper.addProductInfo(productInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取在售商品
     * @param status
     * @return
     */
    public List<ProductInfo> getSaleOnProduct(Integer status) {
        List<ProductInfo> productInfoList = productInfoService.list(new QueryWrapper<ProductInfo>().eq("status", status));
        return productInfoList;
    }

    /**
     * 根据id删除该商品
     * @param ids
     */
    @Transactional
    public void deleteProductById(String ids) {
        List<String> list = new ArrayList<>();
        String[] str = ids.split(",");
        Collections.addAll(list, str);
//        List<OrderDetail> details = orderDetailMapper.selectList(new QueryWrapper<OrderDetail>().in("pid", list));
        productInfoMapper.deleteProductById(list);
    }

//    /**
//     * 获取导出的商品集合
//     * @return
//     */
//    public List<ProductExportVo> getProductExportVo() {
//        List<ProductExportVo> voList = new ArrayList<>();
//        List<ProductInfo> productInfoList = productInfoService.list();
//        for (ProductInfo productInfo : productInfoList) {
//            ProductExportVo exportVo = convertInfoToVo(productInfo);
//            voList.add(exportVo);
//        }
//        return voList;
//    }
//
//    /**
//     * 转换实体类为导出的VO
//     * @param productInfo
//     * @return
//     */
//    private ProductExportVo convertInfoToVo(ProductInfo productInfo) {
//        ProductExportVo exportVo = new ProductExportVo();
//        try {
//            Type type = typeMapper.selectById(productInfo.getTid());
//            exportVo.setType(type.getName());
//            if (productInfo.getStatus() == 1) {
//                exportVo.setStatus("在售");
//            } else if (productInfo.getStatus() == 0) {
//                exportVo.setStatus("下架");
//            }
//            BeanUtils.copyProperties(productInfo, exportVo);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return exportVo;
//    }
}
