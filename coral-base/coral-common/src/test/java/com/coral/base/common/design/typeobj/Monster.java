package com.coral.base.common.design.typeobj;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author huss
 * @version 1.0
 * @className Monster
 * @description todo
 * @date 2022/8/2 10:18
 */
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Monster {
    private Breed breed;

    public Monster(Breed breed) {
        this.breed = breed;
    }

    /**
     * 种类
     */
    public String getKind() {
        return this.breed.getKind();
    }

    /**
     * 血量
     */
    private int getHealth() {
        return this.breed.getHealth();
    }

    /**
     * 攻击力
     */
    private int getAttack() {
        return this.breed.getAttack();
    }
}
