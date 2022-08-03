package com.coral.base.common.design.apigateway;

/**
 * @author huss
 * @version 1.0
 * @className PriceClientImpl
 * @description 价格实现类
 * @date 2022/7/28 14:22
 */
public class PriceClientImpl implements PriceClient {
    @Override
    public String getPrice() {
        return "100";
    }
}
