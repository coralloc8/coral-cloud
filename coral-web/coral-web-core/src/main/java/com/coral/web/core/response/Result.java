package com.coral.web.core.response;

import com.coral.base.common.exception.BaseErrorMessageEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;

/**
 * @author huss
 */
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Result<T> implements Serializable {

    /**
     * 返回代码
     */
    @Setter(AccessLevel.PACKAGE)
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回对象
     */
    @Setter(AccessLevel.PACKAGE)
    private T data;

    @JsonIgnore
    public boolean isSuccess() {
        return code.equals(BaseErrorMessageEnum.SUCCESS.getErrorCode());
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
