package com.coral.base.common.protocol;


import com.coral.base.common.enums.ProtocolType;
import com.coral.base.common.exception.Exceptions;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

/**
 * @author huss
 * @version 1.0
 * @className SoapProtocolHandler
 * @description soap实现
 * @date 2021/4/27 9:27
 */
@Slf4j
public class SoapProtocolHandler implements IProtocolHandler<SoapProtocolHandler.SoapProParam> {

    @Override
    public ProtocolType getProtocol() {
        return ProtocolType.SOAP;
    }

    @Override
    public String send(SoapProParam soapProParam) throws RuntimeException {
        log.info("开始请求WS：参数-->{}", soapProParam);
        try {
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(soapProParam.getPushUrl());
            call.setOperationName(new QName(soapProParam.getNamespaceURI(), soapProParam.getLocalPart()));
            call.setUseSOAPAction(true);
            //接口的参数
            call.addParameter(soapProParam.getParamName(), XMLType.XSD_STRING, ParameterMode.IN);
            //设置返回类型
            call.setReturnType(XMLType.XSD_STRING);

            String result = (String) call.invoke(new Object[]{soapProParam.getParams()});
            log.info("获取请求WS返回：{}", result);
            return result;
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }


    @Data
    @Builder
    public static class SoapProParam {
        /**
         * webservice地址
         */
        private String pushUrl;
        /**
         * 参数名称
         */
        private String paramName;
        /**
         * 入参 xml格式
         */
        private String params;
        /**
         * 工作空间
         */
        private String namespaceURI;
        /**
         * 操作名称
         */
        private String localPart;
    }


}
