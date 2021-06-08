package com.coral.test.r.controller;

import com.coral.base.common.StringUtils;
import com.coral.base.common.json.JsonUtil;
import com.coral.test.r.util.RserveUtil;
import com.coral.web.core.web.BaseController;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author huss
 * @version 1.0
 * @className IndexController
 * @description 首页
 * @date 2021/6/7 14:28
 */
@Slf4j
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {

    private static final String HOST = "192.168.29.190";
    private static final int PORT = 6311;

    private static final String FILE_PATH = "/home/zhyx/cdss_java.r";

    private static final String FUNC_NAME = "CDSS_stat";


    @Autowired
    private HttpServletRequest request;

    @GetMapping
    public String index(IndexQueryDTO indexQueryDTO, Model model) {
        Map<String, Object> params = getParams(request);

        if (StringUtils.isBlank(indexQueryDTO.getFilePath())) {
            indexQueryDTO.setFilePath(FILE_PATH);
        }
        if (StringUtils.isBlank(indexQueryDTO.getFuncName())) {
            indexQueryDTO.setFuncName(FUNC_NAME);
        }

        params.putIfAbsent("dept_name", "妇科");
        params.putIfAbsent("time_select", "最近一个月");
        params.putIfAbsent("data_name", "（警告）多正常值检验项目异常结果警示");
        params.putIfAbsent("doctor_name", "赖紫玲");
        params.putIfAbsent("message", "查看检验报告");

        String result = RserveUtil.callRserve(HOST, PORT, indexQueryDTO.getFilePath(), indexQueryDTO.getFuncName(), params);
        log.info(">>>>>result:{}", result);

        if (StringUtils.isBlank(result)) {
            result = "{\"title\":{\"text\":\"ECharts 默认数据\"},\"tooltip\":{},\"legend\":{\"data\":[\"销量\"]},\"xAxis\":{\"data\":[\"衬衫\",\"羊毛衫\",\"雪纺衫\",\"裤子\",\"高跟鞋\",\"袜子\"]},\"yAxis\":{},\"series\":[{\"name\":\"销量\",\"type\":\"bar\",\"data\":[5,20,36,10,10,20]}]}";

        }
        Map res = JsonUtil.parse(result, Map.class);

        model.addAttribute("echart", res);
        model.addAttribute("json", result);
        return "index";
    }


    private Map<String, Object> getParams(HttpServletRequest request) {
        if (Objects.isNull(request)) {
            return Collections.emptyMap();
        }
        return request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(k -> k.getKey(), v -> Arrays.asList(v.getValue())));
    }


    @Data
    public static class IndexQueryDTO {
        /**
         * 文件路径
         */
        private String filePath;
        /**
         * 函数名称
         */
        private String funcName;

    }

}
