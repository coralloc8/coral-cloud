package com.coral.web.core.web;

import com.coral.base.common.exception.IErrorMessage;
import com.coral.web.core.response.Result;
import com.coral.web.core.response.Results;

/**
 * @description: 基础controller
 * @author: huss
 * @time: 2020/7/14 18:25
 */
public class BaseController {

    protected Result result(boolean success) {
        return this.result(success, null);
    }

    protected Result result(boolean success, Object data) {
        if (success) {
            return new Results().success(data);
        } else {
            if (data instanceof IErrorMessage) {
                return new Results().failure((IErrorMessage)data);
            }
            return new Results().failure();
        }
    }

}
