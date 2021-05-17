package com.coral.database.test.jpa.secondary.repository;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.jpa.secondary.entity.SecTest;
import org.springframework.stereotype.Repository;

/**
 * @author huss
 */
@Repository
public interface SecTestRepository extends JpaBaseRepository<SecTest, Long> {
}
