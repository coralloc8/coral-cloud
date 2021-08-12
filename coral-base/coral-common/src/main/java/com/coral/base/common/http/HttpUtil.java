package com.coral.base.common.http;

import com.coral.base.common.StringUtils;
import com.coral.base.common.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @description: http 工具类
 * @author: huss
 * @time: 2021/3/1 17:52
 */
@Slf4j
public class HttpUtil {

    private final static String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36";

    private final static String CONTENT_TYPE = "content-type";

    private final static String APPLICATION_FORM = "application/x-www-form-urlencoded; charset=";

    private final static String APPLICATION_JSON = "application/json; charset=";

    private final static String APPLICATION_XML = "application/xml; charset=";

    private final static int HTTP_STATUS_OK = 200;

    public static HttpUtil getInstance() {
        return HttpHolder.INSTANCE.getHttpUtil();
    }

    enum HttpHolder {

        INSTANCE;

        private HttpUtil httpUtil;

        HttpHolder() {
            this.httpUtil = new HttpUtil();
        }

        public HttpUtil getHttpUtil() {
            return httpUtil;
        }

    }

    private OkHttpClient getOkHttpClient() {
        return HttpClientHolder.INSTANCE.getOkHttpClient();
    }

    enum HttpClientHolder {

        INSTANCE;

        private OkHttpClient okHttpClient;

        HttpClientHolder() {

            OkHttpConfiguration okHttpConfiguration = new OkHttpConfiguration();

            OkHttpClient.Builder builder = okHttpConfiguration.okHttpClientBuilder();
            builder.addInterceptor(okHttpConfiguration.httpLoggingInterceptor())
                    //
                    .sslSocketFactory(okHttpConfiguration.sslSocketFactory(), okHttpConfiguration.x509TrustManager())
                    //
                    .hostnameVerifier((hostName, session) -> true).retryOnConnectionFailure(true).build();

            okHttpClient = builder.build();
        }

        public OkHttpClient getOkHttpClient() {
            return this.okHttpClient;
        }
    }

    /**
     * mock返回数据
     *
     * @param httpRequestInfo
     * @return
     */
    public HttpResponseInfo mock(HttpRequestInfo httpRequestInfo, String resultData) {
        log.info(">>>>> http sync mock request start, params:{}", httpRequestInfo);

        Request request = this.buildRequest(httpRequestInfo);
        long start = Instant.now().getEpochSecond();
        // 同步请求
        HttpResponseInfo httpResponseInfo = new HttpResponseInfo(HTTP_STATUS_OK, resultData);
        long end = Instant.now().getEpochSecond();
        log.info(">>>>>http sync mock request end，time consuming：{}", (end - start));
        return httpResponseInfo;
    }

    /**
     * 同步请求
     *
     * @param httpRequestInfo
     * @return
     */
    public HttpResponseInfo request(HttpRequestInfo httpRequestInfo) {
        log.info(">>>>> http sync request start, params:{}", httpRequestInfo);
        Request request = this.buildRequest(httpRequestInfo);
        long start = Instant.now().getEpochSecond();
        HttpResponseInfo httpResponseInfo = null;
        // 同步请求
        try {
            Response response = getOkHttpClient().newCall(request).execute();
            Integer status = response.code();
            return new HttpResponseInfo(status, response.body() != null ? response.body().string() : "");
        } catch (IOException e) {
            log.error(">>>>>http sync request error:", e);
            httpResponseInfo = new HttpResponseInfo(e);
        }

        long end = Instant.now().getEpochSecond();
        log.info(">>>>>http sync request end，time consuming：{}", (end - start));
        return httpResponseInfo;

    }

