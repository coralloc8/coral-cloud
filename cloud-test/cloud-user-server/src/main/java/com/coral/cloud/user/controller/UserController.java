package com.coral.cloud.user.controller;

import com.coral.base.common.exception.SystemRuntimeException;
import com.coral.cloud.order.feign.dto.OrderInfoDTO;
import com.coral.cloud.order.feign.service.UserOrderFeign;
import com.coral.cloud.user.common.config.UserProperty;
import com.coral.cloud.user.common.errormsg.UserErrorMessage;
import com.coral.cloud.user.dto.UserSaveDTO;
import com.coral.cloud.user.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author huss
 * @version 1.0
 * @className UserController
 * @description 用户控制器
 * @date 2022/3/31 17:37
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController implements UserApi {

    private static final List<UserInfoVO> USERS = new ArrayList<>();

    @Autowired
    private UserProperty userProperty;

    @Autowired
    private UserOrderFeign userOrderFeign;

    static {
        IntStream.rangeClosed(0, 5).forEach(e -> USERS.add(UserInfoVO.createUser(String.valueOf(e + 1), "")));
        IntStream.rangeClosed(0, 5).forEach(e -> USERS.add(UserInfoVO.createUser()));
    }

    /**
     * 查询所有用户信息
     *
     * @return
     */
    @GetMapping
    @Override
    public ResponseEntity<List<UserInfoVO>> findUserInfos() {
        log.info(">>>>username:{}", userProperty.getUsername());
        return ResponseEntity.ok(USERS);
    }

    /**
     * 查询用户详情
     *
     * @param userNo
     * @return
     */
    @GetMapping("/{userNo}")
    @Override
    public ResponseEntity<UserInfoVO> findUserDetailInfo(@PathVariable String userNo) {
        return USERS.stream().filter(e -> e.getUserNo().equals(userNo)).findAny()
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
                    return ResponseEntity.ok(user);
                })
                .orElseThrow(() -> new SystemRuntimeException(UserErrorMessage.USER_NOT_EXIST));
    }

    /**
     * 保存用户信息
     *
     * @param userSaveDTO
     * @return
     */
    @Override
    @PostMapping
    public ResponseEntity<UserInfoVO> saveUser(@RequestBody UserSaveDTO userSaveDTO) {
        UserInfoVO userInfoVO = UserInfoVO.createUserWithName(userSaveDTO.getUsername());
        USERS.add(userInfoVO);
        return ResponseEntity.ok(userInfoVO);
    }

    /**
     * 修改用户信息
     *
     * @param userNo
     * @param userSaveDTO
     * @return
     */
    @Override
    @PutMapping("/{userNo}")
    public ResponseEntity<UserInfoVO> updateUser(@PathVariable String userNo, @RequestBody UserSaveDTO userSaveDTO) {
        return USERS.stream().filter(e -> e.getUserNo().equals(userNo)).findAny()
                .map(user -> {
                    user.setUsername(userSaveDTO.getUsername());
                    return ResponseEntity.ok(user);
                })
                .orElseThrow(() -> new SystemRuntimeException(UserErrorMessage.USER_NOT_EXIST));
    }


}
