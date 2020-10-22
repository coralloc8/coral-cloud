package com.example.spring.common.jpa.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring.common.jpa.service.IJpaBaseService;
import com.querydsl.core.types.Predicate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Slf4j
public class JpaBaseServiceImpl<T, ID extends Serializable,
    Repository extends org.springframework.data.jpa.repository.JpaRepository<T,
        ID> & org.springframework.data.querydsl.QuerydslPredicateExecutor<T>>
    implements IJpaBaseService<T, ID> {

    @Autowired(required = true)
    private Repository repository;

    protected Repository getRepository() {
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
        return (List<T>)repository.findAll(predicate);
    }

    @Override
    public List<T> findAll(Predicate predicate, Sort sort) {
        return (List<T>)repository.findAll(predicate, sort);
    }

    @Override
    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }
}