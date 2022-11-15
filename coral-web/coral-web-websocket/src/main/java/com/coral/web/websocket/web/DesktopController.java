package com.coral.web.websocket.web;

import com.coral.web.core.response.Result;
import com.coral.web.core.response.Results;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className DesktopController
 * @description 桌面控制器
 * @date 2022/10/27 14:26
 */
@Slf4j
@RestController
public class DesktopController {

    @PostMapping("/api")
    public Result<ResponseDTO> api(@RequestBody RequestDTO requestDTO) {
        String url = "";
        List<String> urls = Arrays.asList(
                "http://192.168.29.189/alpha_diagnosis_doctor/index.html?diagnosisId=74449",
                "http://192.168.29.189/alpha_diagnosis_doctor/index.html?diagnosisId=74450"
        );

        if ("ywz".equals(requestDTO.getProject())) {
            url = urls.get(System.currentTimeMillis() % 2 == 0 ? 0 : 1);
        }

        return new Results().success(new ResponseDTO(requestDTO.getProject(), requestDTO.getFunction(), url));
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class RequestDTO {

        private String project;

        private String function;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ResponseDTO {

        private String project;

        private String function;

        private String url;
    }

}
