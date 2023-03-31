package com.coral.cloud.user.service;

import com.coral.base.common.CollectionUtil;
import com.coral.base.common.exception.SystemRuntimeException;
import com.coral.cloud.order.feign.dto.OrderInfoDTO;
import com.coral.cloud.order.feign.service.UserOrderFeign;
import com.coral.cloud.user.common.errormsg.UserErrorMessage;
import com.coral.cloud.user.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className UserService
 * @description 用户服务
 * @date 2022/4/14 10:51
 */
@Slf4j
@Service
public class UserService {


    @Autowired
    private UserOrderFeign userOrderFeign;


    /**
     * 查询用户详情
     *
     * @param userNo
     * @return
     */
    public UserInfoVO findUserDetail(String userNo, List<UserInfoVO> users) {
        if (CollectionUtil.isBlank(users)) {
            throw new SystemRuntimeException(UserErrorMessage.USER_NOT_EXIST);
        }

        return users.stream().filter(e -> e.getUserNo().equals(userNo)).findAny()
                .map(user -> {
                    try {
                        ResponseEntity<List<OrderInfoDTO>> orderResponse = userOrderFeign.findUserOrderInfos(userNo);
                        if (orderResponse.getStatusCode().equals(HttpStatus.OK)) {
                            log.info(">>>>>user orders:{}", orderResponse.getBody());
                            // 测试order实例挂了一个后会不会进入重试并负载到另外一个在线的实例上，并最终返回数据结果
                            user.setOrders(orderResponse.getBody());
                        } else {
                            log.info("用户订单数据查询失败，请检查");
                        }
                    } catch (Exception e) {
                        log.error(">>>>>查询用户订单接口失败,error:", e);
                    }
                    return user;
                })
                .orElseThrow(() -> new SystemRuntimeException(UserErrorMessage.USER_NOT_EXIST));
    }
}
