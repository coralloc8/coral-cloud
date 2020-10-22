package com.example.spring.web.core.support;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.example.spring.common.IOUtil;

/**
 * post body中的参数只能读取一次
 *
 * @author huss
 * @email huss@bangmart.cc
 * @date 2019年10月30日下午2:46:28
 */
public class ServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public ServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = IOUtil.readByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new MyServletInputStream(new ByteArrayInputStream(body));

    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

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