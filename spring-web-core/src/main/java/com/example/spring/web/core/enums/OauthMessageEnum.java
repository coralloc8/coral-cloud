package com.example.spring.web.core.enums;

import com.example.spring.common.exception.IErrorCodeMessage;
import com.example.spring.web.core.util.I18nMessageUtil;

/**
 * oauth2 错误列表
 * 
 * @author huss
 */

public enum OauthMessageEnum implements IErrorCodeMessage<OauthMessageEnum>, I18nMessageKey {

    /**
     * access_token无效
     */
    ACCESS_TOKEN_INVALID(40001, "invalid access token"),
    /**
     * 资源未授权
     */
    UNAUTHORIZED(40002, "unauthorized"),

    /**
     * 访问资源的用户权限不足
     */
    INSUFFICIENT_PERMISSIONS(40003, "insufficient permissions"),

    /**
     * 用户名或密码错误
     */
    INVALID_GRANT(40004, "wrong user name or password"),

    /**
     * 错误的客户端凭证
     */
    INVALID_CLIENT(40005, "bad client credentials"),
    /**
     * 无效的请求
     */
    INVALID_REQUEST(40006, "invalid request"),
    /**
     * 无效的token
     */
    INVALID_TOKEN(40007, "invalid token"),
    /**
     * 客户端未授权
     */
    UNAUTHORIZED_CLIENT(40008, "unauthorized client"),

    /**
     * 不支持的grant_type
     */
    UNSUPPORTED_GRANT_TYPE(40009, "unsupported grant type"),

    /**
     * http媒体类型不接受
     */
    HTTP_MEDIA_TYPE_NOT_ACCEPT(40010, "http media type not accept"),

    /**
     * user not exist
     */
    USER_NOT_EXIST(50001, "user not exist"),

    ;

    private Integer code;
    private String message;

    OauthMessageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getErrorMsg() {
        return this.message;
    }

    @Override
    public String getMessageKey() {
        return I18nMessageUtil.getMessageKey(this);
    }

    @Override
    public Integer getErrorCode() {
        return this.code;
    }
}