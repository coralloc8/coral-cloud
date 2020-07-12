package com.example.spring.database.test.repository;

import java.util.List;

import com.example.spring.common.jpa.repository.JpaBaseRepository;
import com.example.spring.database.test.entity.SysFile;
import com.example.spring.database.test.repository.dsl.SysFileDslRepository;

/**
 * @author huss
 */

public interface SysFileRepository extends JpaBaseRepository<SysFile, Long>, SysFileDslRepository {

    SysFile findByNo(String no);

    List<SysFile> findByNoIn(List<String> nos);

}
