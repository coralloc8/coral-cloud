package com.example.spring.web.core.response;

import java.io.Serializable;
import java.util.Collections;

import com.example.spring.web.core.enums.BaseErrorMessageEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

/**
 * @author huss
 */
@ToString @Getter @NoArgsConstructor(access = AccessLevel.PACKAGE) @AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Result implements Serializable {

    /**
     * 返回代码
     */
    @Setter(AccessLevel.PACKAGE) private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回对象
     */
    @Setter(AccessLevel.PACKAGE) private Object data;

    @JsonIgnore public boolean isSuccess() {
        return code.equals(BaseErrorMessageEnum.SUCCESS.getErrorCode());
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        if (this.data == null) {
            return Collections.emptyMap();
        }
        return data;
    }
}
