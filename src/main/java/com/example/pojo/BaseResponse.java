package com.example.pojo;

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
 * @date: 2021-04-21 13:45
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@ApiModel("查询结果Data")
public class BaseResponse<T> {
    /**
     * 日志类
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseResponse.class);

    // 常用result 预生成
    // 请求通过成功
    public static final BaseResponse SUCCESS = new BaseResponse(ResultCode.SUCCESS);
    // 拒绝请求（请求未通过）
    public static final BaseResponse FORBIDDEN = new BaseResponse(ResultCode.FORBIDDEN);
    // header 没有携带验证信息
    public static final BaseResponse UN_AUTHORIZED =
            new BaseResponse(ResultCode.UN_AUTHORIZED);
    // 服务器错误
    public static final BaseResponse SERVER_ERROR =
            new BaseResponse(ResultCode.SERVER_ERROR);
    // 未找到指定页面
    public static final BaseResponse NOT_FOUND_PATH = new BaseResponse(ResultCode.NOT_FOUND_PATH);
    //未找到指定资源
    public static final BaseResponse NOT_FOUND_RESOURCES = new BaseResponse(ResultCode.NOT_FOUND_RESOURCES);
    // 请求参数错误
    public static final BaseResponse CLIENT_PARAM_ERROR =
            new BaseResponse(ResultCode.CLIENT_PARAM_ERROR);
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    protected int code;

    /**
     * 响应信息
     */
    @ApiModelProperty(value = "响应信息")
    protected String message;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    protected T data;

    public BaseResponse(int code, String message) {//自定义message
        this.code = code;
        this.message = message;
    }

    public BaseResponse(int code, String message, T data) {//自定义message
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(ResultCode resultCode) {//固定message
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public BaseResponse(ResultCode resultCode, T data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    public static <E> BaseResponse success(E data) {
        return new BaseResponse(ResultCode.SUCCESS, data);
    }

    public static <E> BaseResponse failure(E data) {
        return new BaseResponse(ResultCode.LOGIN_FAILURE, data);
    }

    public static <E> BaseResponse noAuthority(E data) {
        return new BaseResponse(ResultCode.NO_AUTHORITY, data);
    }

    public static BaseResponse errorWithException(ResultCode resultCode, Exception ex) {
        LOGGER.error("response Exception", ex);
        return new BaseResponse<>(resultCode.code(), ex.getMessage());
    }
}
