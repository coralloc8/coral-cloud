package com.coral.database.test.jpa.primary.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.jpa.primary.entity.SysFileRelation;
import com.coral.database.test.jpa.primary.enums.file.FileModuleEnum;

/**
 * @author huss
 */
@Repository
public interface SysFileRelationRepository extends JpaBaseRepository<SysFileRelation, Long> {

    /**
     * 根据module和infoNo删除
     * 
     * @param fileModule
     * @param infoNo
     */
    void deleteByFileModuleAndInfoNo(FileModuleEnum fileModule, String infoNo);

    /**
     * 根据module和infoNo查找
     * 
     * @param fileModule
     * @param infoNo
     * @return
     */
    SysFileRelation findByFileModuleAndInfoNo(FileModuleEnum fileModule, String infoNo);

    /**
     * 多个module和一个infoNo查找
     * 
     * @param fileModuleEnumList
     * @param infoNo
     * @return
     */
    List<SysFileRelation> findByFileModuleInAndInfoNo(List<FileModuleEnum> fileModuleEnumList, String infoNo);

    /**
     * 多个module和infoNo查找
     * 
     * @param fileModuleEnumList
     * @param infoNos
     * @return
     */
    List<SysFileRelation> findByFileModuleInAndInfoNoIn(List<FileModuleEnum> fileModuleEnumList, List<String> infoNos);
}
