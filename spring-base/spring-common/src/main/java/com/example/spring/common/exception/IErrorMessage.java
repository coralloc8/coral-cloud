package com.example.spring.common.exception;

/**
 * 只获取错误信息，不用关注错误码
 * 
 * @author huss
 */
public interface IErrorMessage<E extends Enum<E>>  {

    /**
     * 获取错误信息
     * 
     * @return
     */
    String getErrorMsg();

}
