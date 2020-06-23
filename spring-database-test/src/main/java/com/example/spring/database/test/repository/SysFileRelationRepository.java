package com.example.spring.database.test.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.spring.common.jpa.repository.JpaBaseRepository;
import com.example.spring.database.test.entity.SysFileRelation;
import com.example.spring.database.test.enums.file.FileModuleEnum;

/**
 * @author huss
 */
@Repository
public interface SysFileRelationRepository extends JpaBaseRepository<SysFileRelation, Long> {

    /**
     * 根据module和infoId删除
     * 
     * @param fileModule
     * @param infoId
     */
    void deleteByFileModuleAndInfoId(FileModuleEnum fileModule, Long infoId);

    /**
     * 根据module和infoId查找
     * 
     * @param fileModule
     * @param infoId
     * @return
     */
    SysFileRelation findByFileModuleAndInfoId(FileModuleEnum fileModule, Long infoId);

    /**
     * 多个module和一个infoId查找
     * 
     * @param fileModuleEnumList
     * @param infoId
     * @return
     */
    List<SysFileRelation> findByFileModuleInAndInfoId(List<FileModuleEnum> fileModuleEnumList, Long infoId);

    /**
     * 多个module和infoId查找
     * 
     * @param fileModuleEnumList
     * @param infoIds
     * @return
     */
    List<SysFileRelation> findByFileModuleInAndInfoIdIn(List<FileModuleEnum> fileModuleEnumList, List<Long> infoIds);
}
