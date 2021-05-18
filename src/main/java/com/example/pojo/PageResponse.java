package com.example.pojo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.utils.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 13:57
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@ApiModel("分页查询结果Data")
public class PageResponse <T> extends BaseResponse<T> {
    /**
     * 日志类
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PageResponse.class);

    // 常用result 预生成
    // 请求通过成功
    public static final PageResponse SUCCESS = new PageResponse(ResultCode.SUCCESS);
    // 拒绝请求（请求未通过）
    public static final PageResponse FORBIDDEN = new PageResponse(ResultCode.FORBIDDEN);
    // header 没有携带验证信息
    public static final PageResponse UN_AUTHORIZED =
            new PageResponse(ResultCode.UN_AUTHORIZED);
    // 服务器错误
    public static final PageResponse SERVER_ERROR =
            new PageResponse(ResultCode.SERVER_ERROR);
    // 未找到指定页面
    public static final PageResponse NOT_FOUND_PATH = new PageResponse(ResultCode.NOT_FOUND_PATH);
    //未找到指定资源
    public static final PageResponse NOT_FOUND_RESOURCES = new PageResponse(ResultCode.NOT_FOUND_RESOURCES);
    // 请求参数错误
    public static final PageResponse CLIENT_PARAM_ERROR =
            new PageResponse(ResultCode.CLIENT_PARAM_ERROR);


    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    protected long total;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    protected long totalPages;

    public PageResponse(ResultCode resultCode) { //自定义message
        super(resultCode);
    }

    public PageResponse(int code, String message) {
        super(code, message);
    }

    public PageResponse(ResultCode resultCode, T data, long total, long totalPages) {
        super(resultCode, data);
        this.total = total;
        this.totalPages = totalPages;
    }

    public static <E> PageResponse success(E data, long total, long totalPages) {
        return new PageResponse(ResultCode.SUCCESS, data, total, totalPages);
    }

    public static <E> PageResponse success(IPage<E> res) {
        return new PageResponse(ResultCode.SUCCESS, res.getRecords(), res.getTotal(), res.getPages());
    }

    public static PageResponse errorWithException(ResultCode resultCode, Exception ex) {
        LOGGER.error("response Exception", ex);
        return new PageResponse<>(resultCode.code(), ex.getMessage());
    }
}
