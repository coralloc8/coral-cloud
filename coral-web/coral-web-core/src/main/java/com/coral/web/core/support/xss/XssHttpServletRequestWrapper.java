package com.coral.web.core.support.xss;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.coral.base.common.IOUtil;
import com.coral.base.common.StringEscapeUtils;

/**
 * @author huss
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    /**
     * html过滤
     */
    // private static final HTMLFilter HTML_FILTER = new HTMLFilter();

    public XssHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = IOUtil.readByteArray(request.getInputStream(), true);
    }

    @Override
    public String getHeader(String name) {
        return StringEscapeUtils.escapeHtml4(super.getHeader(name));
    }

    @Override
    public String getQueryString() {
        return StringEscapeUtils.escapeHtml4(super.getQueryString());
    }

    @Override
    public String getParameter(String name) {
        return StringEscapeUtils.escapeHtml4(super.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapseValues[i] = StringEscapeUtils.escapeHtml4(values[i]);
            }
            return escapseValues;
        }
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = StringEscapeUtils.escapeHtml4(values[i]);
            }
            map.put(key, values);
        }
        return map;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new MyServletInputStream(new ByteArrayInputStream(body));

    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    // private String xssEncode(String input) {
    // String str = HTML_FILTER.filter(input);
    // return StringEscapeUtils.escapeHtml4(str);
    // }

    static class MyServletInputStream extends ServletInputStream {

        private ByteArrayInputStream byteArrayInputStream;

        public MyServletInputStream(ByteArrayInputStream byteArrayInputStream) {
            super();
            this.byteArrayInputStream = byteArrayInputStream;
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public int read() throws IOException {
            return byteArrayInputStream.read();
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

    }

}