package com.fxytb.malltiny.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestStatusEnum {
    /**
     * 权限不足
     */
    FORBIDDEN(403, "账号权限不足,请求被拒绝"),
    /**
     * 身份认证失败
     */
    NO_AUTH(401, "账号未登录,请求被拒绝");
    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态信息文本
     */

    private final String message;
}
