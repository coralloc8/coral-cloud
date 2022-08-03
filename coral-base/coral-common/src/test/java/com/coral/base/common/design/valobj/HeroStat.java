package com.coral.base.common.design.valobj;

import lombok.AccessLevel;
import lombok.Value;
import lombok.With;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.NonFinal;

/**
 * @author huss
 * @version 1.0
 * @className HeroState
 * @description todo
 * @date 2022/8/2 9:12
 */
@FieldNameConstants
@Value(staticConstructor = "of")
public class HeroStat {

    @With(AccessLevel.PACKAGE)
    @NonFinal
    private int strength;

    private int intelligence;

    private int luck;
}
