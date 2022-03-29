package com.coral.test.opendoc.modules.admin.controller;

import com.coral.test.opendoc.common.enums.GlobalStatus;
import com.coral.test.opendoc.modules.admin.bo.UserQueryBO;
import com.coral.test.opendoc.modules.admin.bo.UserSaveBO;
import com.coral.test.opendoc.modules.admin.vo.PageInfoVO;
import com.coral.test.opendoc.modules.admin.vo.UserInfoVO;
import com.coral.test.opendoc.util.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author huss
 * @version 1.0
 * @className UserController
 * @description 用户管理
 * @date 2021/9/14 15:56
 */
@Tags
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Operation(summary = "分页查询用户列表", description = "分页查询用户列表", tags = "1.4.7")
    @GetMapping
    public Result<PageInfoVO<UserInfoVO>> page(@ParameterObject UserQueryBO queryBO) {
        List<UserInfoVO> infos = new ArrayList<>();
        UserInfoVO info = UserInfoVO.builder()
                .no(UUID.randomUUID().toString())
                .username("test_" + System.currentTimeMillis())
                .createTime(LocalDateTime.now().minusMinutes(40))
                .status(GlobalStatus.DISABLED)
                .build();
        infos.add(info);

        PageInfoVO.PageInfoVOBuilder<UserInfoVO> builder = PageInfoVO.builder();
        PageInfoVO<UserInfoVO> page = builder.pageSize(queryBO.getPageSize())
                .pageNum(queryBO.getPageNum())
                .size(1000)
                .total(100000L)
                .hasNextPage(true)
                .records(infos)
                .build();

        return new Result<PageInfoVO<UserInfoVO>>().success(page);
    }

    @Operation(summary = "根据id查询用户详细信息", description = "根据id查询用户详细信息", tags = {"1.4.7", "1.5.0"})
    @GetMapping("/{id}")
    @Parameters(
            @Parameter(name = "id", description = "医生ID")
    )
    public Result<UserInfoVO> one(@PathVariable Long id) {
        UserInfoVO info = UserInfoVO.builder()
                .no(UUID.randomUUID().toString())
                .username("test_" + System.currentTimeMillis())
                .createTime(LocalDateTime.now())
                .status(GlobalStatus.NORMAL)
                .build();
        Optional<UserInfoVO> opt = Optional.of(info);
        if (opt.isPresent()) {
            return new Result<UserInfoVO>().success(opt.get());
        }
        return new Result<UserInfoVO>().success();
    }


    @Operation(summary = "保存用户信息", description = "保存用户信息", tags = {"1.5.0"})
    @PostMapping
    public Result<UserInfoVO> saveUser(@RequestBody UserSaveBO userSaveBO) {
        log.info(">>>>>用户信息:{}", userSaveBO);
        return new Result<UserInfoVO>().success();
    }

}
