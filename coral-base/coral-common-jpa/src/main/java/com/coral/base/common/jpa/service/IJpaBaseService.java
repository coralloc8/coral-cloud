package com.coral.base.common.jpa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Predicate;

/**
 * @author huss
 */
public interface IJpaBaseService<T, ID> {

    boolean save(T t);

    boolean update(T t);

    boolean deleteById(ID id);

    boolean delete(T t);

    Optional<T> findOne(Predicate predicate);

    Optional<T> findById(ID id);

    List<T> findAll(Predicate predicate);

    List<T> findAll(Predicate predicate, Sort sort);

    Page<T> findAll(Predicate predicate, Pageable pageable);

}
