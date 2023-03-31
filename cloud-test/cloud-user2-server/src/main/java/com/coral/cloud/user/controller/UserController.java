package com.coral.cloud.user.controller;

import com.coral.base.common.RandomUtil;
import com.coral.base.common.exception.SystemRuntimeException;
import com.coral.cloud.user.common.config.UserProperty;
import com.coral.cloud.user.common.errormsg.UserErrorMessage;
import com.coral.cloud.user.dto.UserSaveDTO;
import com.coral.cloud.user.service.PrometheusCustomMonitor;
import com.coral.cloud.user.service.UserService;
import com.coral.cloud.user.vo.MonitorVO;
import com.coral.cloud.user.vo.UserInfoVO;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService userService;


    @Autowired
    private PrometheusCustomMonitor prometheusCustomMonitor;


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
    @Timed(value = "find_user_info_duration", description = "查询所有用户信息耗时")
    @Counted(value = "find_user_info_count",description = "查询所有用户信息调用次数")
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
        return ResponseEntity.ok(userService.findUserDetail(userNo, USERS));
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
     * 保存用户信息测试
     *
     * @param userSaveDTO
     * @return
     */
    @Override
    @PostMapping("/test")
    public ResponseEntity<UserInfoVO> saveUserTest(@RequestBody UserSaveDTO userSaveDTO, UserSaveDTO formData) {
        log.info(">>>>>userSaveDTO:{}", userSaveDTO);
        log.info(">>>>>userSaveDTO form data:{}", formData);
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

    @PostMapping("/monitor")
    @Override
    public ResponseEntity<MonitorVO> prometheusCustomMonitor() {
        MonitorVO.MonitorVOBuilder builder = MonitorVO.builder();
        int number = RandomUtil.buildRandom(5);
        prometheusCustomMonitor.addMonitorGaugeTotal(number);
        prometheusCustomMonitor.addCounter();
        int summaryNumber = RandomUtil.buildRandom(3);
        prometheusCustomMonitor.addSummary(summaryNumber);

        builder.counterNumber(Double.valueOf(number))
                .gaugeNumber(prometheusCustomMonitor.getMonitorGaugeTotal())
                .summaryNumber(Double.valueOf(summaryNumber))
                .summaryCount(prometheusCustomMonitor.getSummaryCount())
                .summaryTotalAmount(prometheusCustomMonitor.getSummaryTotalAmount())
                .summaryMean(prometheusCustomMonitor.getSummaryMean())
                .summaryMax(prometheusCustomMonitor.getSummaryMax())
        ;

        return ResponseEntity.ok(builder.build());
    }


}
