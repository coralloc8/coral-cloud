package com.coral.web.websocket.webservice;


import com.coral.web.websocket.dto.UrlRequestDTO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author huss
 * @version 1.0
 * @className ZhyxToolWebService
 * @description 工具ws
 * @date 2022/10/28 13:44
 */
@WebService(
        name = "ZhyxToolWebService",
        targetNamespace = "http://webservice.websocket.web.coral.com/"//命名空间,一般是接口的包名倒序
)
public interface ZhyxToolWebService {

    /**
     * @param request {@link UrlRequestDTO}
     * @return
     */
    @WebMethod
    String findUrl(@WebParam(name = "request") String request);
}
