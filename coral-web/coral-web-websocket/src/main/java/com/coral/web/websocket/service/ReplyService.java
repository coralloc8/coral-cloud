package com.coral.web.websocket.service;

import com.coral.web.core.response.Result;
import com.coral.web.core.response.Results;
import com.coral.web.websocket.common.enums.BusinessType;
import com.coral.web.websocket.dto.UrlInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author huss
 * @version 1.0
 * @className ReplyService
 * @description 响应服务
 * @date 2022/10/28 14:17
 */
@Slf4j
@Service
public class ReplyService {

    public Result findUrl(BusinessType businessType, String function, Object params) {
        log.info(">>>>[findUrl] businessType:{},function:{},params:{}", businessType, function, params);
        Result result = new Results().failure();
        switch (businessType) {
            case YWZ:
                result = new Results().success(new UrlInfoDTO("https://www.hao123.com/?id=111"));
                break;
            case CDSS:
                result = new Results().success(new UrlInfoDTO("https://www.baidu.com/no=2233"));
                break;
        }

        return result;
    }
}
