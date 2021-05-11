package com.coral.simple.web1.service;

import java.io.Serializable;

/**
 * @description: 号码制造器
 * @author: huss
 * @time: 2020/10/24 15:02
 */
public interface NumCreatorService<T extends Serializable> {
    /**
     * 制造号码
     * 
     * @return
     */
    T create();
}
