package com.coral.database.test.jpa.primary.repository;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.jpa.primary.entity.Test;
import org.springframework.stereotype.Repository;

/**
 * @author huss
 */
@Repository
public interface TestRepository extends JpaBaseRepository<Test, Long> {
}
