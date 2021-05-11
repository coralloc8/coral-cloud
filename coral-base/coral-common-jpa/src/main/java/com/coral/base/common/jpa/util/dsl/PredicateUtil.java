package com.coral.base.common.jpa.util.dsl;

import java.util.List;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;

/**
 * @author huss
 * @description:
 * @date: 2018/9/28 14:26
 */
public class PredicateUtil {

    public static Predicate link(List<Predicate> predicates) {
        Predicate predicate = null;
        if (predicates == null || predicates.size() == 0) {
            return null;
        }

        for (Predicate pre : predicates) {
            predicate = ExpressionUtils.and(predicate, pre);
        }
        return predicate;
    }

    public static <T> String getDslElement(Path<T> path) {
        return path.getMetadata().getElement().toString();
    }

}
