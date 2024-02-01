package com.coral.cloud.monitor.common.util;

import com.coral.base.common.StringPool;
import com.coral.base.common.json.JsonUtil;
import com.coral.cloud.monitor.common.constants.MetricsKey;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huss
 * @version 1.0
 * @className ApiUtil
 * @description api
 * @date 2023/4/13 14:21
 */
public final class ApiUtil {

    private final static Pattern PARAM_PATTERN = Pattern.compile("\\{\\w+\\}");

    public static String buildUrl(String base, String url) {
        StringBuilder urlSb = new StringBuilder();
        final String slash = StringPool.SLASH + "";
        urlSb.append(StringUtils.isBlank(base) ? "" : base.trim());
        if (!urlSb.toString().endsWith(slash)) {
            urlSb.append(slash);
        }
        if (StringUtils.isBlank(url)) {
            return urlSb.toString();
        }
        if (url.trim().startsWith(slash)) {
            urlSb.append(url.trim().substring(1));
        } else {
            urlSb.append(url);
        }
        return urlSb.toString();
    }


    public static String buildUrl(String base, String url, Map<String, Object> params) {
        String baseUrl = buildUrl(base, url);
        return buildUrl(baseUrl, params);
    }

    public static String buildUrl(String url, Map<String, Object> params) {
        if (StringUtils.isBlank(url)) {
            return "";
        }
        if (Objects.isNull(params)) {
            return url;
        }

        Object obj = params.get(MetricsKey.EXEC_PARAM_API_ROUTE_PARAMS);
        if (Objects.isNull(obj)) {
            return url;
        }
        Map<String, Object> routeParams = (Map<String, Object>) obj;

        Matcher matcher = PARAM_PATTERN.matcher(url);

        while (matcher.find()) {
            String group = matcher.group();
            String field = group.substring(1, group.length() - 1);

            if (!routeParams.containsKey(field)) {
                continue;
            }
            Object val = routeParams.get(field);
            field = new StringBuilder("")
                    .append(StringPool.BACK_SLASH).append(StringPool.LEFT_BRACE)
                    .append(field)
                    .append(StringPool.BACK_SLASH).append(StringPool.RIGHT_BRACE)
                    .toString();
            url = url.replaceAll(field, val.toString());
        }
        return url;
    }

    public static String buildRouteParamStr(Map<String, Object> params) {
        Object obj = params.get(MetricsKey.EXEC_PARAM_API_ROUTE_PARAMS);
        if (Objects.isNull(obj)) {
            return "";
        }
        Map<String, Object> routeParams = (Map<String, Object>) obj;

        return JsonUtil.toJson(routeParams);
    }

    public static String getExceptionSimpleName(Throwable throwable) {
        if (throwable == null) {
            return MetricsKey.METRICS_DEF_TAG_VALUE;
        }

        if (throwable.getCause() == null) {
            return throwable.getClass().getSimpleName();
        }

        return throwable.getCause().getClass().getSimpleName();
    }


    public static ExecutorService executor(String poolName, int corePoolSize, int maximumPoolSize) {
        ThreadFactory namedThreadFactory =
                new ThreadFactoryBuilder().setNameFormat(poolName.toLowerCase() + "-pool-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        return pool;
    }

}
