package com.coral.web.websocket.webservice;

import com.coral.base.common.EnumUtil;
import com.coral.base.common.XmlUtil;
import com.coral.web.core.response.Result;
import com.coral.web.core.response.Results;
import com.coral.web.websocket.common.enums.BusinessType;
import com.coral.web.websocket.dto.UrlRequestDTO;
import com.coral.web.websocket.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.Objects;

/**
 * @author huss
 * @version 1.0
 * @className ZhyxToolWebServiceImpl
 * @description 工具ws
 * @date 2022/10/28 13:44
 */
@Slf4j
@Component
@WebService(
        serviceName = "ZhyxToolWebService",
        endpointInterface = "com.coral.web.websocket.webservice.ZhyxToolWebService",
        targetNamespace = "http://webservice.websocket.web.coral.com/"//命名空间,一般是接口的包名倒序
)
public class ZhyxToolWebServiceImpl implements ZhyxToolWebService {

    @Autowired
    private ReplyService replyService;

    @Override
    public String findUrl(String request) {
        log.info(">>>>>request:{}", request);
        UrlRequestDTO requestDTO = XmlUtil.parseXmlWithoutRootNode(request, UrlRequestDTO.class, true).get(0);
        log.info(">>>>>requestDTO:{}", requestDTO);
        BusinessType type = null;
        try {
            type = EnumUtil.codeOf(BusinessType.class, requestDTO.getBusinessType());
        } catch (Exception e) {
            log.error(">>>>>Error", e);
        }
        if (Objects.isNull(type)) {
            return buildResponse(new Results().failure());
        }

        return buildResponse(replyService.findUrl(type, requestDTO.getFunction(), requestDTO.getParams()));
    }

    private String buildResponse(Result result) {
        String response = XmlUtil.createXml(result, "response", true, false);
        log.info(">>>>response:{}", response);
//        response = XmlUtil.escapeText(response);
//        log.info(">>>>转移后的response:{}", response);
        return response;
    }

}
