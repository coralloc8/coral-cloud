package com.coral.base.common.design.asyncmethod;

import java.util.Optional;

/**
 * @author huss
 * @version 1.0
 * @className AsyncCallback
 * @description todo
 * @date 2022/7/29 17:13
 */
public interface AsyncCallback<T> {
    void onComplete(T value, Optional<Exception> ex);
}