    /**
     * 异步请求
     *
     * @param httpRequestInfo
     * @param callBack
     */
    public void request(HttpRequestInfo httpRequestInfo, ICallBack callBack) {
        log.info(">>>>> http async request start, params:{}, callBack:{}", httpRequestInfo, callBack);
        final long start = Instant.now().getEpochSecond();
        Request request = this.buildRequest(httpRequestInfo);
        // 异步请求
        getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.error(">>>>>http request async error:", e);
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    long end = Instant.now().getEpochSecond();
                    log.info(">>>>>http async request end，time consuming：{}", (end - start));
                    Integer status = response.code();
                    HttpResponseInfo httpResponseInfo =
                            new HttpResponseInfo(status, response.body() != null ? response.body().string() : "");
                    callBack.onSuccessful(httpResponseInfo);
                } catch (IOException e) {
                    log.error(">>>>>onResponse error:", e);
                    callBack.onSuccessful(new HttpResponseInfo(e));
                }
            }
        });
    }

    private Request buildRequest(HttpRequestInfo httpRequestInfo) {

        String url = httpRequestInfo.getUrl();
        if (StringUtils.isBlank(url)) {
            log.error(">>>>>Request url is blank...");
            return null;
        }
        Map<String, String> headers = httpRequestInfo.getHeaders();

        headers = headers == null ? new HashMap<>(16) : headers;
        headers.put("user-agent", USER_AGENT);

        Request.Builder requestBuilder = new Request.Builder();

        for (Map.Entry<String, String> en : headers.entrySet()) {
            requestBuilder.addHeader(en.getKey(), en.getValue());
        }

        switch (httpRequestInfo.getMethodType()) {
            case GET:
                requestBuilder = this.get(httpRequestInfo, requestBuilder);
                break;
            case PUT:
            case POST:
            case PATCH:
            default:
                requestBuilder = this.pppd(httpRequestInfo, requestBuilder);
                break;

        }
        return requestBuilder.build();
    }

    private Request.Builder get(HttpRequestInfo httpRequestInfo, Request.Builder requestBuilder) {
        StringBuilder urlBuilder = new StringBuilder(httpRequestInfo.getUrl());

        if (httpRequestInfo.getParams() == null || httpRequestInfo.getParams().isEmpty()) {
            return requestBuilder.url(urlBuilder.toString()).get();
        }
        urlBuilder.append("?");

        if (httpRequestInfo.getParams().containsKey(HttpRequestInfo.DEFAULT_PARAM_KEY)) {
            String value = httpRequestInfo.getParams().get(HttpRequestInfo.DEFAULT_PARAM_KEY).toString();
            urlBuilder.append(value).append("&");
        } else {
            httpRequestInfo.getParams().entrySet().forEach(param -> {
                try {
                    urlBuilder.append(URLEncoder.encode(param.getKey(), httpRequestInfo.getCharsetName()))
                            .append("=")
                            .append(URLEncoder.encode(param.getValue().toString(), httpRequestInfo.getCharsetName()))
                            .append("&");
                } catch (UnsupportedEncodingException e) {
                    log.error(">>>>>e:", e);
                }
            });

        }
        urlBuilder.append("_timestamp=").append(Instant.now().getEpochSecond());
        requestBuilder.addHeader(CONTENT_TYPE, APPLICATION_FORM + httpRequestInfo.getCharsetName());
        return requestBuilder.url(urlBuilder.toString()).get();
    }


    /**
     * post put patch delete
     *
     * @param httpRequestInfo
     * @param requestBuilder
     */
    private Request.Builder pppd(HttpRequestInfo httpRequestInfo, Request.Builder requestBuilder) {
        if (!Arrays.asList(MethodType.POST, MethodType.PUT, MethodType.PATCH, MethodType.DELETE)
                .contains(httpRequestInfo.getMethodType())) {
            return requestBuilder;
        }
        RequestBody requestBody;
        switch (httpRequestInfo.getApplicationType()) {
            case JSON:
                String json = "";
                if (httpRequestInfo.getParams().containsKey(HttpRequestInfo.DEFAULT_PARAM_KEY)) {
                    json = httpRequestInfo.getParams().get(HttpRequestInfo.DEFAULT_PARAM_KEY).toString();
                } else {
                    json = JsonUtil.toJson(httpRequestInfo.getParams());
                }
                requestBody =
                        RequestBody.create(json, MediaType.parse(APPLICATION_JSON + httpRequestInfo.getCharsetName()));
                requestBuilder.addHeader(CONTENT_TYPE, APPLICATION_JSON + httpRequestInfo.getCharsetName());
                break;
            case XML:
                String xmlStr = httpRequestInfo.getParams().get(HttpRequestInfo.DEFAULT_PARAM_KEY).toString();
                requestBody = RequestBody.create(xmlStr, MediaType.parse(APPLICATION_XML + httpRequestInfo.getCharsetName()));
                requestBuilder.addHeader(CONTENT_TYPE, APPLICATION_XML + httpRequestInfo.getCharsetName());
                break;
            case FORM_DATA:
                FileInfo fileInfo = httpRequestInfo.getFileInfo();
                RequestBody fileBody = RequestBody.create(fileInfo.getFileBytes(), MediaType.parse(fileInfo.getMediaType()));

                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart(fileInfo.getFileKey(), fileInfo.getFileName(), fileBody);

                if (httpRequestInfo.getParams() != null) {
                    for (Map.Entry<String, Object> entry : httpRequestInfo.getParams().entrySet()) {
                        if (Objects.nonNull(entry.getValue())) {
                            builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
                        }
                    }
                }
                requestBody = builder.build();
                break;
            case FORM:
            default:
                FormBody.Builder formBody = new FormBody.Builder();
                if (httpRequestInfo.getParams() != null) {
                    httpRequestInfo.getParams().entrySet()
                            .forEach(en -> formBody.add(en.getKey(), en.getValue().toString()));
                }
                requestBody = formBody.build();
                requestBuilder.addHeader(CONTENT_TYPE, APPLICATION_FORM + httpRequestInfo.getCharsetName());
                break;
        }

        if (httpRequestInfo.getMethodType().equals(MethodType.POST)) {
            requestBuilder.url(httpRequestInfo.getUrl()).post(requestBody);
        } else if (httpRequestInfo.getMethodType().equals(MethodType.PUT)) {
            requestBuilder.url(httpRequestInfo.getUrl()).put(requestBody);
        } else if (httpRequestInfo.getMethodType().equals(MethodType.PATCH)) {
            requestBuilder.url(httpRequestInfo.getUrl()).patch(requestBody);
        } else if (httpRequestInfo.getMethodType().equals(MethodType.DELETE)) {
            requestBuilder.url(httpRequestInfo.getUrl()).delete(requestBody);
        }

        return requestBuilder;

    }

}
