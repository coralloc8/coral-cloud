package com.coral.service.file.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.coral.service.file.dto.FileRelationSaveDTO;
import com.coral.service.file.dto.FileSaveDTO;
import com.coral.service.file.vo.FileAndInfoIdResVO;
import com.coral.service.file.vo.FileByFileIdVO;
import com.coral.service.file.vo.FileListResVO;
import com.coral.service.file.vo.FileResVO;
import com.coral.database.test.jpa.enums.file.FileModuleEnum;

/**
 * @author huss
 */
public interface IFileService {

    /**
     * 保存文件
     * 
     * @param fileSaveDTO
     * @return
     */
    FileResVO saveFile(FileSaveDTO fileSaveDTO);

    /**
     * 删除文件
     * 
     * @param fileNos
     */
    void deleteFile(Set<String> fileNos);

    /**
     * 保存文件映射关系
     * 
     * @param relationSaveDTOList
     */
    void saveFileRelations(List<FileRelationSaveDTO> relationSaveDTOList);

    /**
     * 删除文件映射关系
     * 
     * @param fileModuleEnum
     * @param infoNo
     */
    void deleteFileRelations(FileModuleEnum fileModuleEnum, String infoNo);

    /**
     * 根据filemodule和infoNo查找文件
     * 
     * @param fileModuleEnum
     * @param infoNo
     * @return
     */
    Optional<FileResVO> findByFileModuleAndInfoNo(FileModuleEnum fileModuleEnum, String infoNo);

    /**
     * 根据filemodule和infoNo查找文件
     * 
     * @param fileModuleEnums
     * @param infoNo
     * @return
     */
    List<FileResVO> findByFileModuleInAndInfoNo(List<FileModuleEnum> fileModuleEnums, String infoNo);

    /**
     * 根据filemodule和infoNo查找文件
     * 
     * @param fileModuleEnums
     * @param infoNos
     * @return
     */
    List<FileListResVO> findByFileModuleInAndInfoNoIn(List<FileModuleEnum> fileModuleEnums, List<String> infoNos);

    /**
     * 根据filemodule和infoNo查找文件
     * 
     * @param fileModuleEnum
     * @param infoNos
     * @return
     */
    List<FileByFileIdVO> findByModuleAndInfoNos(FileModuleEnum fileModuleEnum, List<String> infoNos);

    /**
     * 根据filemodule和infoNo查找文件
     * 
     * @param fileModuleEnums
     * @param infoNos
     * @return
     */
    List<FileAndInfoIdResVO> findByModulesAndInfoNos(List<FileModuleEnum> fileModuleEnums, List<String> infoNos);

}