package com.coral.database.test.repository;

import com.coral.database.test.entity.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author huss
 */
@Repository
public interface TestRepository extends CrudRepository<Test, Long> {}
