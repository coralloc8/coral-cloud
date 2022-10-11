package com.coral.cloud.user.controller;

import com.coral.base.common.http.response.ErrorResponse;
import com.coral.cloud.user.common.constants.ApiVersion;
import com.coral.cloud.user.common.errormsg.UserErrorMessageDesc;
import com.coral.cloud.user.dto.UserSaveDTO;
import com.coral.cloud.user.vo.MonitorVO;
import com.coral.cloud.user.vo.UserInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className UserApi
 * @description 用户api
 * @date 2022/4/2 11:26
 */
@Tags
public interface UserApi {

    /**
     * 查询所有用户信息
     *
     * @return
     */
    @Operation(summary = "查询所有用户信息", description = "查询所有用户信息", tags = {ApiVersion.V_1_0_0})
    @ApiResponses({
            @ApiResponse(description = "success", responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserInfoVO.class))))
    })
    ResponseEntity<List<UserInfoVO>> findUserInfos();

    /**
     * 查询用户详情
     *
     * @param userNo
     * @return
     */
    @Operation(summary = "查询用户详情", description = "查询用户详情", tags = {ApiVersion.V_1_0_0})
    @ApiResponses({
            @ApiResponse(description = "success", responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = UserInfoVO.class))),
            @ApiResponse(description = UserErrorMessageDesc.USER_NOT_EXIST_DESC,
                    responseCode = UserErrorMessageDesc.USER_NOT_EXIST_CODE,
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Parameters({
            @Parameter(name = "userNo", description = "用户编码"),
    })
    ResponseEntity<UserInfoVO> findUserDetailInfo(String userNo);

    /**
     * 保存用户信息
     *
     * @param userSaveDTO
     * @return
     */
    @Operation(summary = "保存用户信息", description = "保存用户信息", tags = {ApiVersion.V_1_0_0})
    @ApiResponses({
            @ApiResponse(description = "success", responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = UserInfoVO.class)))
    })
    ResponseEntity<UserInfoVO> saveUser(UserSaveDTO userSaveDTO);

    /**
     * 保存用户信息测试
     *
     * @param userSaveDTO
     * @return
     */
    @Operation(summary = "保存用户信息测试", description = "保存用户信息测试", tags = {ApiVersion.V_1_0_0})
    @ApiResponses({
            @ApiResponse(description = "success", responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = UserInfoVO.class)))
    })
    ResponseEntity<UserInfoVO> saveUserTest(UserSaveDTO userSaveDTO, UserSaveDTO formData);

    /**
     * 修改用户信息
     *
     * @param userNo
     * @param userSaveDTO
     * @return
     */
    @Operation(summary = "修改用户信息", description = "修改用户信息", tags = {ApiVersion.V_1_0_0})
    @ApiResponses({
            @ApiResponse(description = "success",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = UserInfoVO.class))),
            @ApiResponse(description = UserErrorMessageDesc.USER_NOT_EXIST_DESC,
                    responseCode = UserErrorMessageDesc.USER_NOT_EXIST_CODE,
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Parameters({
            @Parameter(name = "userNo", description = "用户编码"),
    })
    ResponseEntity<UserInfoVO> updateUser(String userNo, UserSaveDTO userSaveDTO);


    /**
     * prometheus监控
     *
     * @return
     */
    @Operation(summary = "prometheus监控", description = "prometheus监控", tags = {ApiVersion.V_1_0_0})
    @ApiResponses({
            @ApiResponse(description = "success",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = MonitorVO.class))),
    })
    ResponseEntity<MonitorVO> prometheusCustomMonitor();
}
