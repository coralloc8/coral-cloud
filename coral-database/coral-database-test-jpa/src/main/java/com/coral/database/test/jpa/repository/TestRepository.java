package com.coral.database.test.jpa.repository;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.jpa.entity.Test;
import org.springframework.stereotype.Repository;

/**
 * @author huss
 */
@Repository
public interface TestRepository extends JpaBaseRepository<Test, Long> {
}
