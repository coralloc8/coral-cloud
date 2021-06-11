package com.coral.base.common.protocol;


import com.coral.base.common.enums.ProtocolType;

/**
 * @author huss
 * @version 1.0
 * @className IProtocolHandler
 * @description 应用层协议
 * @date 2021/4/27 9:25
 */
public interface IProtocolHandler<R> {
    /**
     * 获取协议
     *
     * @return
     */
    ProtocolType getProtocol();

    /**
     * 发送
     *
     * @param r
     * @return
     * @throws RuntimeException
     */
    String send(R r) throws RuntimeException;
}
