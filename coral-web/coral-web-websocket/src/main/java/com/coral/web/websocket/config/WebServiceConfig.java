package com.coral.web.websocket.config;

import com.coral.web.websocket.webservice.ZhyxToolWebService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;


/**
 * @author huss
 * @version 1.0
 * @className WebServiceConfig
 * @description WebService 配置
 * @date 2022/11/4 9:45
 */
@Configuration
public class WebServiceConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private ZhyxToolWebService zhyxToolWebService;

    @Bean
    public ServletRegistrationBean dispatcherServletAdr() {
        return new ServletRegistrationBean(new CXFServlet(), "/services/*");
    }


    @Bean
    public Endpoint zhyxToolWebService() {
        EndpointImpl endpoint = new EndpointImpl(bus, zhyxToolWebService);
        //Web服务名称
        endpoint.publish("/ZhyxToolWebService");
        endpoint.getInInterceptors().add(new RequestParamInterceptor(Phase.RECEIVE));
        return endpoint;
    }


}
