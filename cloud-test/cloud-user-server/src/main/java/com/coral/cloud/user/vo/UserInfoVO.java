package com.coral.cloud.user.vo;

import com.coral.base.common.http.response.IResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author huss
 * @version 1.0
 * @className UserInfoVO
 * @description 用户信息
 * @date 2022/4/2 13:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户信息")
public class UserInfoVO implements IResponse {
    @Schema(description = "用户编码")
    private String userNo;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "订单信息,返回object类型仅供测试用")
    private Object orders;


    private UserInfoVO(String userNo, String username) {
        this.userNo = userNo;
        this.username = username;
    }

    /**
     * 创建用户信息
     *
     * @return
     */
    public static UserInfoVO createUser() {
        return createUser("", "");
    }

    public static UserInfoVO createUser(String userNo) {
        return createUser(userNo, "");
    }

    public static UserInfoVO createUserWithName(String username) {
        return createUser("", username);
    }


    /**
     * 创建用户信息
     *
     * @param username
     * @return
     */
    public static UserInfoVO createUser(String userNo, String username) {
        String uid = UUID.randomUUID().toString().replaceAll("-", "");
        username = StringUtils.hasText(username) ? username.trim() : "用户" + uid;
        userNo = StringUtils.hasText(userNo) ? userNo : uid;
        return new UserInfoVO(userNo, username);
    }
}