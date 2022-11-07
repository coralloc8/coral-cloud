package com.coral.web.websocket;

import com.coral.base.common.XmlUtil;
import com.coral.base.common.json.JsonUtil;
import com.coral.base.common.protocol.IProtocolHandler;
import com.coral.base.common.protocol.SoapProtocolHandler;
import com.coral.web.core.response.Result;
import com.coral.web.websocket.dto.UrlInfoDTO;
import com.coral.web.websocket.dto.UrlRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className WebServiceTest
 * @description webservice 测试
 * @date 2022/11/4 17:18
 */
public class WebServiceTest {

    private static final String URL = "http://localhost:8083/websocket/services/ZhyxToolWebService?wsdl";

    @Test
    @DisplayName("web service 测试")
    public void test() {
        UrlRequestDTO request = new UrlRequestDTO();
        request.setAge(30);
        request.setChildren(Arrays.asList(
                new UrlRequestDTO.UrlSonRequestDTO("01_name", 1L),
                new UrlRequestDTO.UrlSonRequestDTO("02_name", 2L)
        ));
        request.setFunction("2eeee2");
        request.setBusinessType("ywz");
        request.setIsMale(true);
        request.setMoney(54444.50);
        request.setParams("433223");
        request.setSon(new UrlRequestDTO.UrlSonRequestDTO("03_name", 3L));

        Map<String, String> map = new HashMap<>();
        map.put("11", "11_val");
        map.put("22", "22_val");
        System.out.println("request:" + request);


        String xml = XmlUtil.createXmlWithoutRootNode(request, "request");
        System.out.println("xml:" + xml);

        IProtocolHandler<SoapProtocolHandler.SoapProParam> handler = new SoapProtocolHandler();
        SoapProtocolHandler.SoapProParam soapProParam = SoapProtocolHandler.SoapProParam.builder()
                .pushUrl(URL)
                .paramName("request")
                .params(xml)
                .namespaceURI("http://webservice.websocket.web.coral.com/")
                .localPart("findUrl")
                .build();
        String result = handler.send(soapProParam);

        System.out.println(">>>>>>结果:\n" + result);

        Result result1 = XmlUtil.parseXmlWithoutRootNode(result, Result.class).get(0);

        if (result1.isSuccess()) {
            UrlInfoDTO urlInfoDTO = JsonUtil.toPojo((Map) result1.getData(), UrlInfoDTO.class);
            System.out.println("re:" + urlInfoDTO.getUrl());
        }


    }
}
