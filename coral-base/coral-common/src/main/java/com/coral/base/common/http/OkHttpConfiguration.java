package com.coral.base.common.http;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @description: okhttp 配置
 * @author: huss
 * @time: 2021/3/1 17:50
 */
@Slf4j
public class OkHttpConfiguration {

    public OkHttpClient.Builder okHttpClientBuilder() {
        return new OkHttpClient.Builder()
            // 失败重试
            .retryOnConnectionFailure(true)
            //
            .connectionPool(pool())
            //
            .connectTimeout(15, TimeUnit.SECONDS)
            //
            .readTimeout(30, TimeUnit.SECONDS)
            //
            .writeTimeout(30, TimeUnit.SECONDS);
    }

    public HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> {
            log.info("OKHttp-----{}", message);
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    public X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    public SSLSocketFactory sslSocketFactory() {
        try {
            // 信任任何链接
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] {x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a new connection pool with tuning parameters appropriate for a single-user application. The tuning
     * parameters in this pool are subject to change in future OkHttp releases. Currently
     */
    public ConnectionPool pool() {
        return new ConnectionPool(200, 5, TimeUnit.MINUTES);
    }
}
