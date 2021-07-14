package com.coral.database.test.jpa.tertiary.repository;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.jpa.tertiary.entity.TerTest;
import org.springframework.stereotype.Repository;

/**
 * @author huss
 */
@Repository
public interface TerTestRepository extends JpaBaseRepository<TerTest, Long> {
}
