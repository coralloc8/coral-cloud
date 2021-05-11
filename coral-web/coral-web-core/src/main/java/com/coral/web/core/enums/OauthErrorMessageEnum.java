package com.coral.web.core.enums;

import com.coral.base.common.enums.I18nMessageKey;
import com.coral.base.common.exception.IErrorCodeMessage;

/**
 * oauth2 错误列表
 * 
 * @author huss
 */

public enum OauthErrorMessageEnum implements IErrorCodeMessage<OauthErrorMessageEnum>, I18nMessageKey {

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
     * client_id或者client_secret有误
     */
    BAD_CREDENTIALS(40011, "bad credentials"),

    ;

    private Integer code;
    private String message;

    OauthErrorMessageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getErrorMsg() {
        return this.message;
    }

    @Override
    public Integer getErrorCode() {
        return this.code;
    }

}