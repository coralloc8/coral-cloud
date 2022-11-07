package com.coral.web.websocket.config;

import com.coral.base.common.IOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huss
 * @version 1.0
 * @className RequestParamInterceptor
 * @description 入参拦截
 * @date 2022/11/7 10:39
 */

@Slf4j
public class RequestParamInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private final static String PATTERN = "</?request.*?>";

    public RequestParamInterceptor(String phase) {
        super(phase);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        String xml = "";
        try (InputStream inputStream = message.getContent(InputStream.class)) {
            xml = IOUtil.readStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.debug(">>>>>原始入参为:\n{}", xml);
        xml = encodeParams(xml);
        log.debug(">>>>>入参编码后为:\n{}", xml);
        message.setContent(InputStream.class, new ByteArrayInputStream(xml.getBytes()));
    }

    private String encodeParams(String xml) {
        Pattern r = Pattern.compile(PATTERN);
        Matcher m = r.matcher(xml);
        int start = 0, end = xml.length();
        if (m.find()) {
            start = m.end();
        }
        if (m.find()) {
            end = m.start();
        }

        if (start == 0) {
            return xml;
        }

        String firstStr = xml.substring(0, start);

        String params = xml.substring(start, end);
        params = params.replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");

        String endStr = xml.substring(end);

        return String.join("", firstStr, params, endStr);
    }

}
