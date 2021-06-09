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
import java.util.*;
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


    private static final String FILE_PATH_KEY = "filePath";
    private static final String FUNC_NAME_KEY = "funcName";

    private static final String FILE_PATH = "/home/zhyx/cdss_java.r";

    private static final String FUNC_NAME = "CDSS_stat";

    @Autowired
    private HttpServletRequest request;

    @GetMapping
    public String index(IndexQueryDTO indexQueryDTO, Model model) {
        Map<String, Object> params = getParams(request);

        if (StringUtils.isBlank(indexQueryDTO.getFilePath())) {
            indexQueryDTO.setFilePath(FILE_PATH);

            params.putIfAbsent("dept_name", "妇科");
            params.putIfAbsent("time_select", "最近一个月");
            params.putIfAbsent("data_name", "（警告）多正常值检验项目异常结果警示");
            params.putIfAbsent("doctor_name", "赖紫玲");
            params.putIfAbsent("message", "查看检验报告");
        }
        if (StringUtils.isBlank(indexQueryDTO.getFuncName())) {
            indexQueryDTO.setFuncName(FUNC_NAME);
        }


        long start = System.currentTimeMillis();
        String result = RserveUtil.callRserve(HOST, PORT, indexQueryDTO.getFilePath(), indexQueryDTO.getFuncName(), params);
        long end = System.currentTimeMillis();
        log.debug(">>>>>r result:{}", result);
        long rTime = end - start;

        if (StringUtils.isBlank(result)) {
            result = "{\"title\":{\"text\":\"ECharts 默认数据\"},\"tooltip\":{},\"legend\":{\"data\":[\"销量\"]},\"xAxis\":{\"data\":[\"衬衫\",\"羊毛衫\",\"雪纺衫\",\"裤子\",\"高跟鞋\",\"袜子\"]},\"yAxis\":{},\"series\":[{\"name\":\"销量\",\"type\":\"bar\",\"data\":[5,20,36,10,10,20]}]}";
        }
        start = System.currentTimeMillis();
        Map res = JsonUtil.parse(result, Map.class);
        end = System.currentTimeMillis();
        long parseTime = end - start;
        model.addAttribute("echart", res);
        model.addAttribute("json", result);
        model.addAttribute("rTime", rTime);
        model.addAttribute("parseTime", parseTime);
        return "index";
    }


    /**
     * 组装echarts的option属性
     * @param json
     * @return
     */
    private Map buildEChartOptions(String json) {
        List<Map> list = JsonUtil.parseArray(json, Map.class);

        final String xAxisDataKey = "$xAxisData";
        final String seriesDataKey = "$seriesData";

        // {"yAxis":[{"show":true}],"xAxis":[{"data":[],"type":"category","boundaryGap":true}],"legend":{"data":["bar"]},"series":[{"data":[],"name":"bar","type":"bar","yAxisIndex":0,"xAxisIndex":0,"coordinateSystem":"cartesian2d"}],"title":[{"text":"Bar and step charts"}]}

        String options = "{\n" +
                "    \"yAxis\": [{\n" +
                "        \"show\": true\n" +
                "    }],\n" +
                "    \"xAxis\": [{\n" +
                "        \"data\": $xAxisData,\n" +
                "        \"type\": \"category\",\n" +
                "        \"boundaryGap\": true\n" +
                "    }],\n" +
                "    \"legend\": {\n" +
                "        \"data\": [\"bar\"]\n" +
                "    },\n" +
                "    \"series\": [{\n" +
                "        \"data\": $seriesData,\n" +
                "        \"name\": \"bar\",\n" +
                "        \"type\": \"bar\",\n" +
                "        \"yAxisIndex\": 0,\n" +
                "        \"xAxisIndex\": 0,\n" +
                "        \"coordinateSystem\": \"cartesian2d\"\n" +
                "    }],\n" +
                "    \"title\": [{\n" +
                "        \"text\": \"Bar and step charts\"\n" +
                "    }]\n" +
                "}";
        return null;
    }

    private Map<String, Object> getParams(HttpServletRequest request) {
        if (Objects.isNull(request)) {
            return Collections.emptyMap();
        }
        Map<String, String[]> paramMap = new HashMap<>(request.getParameterMap());
        paramMap.remove(FILE_PATH_KEY);
        paramMap.remove(FUNC_NAME_KEY);
        return paramMap.entrySet().stream()
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

        /**
         * 是否元数据
         */
        private Boolean isMetadata;

    }

}
