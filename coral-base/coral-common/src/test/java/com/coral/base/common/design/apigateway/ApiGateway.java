package com.coral.base.common.design.apigateway;

/**
 * @author huss
 * @version 1.0
 * @className ApiGateway
 * @description 网关
 * @date 2022/7/28 13:58
 */
public class ApiGateway {


    public static void main(String[] args) {
        gateway();
    }

    public static void gateway() {
        PriceClient priceClient = new PriceClientImpl();
        System.out.println("价格为:" + priceClient.getPrice());
    }
}
