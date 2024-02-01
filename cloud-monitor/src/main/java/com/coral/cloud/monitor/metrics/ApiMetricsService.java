package com.coral.cloud.monitor.metrics;

import com.coral.base.common.CollectionUtil;
import com.coral.base.common.EnumUtil;
import com.coral.base.common.StringUtils;
import com.coral.base.common.http.ApplicationType;
import com.coral.base.common.http.HttpRequestInfo;
import com.coral.base.common.http.MethodType;
import com.coral.base.common.json.JsonUtil;
import com.coral.base.common.protocol.HttpProtocolHandler;
import com.coral.cloud.monitor.common.enums.MetricsSourceType;
import com.coral.cloud.monitor.common.util.ApiUtil;
import com.coral.cloud.monitor.dto.job.MetricsExecConfInfoDTO;
import com.coral.cloud.monitor.dto.metresponse.ApiResponseInfoDTO;
import com.coral.cloud.monitor.dto.metresponse.ErrorResponseInfoDTO;
import com.coral.cloud.monitor.dto.metresponse.IResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.coral.cloud.monitor.common.constants.MetricsKey.*;


/**
 * @author huss
 * @version 1.0
 * @className ApiMetricsService
 * @description api指标服务
 * @date 2023/4/13 17:51
 */
@Component
@Slf4j
public class ApiMetricsService implements IMetricsService {
    @Autowired
    private MetricsFactory metricsFactory;

    @Override
    public MetricsSourceType metricsSourceType() {
        return MetricsSourceType.API;
    }

    @Override
    public void run(MetricsExecConfInfoDTO metricsExecConf) {
        this.timerRun(metricsExecConf);
        this.commonRun(metricsExecConf);
    }

    private void commonRun(MetricsExecConfInfoDTO metricsExecConf) {
        if (isTimer(metricsExecConf.getMetricsType())) {
            return;
        }
        log.info(">>>>>[Api Metrics Common] run start.");
        double value = 0;
        String exceptionClass = "";
        try {
            String resultStr = sendHttpRequest(metricsExecConf);
            Map resultMap = JsonUtil.toMap(resultStr);

            String datCodeKey = this.findConf(EXEC_PARAM_API_DATA_CODE, metricsExecConf);

            String data = this.getValue(resultMap, datCodeKey).toString();

            if (StringUtils.isNotBlank(data) && StringUtils.isNumber(data.trim())) {
                value = Double.valueOf(data.trim());
            }

        } catch (Exception e) {
            log.error(">>>>>[Api Metrics Common] run error", e);
            exceptionClass = ApiUtil.getExceptionSimpleName(e);
        } finally {
            metricsFactory.record(metricsExecConf, exceptionClass, value);
        }
        log.info(">>>>>[Api Metrics Common] run end.");
    }

    private void timerRun(MetricsExecConfInfoDTO metricsExecConf) {
        if (!isTimer(metricsExecConf.getMetricsType())) {
            return;
        }
        log.info(">>>>>[Api Metrics Timer] run start.");
        IResponse response = null;
        String watchId = UUID.randomUUID().toString().replace("-", "");
        try {
            metricsFactory.watchStart(watchId, metricsExecConf, 0);
            String resultStr = sendHttpRequest(metricsExecConf);

            Map resultMap = JsonUtil.toMap(resultStr);

            String errorCodeKey = this.findConf(EXEC_PARAM_API_ERR_CODE, metricsExecConf);
            String errorMsgKey = this.findConf(EXEC_PARAM_API_ERR_MSG, metricsExecConf);

            String errorCode = resultMap.getOrDefault(errorCodeKey, "").toString();
            String errorMsg = resultMap.getOrDefault(errorMsgKey, "").toString();

            response = ApiResponseInfoDTO.builder()
                    .status(String.valueOf("200"))
                    .errorCode(errorCode)
                    .errorMsg(errorMsg)
                    .build();
        } catch (Exception e) {
            log.error(">>>>>[Api Metrics Timer] run error", e);
            response = new ErrorResponseInfoDTO(ApiUtil.getExceptionSimpleName(e));
        } finally {
            metricsFactory.watchStop(watchId, metricsExecConf, response);
            log.info(">>>>>[Api Metrics Timer] run end.");
        }
    }

