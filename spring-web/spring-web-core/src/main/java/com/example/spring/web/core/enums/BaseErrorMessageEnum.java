package com.example.spring.web.core.enums;

import com.example.spring.common.I18nMessageUtil;
import com.example.spring.common.enums.I18nMessageKey;
import com.example.spring.common.exception.IErrorCodeMessage;

/**
 * 基础错误信息，默认全英文提示
 * 
 * @author huss
 */
public enum BaseErrorMessageEnum implements IErrorCodeMessage<BaseErrorMessageEnum>, I18nMessageKey {

    /**
     * success
     */
    SUCCESS(0, "success"),

    /**
     * 系统异常，请联系管理员
     */
    FAILURE(1, "the system is abnormal, please contact the administrator"),
    /**
     * 系统繁忙，请稍后重试
     */
    SYSTEM_BUSY(-1, "system is busy, please try again later"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR(10001, "system error"),
    /**
     * 不合法的参数
     */
    ILLEGAL_PARAMETER(10002, "illegal parameter"),
    /**
     * 不合法的请求格式
     */
    ILLEGAL_REQUEST_FORMAT(10003, "illegal request format"),

    /**
     * 日期格式错误
     */
    DATE_FORMAT_ERROR(10004, "date format error"),

    /**
     * 时间区间不合法
     */
    ILLEGAL_TIME_INTERVAL(10005, "illegal time interval"),

    /**
     * 上传文件缺失
     */
    UPLOAD_FILE_MISSING(10006, "upload file is missing"),

    /**
     * 部分必填参数为空
     */
    REQUIRED_PARAMETERS_EMPTY(10007, "some required parameters are empty"),

    /**
     * 参数校验错误
     */
    PARAMETER_VERIFICATION_ERROR(10008, "parameter verification error"),

    /**
     * 不支持此操作
     */
    OPERATION_NOT_SUPPORT(10009, "this operation is not supported"),

    ;

    private Integer code;

    private String message;

    BaseErrorMessageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.message;
    }

    @Override
    public String getMessageKey() {
        return I18nMessageUtil.getMessageKey(this);
    }

}
