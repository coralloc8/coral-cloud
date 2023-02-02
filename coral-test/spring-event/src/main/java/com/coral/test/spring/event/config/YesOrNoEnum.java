package com.coral.test.spring.event.config;

import com.coral.base.common.StringUtils;
import com.coral.base.common.enums.IEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author huss
 * @version 1.0
 * @className YesOrNoEnum
 * @description todo
 * @date 2023/2/2 13:49
 */
@Slf4j
public enum YesOrNoEnum implements IEnum<YesOrNoEnum, String> {

    /**
     * 是
     */
    YES("y", "是"),
    /**
     * 否
     */
    NO("f", "否"),
    ;

    YesOrNoEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public static YesOrNoEnum codeOf(String code) {
        return Arrays.stream(YesOrNoEnum.values())
                .filter(e -> e.getCode().equals(code) ||
                        (StringUtils.isNotBlank(code) && Boolean.valueOf(code))
                ).findFirst().orElse(YesOrNoEnum.NO);
    }


    @Getter
    private String code;

    @Getter
    private String name;
}
