package com.fxytb.malltiny.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageEnum {
    /**
     * 成功
     */
    SUCCESS("success", true),
    /**
     * 警告
     */
    WARNING("warning", true),
    /**
     * 异常
     */
    ERROR("error", false),
    /**
     * 信息
     */
    INFO("info", true);

    /**
     * 类型
     */
    private final String type;

    private final Boolean isSuccessful;
}
