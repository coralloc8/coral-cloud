package com.example.spring.web.test.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.spring.database.test.enums.file.FileModuleEnum;
import com.example.spring.web.test.dto.FileRelationSaveDTO;
import com.example.spring.web.test.dto.FileSaveDTO;
import com.example.spring.web.test.vo.response.FileAndInfoIdResVO;
import com.example.spring.web.test.vo.response.FileByFileIdVO;
import com.example.spring.web.test.vo.response.FileListResVO;
import com.example.spring.web.test.vo.response.FileResVO;

public interface IFileService {

    FileResVO saveFile(FileSaveDTO fileSaveDTO);

    void deleteFile(Set<Long> fileIds);

    void saveFileRelations(List<FileRelationSaveDTO> relationSaveDTOList);

    void deleteFileRelations(FileModuleEnum fileModuleEnum, Long infoId);

    Optional<FileResVO> findByFileModuleAndInfoId(FileModuleEnum fileModuleEnum, Long infoId);

    List<FileResVO> findByFileModuleInAndInfoId(List<FileModuleEnum> fileModuleEnums, Long infoId);

    List<FileListResVO> findByFileModuleInAndInfoIdIn(List<FileModuleEnum> fileModuleEnums, List<Long> infoIds);

    List<FileByFileIdVO> findByModuleAndInfoIds(FileModuleEnum fileModuleEnum, List<Long> infoIds);

    List<FileAndInfoIdResVO> findByModulesAndInfoIds(List<FileModuleEnum> fileModuleEnums, List<Long> infoIds);

}
