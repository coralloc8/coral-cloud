package com.coral.base.common.http;

import com.coral.base.common.StringPool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 入参
 * @author: huss
 * @time: 2021/3/1 21:27
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HttpRequestInfo {

    public final static String DEFAULT_PARAM_KEY = "ALL";

    private final static Pattern PARAM_PATTERN = Pattern.compile("\\{\\w+\\}");

    /**
     * URL
     */
    private String url;

    /**
     * 请求头设置
     */
    private Map<String, String> headers;

    /**
     * 参数设置
     */
    private Map<String, Object> params;

    /**
     * url入参
     */
    private Map<String, Object> urlParams;

    /**
     * 请求方法类型
     */
    private MethodType methodType;

    /**
     * 请求数据类型
     */
    private ApplicationType applicationType;

    /**
     * 编码
     */
    private Charset charset;

    /**
     * 添加请求头
     *
     * @param key
     * @param value
     * @return
     */
    public HttpRequestInfo addHeader(String key, String value) {
        headers = headers == null ? new HashMap<>(16) : headers;
        headers.put(key, value);
        return this;
    }

    /**
     * 添加入参
     *
     * @param key
     * @param value
     * @return
     */
    public HttpRequestInfo addParam(String key, Object value) {
        params = params == null ? new HashMap<>(16) : params;
        params.put(key, value);
        return this;
    }

    /**
     * 此种方式入参的会将其他的所有入参都给清除掉，只保留该入参
     *
     * @param value
     * @return
     */
    public HttpRequestInfo addParam(String value) {
        params = Collections.singletonMap(DEFAULT_PARAM_KEY, value);
        return this;
    }

    public String getCharsetName() {
        return Objects.isNull(this.getCharset()) ? StandardCharsets.UTF_8.name() : this.getCharset().name();
    }


    public HttpRequestInfo addUrlParam(String key, Object value) {
        urlParams = Objects.isNull(urlParams) ? new HashMap<>(16) : urlParams;
        urlParams.put(key, value);
        return this;
    }


    public String getUrl() {
        if (StringUtils.isBlank(url)) {
            return "";
        }

        if (Objects.isNull(getUrlParams()) || getUrlParams().isEmpty()) {
            return url;
        }

        Matcher matcher = PARAM_PATTERN.matcher(url);

        while (matcher.find()) {
            String group = matcher.group();
            String field = group.substring(1, group.length() - 1);

            if (!getUrlParams().containsKey(field)) {
                continue;
            }

            Object val = getUrlParams().get(field);

            field = new StringBuilder("")
                    .append(StringPool.BACK_SLASH).append(StringPool.LEFT_BRACE)
                    .append(field)
                    .append(StringPool.BACK_SLASH).append(StringPool.RIGHT_BRACE)
                    .toString();
            url = url.replaceAll(field, val.toString());
        }
        return url;
    }
}