    /**
     * 发送http请求
     *
     * @param metricsExecConf
     * @return
     */
    private String sendHttpRequest(MetricsExecConfInfoDTO metricsExecConf) {
        String url = ApiUtil.buildUrl(metricsExecConf.getDataSource(), metricsExecConf.getExecPath(),
                metricsExecConf.getParams());

        ApplicationType applicationType;
        MethodType methodType;
        try {
            methodType = EnumUtil.getOf(MethodType.class,
                    this.findConf(EXEC_PARAM_API_METHOD, metricsExecConf).toString());
            applicationType = EnumUtil.getOf(ApplicationType.class,
                    this.findConf(EXEC_PARAM_API_APPLICATION_TYPE, metricsExecConf).toString());
        } catch (Exception e) {
            log.error("枚举转换异常.", e);
            applicationType = ApplicationType.FORM;
            log.error("[method]枚举转换异常,设置默认值.[{}]", ApplicationType.FORM);
            methodType = MethodType.POST;
            log.error("[methodType]枚举转换异常,设置默认值.[{}]", MethodType.POST);
        }


        Map<String, Object> params = this.findConf(EXEC_PARAM_API_PARAMS, metricsExecConf);
        if (Objects.isNull(params)) {
            params = new HashMap<>(2);
            params.put("_t", System.currentTimeMillis());
        }

        HttpRequestInfo requestInfo = HttpRequestInfo.builder()
                .url(url)
                .headers(this.findConf(EXEC_PARAM_API_HEADERS, metricsExecConf))
                .urlParams(this.findConf(EXEC_PARAM_API_ROUTE_PARAMS, metricsExecConf))
                .params(params)
                .methodType(methodType)
                .applicationType(applicationType)
                .charset(StandardCharsets.UTF_8)
                .build();
        String resultStr = new HttpProtocolHandler().send(requestInfo);
        return resultStr;
    }

    /**
     * 获取数据
     *
     * @param data
     * @param key  可以以 "data.token.token" 这样一级一级的精确查找 也可以直接模糊查找 "token"，模糊查找碰到相同的key先返回最先查找的key对应的值
     * @return
     */
    public Object getValue(Map<String, Object> data, String key) {
        log.debug(">>>>>>>>data:{},key:{}", data, key);
        if (StringUtils.isBlank(key) || Objects.isNull(data) || data.isEmpty()) {
            log.error("getValue 参数为空");
            return "";
        }
        if (key.contains(".")) {
            Map<String, Object> obj = data;
            String[] keys = key.split("\\.");
            for (int i = 0, size = keys.length; i < size - 1; i++) {
                if (StringUtils.isBlank(keys[i])) {
                    continue;
                }
                Object cur = obj.get(keys[i]);
                if (cur instanceof Collection) {
                    obj = (Map<String, Object>) ((Collection<?>) cur).iterator().next();
                } else {
                    obj = (Map<String, Object>) cur;
                }
            }
            return obj.containsKey(keys[keys.length - 1]) ? obj.get(keys[keys.length - 1]) : "";
        }

        return getValue(data, key, new LinkedList<>());
    }

    private Object getValue(Map<String, Object> data, String key, Deque<Object> stack) {
        Deque<Object> curStack = CollectionUtil.isBlank(stack) ? new LinkedList<>() : stack;
        for (Map.Entry<String, Object> en : data.entrySet()) {
            Object val = en.getValue();
            if (key.equals(en.getKey())) {
                return val;
            }

            if ((val instanceof Map) || (val instanceof Collection)) {
                curStack.add(val);
            }

        }

        if (!curStack.isEmpty()) {
            Object obj = curStack.poll();

            if (obj instanceof Collection) {
                obj = ((Collection) obj).iterator().next();
            }

            return getValue((Map<String, Object>) obj, key, curStack);
        }

        return "";
    }


}
