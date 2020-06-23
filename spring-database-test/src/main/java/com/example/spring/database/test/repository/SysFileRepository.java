package com.example.spring.database.test.repository;

import com.example.spring.common.jpa.repository.JpaBaseRepository;
import com.example.spring.database.test.entity.SysFile;
import com.example.spring.database.test.repository.dsl.SysFileDslRepository;

/**
 * @author huss
 */

public interface SysFileRepository extends JpaBaseRepository<SysFile, Long>, SysFileDslRepository {

}
