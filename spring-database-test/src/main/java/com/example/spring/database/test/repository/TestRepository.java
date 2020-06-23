package com.example.spring.database.test.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.spring.database.test.entity.Test;

/**
 * @author huss
 */
@Repository
public interface TestRepository extends CrudRepository<Test, Long> {}
