package com.fxytb.malltiny.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * jwt枚举
 */
@Getter
@AllArgsConstructor
public enum JwtEnum {

    /**
     * claim用户名属性
     */
    CLAIM_KEY_USERNAME("sub"),
    /**
     * claim创建时间属性
     */
    CLAIM_KEY_CREATED("created")

    ;
    /**
     * 属性
     */
    private final String attribute;

}
