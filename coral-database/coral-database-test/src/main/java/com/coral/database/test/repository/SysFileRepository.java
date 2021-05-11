package com.coral.database.test.repository;

import java.util.List;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.entity.SysFile;
import com.coral.database.test.repository.dsl.SysFileDslRepository;

/**
 * @author huss
 */

public interface SysFileRepository extends JpaBaseRepository<SysFile, Long>, SysFileDslRepository {

    SysFile findByNo(String no);

    List<SysFile> findByNoIn(List<String> nos);

}
