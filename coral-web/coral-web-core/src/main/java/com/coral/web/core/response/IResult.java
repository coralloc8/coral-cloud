package com.coral.web.core.response;

import com.coral.base.common.exception.IErrorMessage;

/**
 * @author huss
 */
public interface IResult {

    /**
     * 获取成功信息
     * 
     * @return
     */
    Result success();

    /**
     * 获取成功信息
     * 
     * @param data
     * @return
     */
    Result success(Object data);

    /**
     * 获取失败信息
     * 
     * @param message
     * @return
     */
    Result failure(IErrorMessage message);

    /**
     * 获取失败信息
     * 
     * @return
     */
    Result failure();

}
