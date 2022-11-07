package com.coral.web.websocket.web;

import com.coral.web.core.response.Result;
import com.coral.web.core.web.BaseController;
import com.coral.web.websocket.dto.ReplyUrlRequestDTO;
import com.coral.web.websocket.dto.UrlInfoDTO;
import com.coral.web.websocket.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huss
 * @version 1.0
 * @className ReplyController
 * @description 响应控制器
 * @date 2022/10/28 10:40
 */
@Slf4j
@RequestMapping("/reply")
@RestController
public class ReplyController extends BaseController {


    @Autowired
    private ReplyService replyService;

    @PostMapping("/url")
    public Result<UrlInfoDTO> ulr(@RequestBody ReplyUrlRequestDTO replyUrlRequest) {
        return replyService.findUrl(replyUrlRequest.getBusinessType(), replyUrlRequest.getFunction(), replyUrlRequest.getParams());
    }
}
