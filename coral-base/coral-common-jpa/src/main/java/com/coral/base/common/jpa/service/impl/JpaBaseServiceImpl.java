package com.coral.base.common.jpa.service.impl;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.base.common.jpa.service.IJpaBaseService;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author huss
 */
@Slf4j
public class JpaBaseServiceImpl<T, ID extends Serializable, R extends JpaBaseRepository<T, ID>>
        implements IJpaBaseService<T, ID> {

    @Autowired
    private R repository;

    protected R getRepository() {
        return repository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(T t) {
        try {
            t = repository.saveAndFlush(t);
            return t != null;
        } catch (Exception e) {
            log.error(">>>>>e:", e);
        }
        return false;

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(T t) {
        try {
            t = repository.saveAndFlush(t);
            return t != null;
        } catch (Exception e) {
            log.error(">>>>>e:", e);
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteById(ID id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error(">>>>>e:", e);
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(T t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            log.error(">>>>>e:", e);
        }
        return false;
    }

    @Override
    public Optional<T> findOne(Predicate predicate) {
        return repository.findOne(predicate);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll(Predicate predicate) {
        if (Objects.isNull(predicate)) {
            return repository.findAll();
        }
        return (List<T>) repository.findAll(predicate);
    }

    @Override
    public List<T> findAll(Predicate predicate, Sort sort) {
        if (Objects.isNull(predicate)) {
            return repository.findAll(sort);
        }
        return (List<T>) repository.findAll(predicate, sort);
    }

    @Override
    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        if (Objects.isNull(predicate)) {
            return repository.findAll(pageable);
        }
        return repository.findAll(predicate, pageable);
    }
}