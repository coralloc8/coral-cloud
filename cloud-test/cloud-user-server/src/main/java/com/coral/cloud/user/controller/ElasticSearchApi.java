package com.coral.cloud.user.controller;

import com.coral.cloud.user.common.constants.ApiVersion;
import com.coral.cloud.user.entity.PersonDocument;
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
 * @className ElasticSearchApi
 * @description ElasticSearch Api
 * @date 2022/4/28 11:10
 */
@Tags
public interface ElasticSearchApi {
    /**
     * 查询用户文档信息
     *
     * @param personName
     * @return
     */
    @Operation(summary = "查询用户文档信息", description = "查询用户文档信息", tags = {ApiVersion.V_1_0_1})
    @ApiResponses({
            @ApiResponse(description = "success", responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = PersonDocument.class))))
    })
    @Parameters({
            @Parameter(name = "personName", description = "用户姓名"),
    })
    ResponseEntity<List<PersonDocument>> findPersons(String personName);


    /**
     * 保存用户文档信息
     *
     * @param personDocument
     * @return
     */
    @Operation(summary = "保存用户文档信息", description = "保存用户文档信息", tags = {ApiVersion.V_1_0_1})
    @ApiResponses({
            @ApiResponse(description = "success", responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = PersonDocument.class)))
    })
    ResponseEntity<PersonDocument> savePerson(PersonDocument personDocument);


    /**
     * 初始化用户门诊记录信息
     *
     * @return
     */
    @Operation(summary = "初始化用户门诊记录信息", description = "初始化用户门诊记录信息", tags = {ApiVersion.V_1_0_1})
    @ApiResponses({
            @ApiResponse(description = "success", responseCode = "200")
    })
    ResponseEntity<String> initUserRecordMzs();

}
