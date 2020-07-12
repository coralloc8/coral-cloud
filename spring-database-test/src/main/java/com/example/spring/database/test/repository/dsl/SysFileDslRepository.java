package com.example.spring.database.test.repository.dsl;

import java.util.List;
import java.util.Set;

import com.example.spring.database.test.dto.FileInfoDTO;
import com.example.spring.database.test.enums.file.FileModuleEnum;

/**
 * @description: 举例用于复杂查询 file表和file_relation表
 * @author: huss
 * @time: 2020/6/22 16:03
 */
public interface SysFileDslRepository {

    /**
     * 根据module和info查询文件
     * 
     * @param fileModule
     * @param infoNo
     * @return
     */
    FileInfoDTO findFilesByModuleAndInfoNo(FileModuleEnum fileModule, String infoNo);

    /**
     * 根据module和info查询文件
     * @param modules
     * @param infoNos
     * @return
     */
    List<FileInfoDTO> findFilesByModuleInAndInfoNoIn(Set<FileModuleEnum> modules, Set<String> infoNos);

}
