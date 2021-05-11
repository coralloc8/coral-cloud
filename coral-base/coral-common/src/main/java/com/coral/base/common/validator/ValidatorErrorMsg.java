package com.coral.base.common.validator;

import lombok.*;

/**
 * @description: 校验错误信息
 * @author: huss
 * @time: 2021/1/20 11:01
 */
@Data
public class ValidatorErrorMsg {

    /**
     * 属性
     */
    private String property;

    /**
     * 翻译key
     */
    private String messageKey;

    /**
     * 错误信息
     */
    private String message;

    public ValidatorErrorMsgView create() {
        return new ValidatorErrorMsgView(this.getProperty(), this.getMessage());
    }

    @AllArgsConstructor
    @ToString
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ValidatorErrorMsgView {
        /**
         * 属性
         */
        private String property;

        /**
         * 错误信息
         */
        private String message;
    }
}
