package com.example.spring.common.jpa.util.dsl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

/**
 * @description: dsl predicat创建器
 * @author: huss
 * @time: 2020/7/14 16:00
 */
public class PredicateCreator {

    private List<Predicate> predicateList;

    public PredicateCreator() {
        this.predicateList = new ArrayList<>();
    }

    public static PredicateCreator builder() {
        return new PredicateCreator();
    }

    public PredicateCreator link(Predicate predicate) {
        this.predicateList.add(predicate);
        return this;
    }

    /**
     * 
     * @param predicate
     * @param canLink
     *            是否能link 能的话才会执行link
     * @return
     */
    public PredicateCreator link(boolean canLink, Predicate predicate) {
        if (canLink && !Objects.isNull(predicate)) {
            this.predicateList.add(predicate);
        }
        return this;
    }

    public Predicate build() {
        return ExpressionUtils.allOf(this.predicateList);
    }

}
