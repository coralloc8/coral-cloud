package com.coral.test.opendoc.util.response;

import com.coral.base.common.exception.BaseErrorMessageEnum;
import com.coral.base.common.exception.IErrorCodeMessage;
import com.coral.base.common.exception.IErrorMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author huss
 * @version 1.0
 * @className Result
 * @description 结果集
 * @date 2021/9/14 13:14
 * <p>
 * 对于泛型不能给Schema中添加name属性
 * {@link "https://github.com/swagger-api/swagger-core/issues/3323"
 * "http://springfox.github.io/springfox/docs/snapshot/#changing-how-generic-types-are-named"}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "响应结果")
public class Result<T> {

    private final static Integer ERROR_CODE = BaseErrorMessageEnum.FAILURE.getErrorCode();
    private final static String ERROR_MSG = BaseErrorMessageEnum.FAILURE.getErrorMsg();
    private final static Integer SUCCESS_CODE = BaseErrorMessageEnum.SUCCESS.getErrorCode();


    /**
     * 返回代码
     */
    @Setter(AccessLevel.PACKAGE)
    @Getter
    @Schema(description = "响应码")
    private Integer code;

    /**
     * 返回消息
     */
    @Schema(description = "响应信息")
    @Getter
    private String message;


    @Schema(description = "响应数据")
    private T data;

    public T getData() {
        return data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return code.equals(SUCCESS_CODE);
    }


    public Result<T> failure(IErrorMessage message) {
        Integer errorCode = ERROR_CODE;
        String errorMsg = ERROR_MSG;
        if (message != null && message instanceof IErrorCodeMessage) {
            errorCode = ((IErrorCodeMessage) message).getErrorCode();
            errorMsg = message.getErrorMsg();
        }
        this.setCode(errorCode);
        this.setMessage(errorMsg);
        return this;
    }

    public Result<T> failure() {
        return failure(null);
    }

    public Result<T> success() {
        return success(null);
    }

    public Result<T> success(T data) {
        IErrorCodeMessage messageEnum = BaseErrorMessageEnum.SUCCESS;
        this.setCode(messageEnum.getErrorCode());
        this.setMessage(messageEnum.getErrorMsg());
        this.setData(data);
        return this;
    }
}
