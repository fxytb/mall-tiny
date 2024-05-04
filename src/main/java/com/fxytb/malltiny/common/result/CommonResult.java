package com.fxytb.malltiny.common.result;

import cn.hutool.core.util.ArrayUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "方法公共返回类", description = "方法公共返回类")
public class CommonResult<T> {

    @ApiModelProperty(value = "是否成功标识 true成功 false失败")
    private Boolean successful;

    @ApiModelProperty(value = "信息类型 info successful warning error")
    private String messageType;

    @ApiModelProperty(value = "返回信息")
    private String message;

    @ApiModelProperty(value = "状态码 100 普通 200 成功 300 失败 400 错误")
    private Integer code;

    @ApiModelProperty(value = "返回值")
    private T data;

    public static <T> CommonResult<T> success(String message, T... data) {
        return CommonResult.<T>builder()
                .successful(true)
                .message(message)
                .data(ArrayUtil.isNotEmpty(data) ? data[0] : null)
                .code(200)
                .messageType("success")
                .build();
    }

    public static <T> CommonResult<T> info(String message) {
        return CommonResult.<T>builder()
                .successful(true)
                .message(message)
                .data(null)
                .code(100)
                .messageType("info")
                .build();
    }

    public static <T> CommonResult<T> warn(String message) {
        return CommonResult.<T>builder()
                .successful(true)
                .message(message)
                .data(null)
                .code(300)
                .messageType("warning")
                .build();
    }

    public static <T> CommonResult<T> error(String message) {
        return CommonResult.<T>builder()
                .successful(false)
                .message(message)
                .data(null)
                .code(400)
                .messageType("error")
                .build();
    }


}
