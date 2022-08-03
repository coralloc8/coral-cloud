package com.coral.base.common.design.typeobj;

import lombok.Value;

/**
 * @author huss
 * @version 1.0
 * @className Breed
 * @description todo
 * @date 2022/8/2 10:27
 */

@Value(staticConstructor = "of")
public class Breed {

    /**
     * 种类
     */
    private String kind;

    /**
     * 血量
     */
    private int health;

    /**
     * 攻击力
     */
    private int attack;
}
