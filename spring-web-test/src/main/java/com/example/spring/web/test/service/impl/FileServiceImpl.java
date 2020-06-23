package com.example.spring.web.test.service.impl;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.common.CollectionUtil;
import com.example.spring.common.FileUtil;
import com.example.spring.common.jpa.enums.GlobalDeletedEnum;
import com.example.spring.database.test.dto.FileInfoDTO;
import com.example.spring.database.test.entity.SysFile;
import com.example.spring.database.test.entity.SysFileRelation;
import com.example.spring.database.test.enums.file.FileModuleEnum;
import com.example.spring.database.test.enums.file.FileSaveTypeEnum;
import com.example.spring.database.test.repository.SysFileRelationRepository;
import com.example.spring.database.test.repository.SysFileRepository;
import com.example.spring.web.test.config.UploadProperty;
import com.example.spring.web.test.dto.FileRelationSaveDTO;
import com.example.spring.web.test.dto.FileSaveDTO;
import com.example.spring.web.test.service.IFileService;
import com.example.spring.web.test.vo.response.FileAndInfoIdResVO;
import com.example.spring.web.test.vo.response.FileByFileIdVO;
import com.example.spring.web.test.vo.response.FileListResVO;
import com.example.spring.web.test.vo.response.FileResVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Service
@Slf4j
public class FileServiceImpl implements IFileService {

    @Autowired
    private SysFileRepository fileRepository;

    @Autowired
    private SysFileRelationRepository fileRelationRepository;

    @Autowired
    private UploadProperty uploadProperty;

    @Override
    public FileResVO saveFile(FileSaveDTO fileSaveDTO) {
        String savePath = uploadProperty.getSavePath();
        FileUtil.FilePath filePath = FileUtil.writeFile((FileInputStream)fileSaveDTO.getInputStream(),
            fileSaveDTO.getFileName(), Paths.get(savePath));
        String relativePath = filePath.getRelativePath();

        SysFile sysFile = new SysFile();
        sysFile.setRelativePath(relativePath);
        sysFile.setRemark(fileSaveDTO.getRemark());
        sysFile.setSaveType(FileSaveTypeEnum.LOCAL_DISK);
        fileRepository.save(sysFile);

        return new FileResVO(sysFile.getId(), uploadProperty.getNetworkFullPath(relativePath), null);
    }

    @Override
    public void deleteFile(Set<Long> fileIds) {
        for (Long fileId : fileIds) {
            Optional<SysFile> sysFileOptional = fileRepository.findById(fileId);
            if (sysFileOptional.isPresent()) {
                SysFile sysFile = sysFileOptional.get();
                sysFile.setDeleted(GlobalDeletedEnum.YES);
                fileRepository.save(sysFile);
            }
        }
    }

    @Override
    public void saveFileRelations(List<FileRelationSaveDTO> relationSaveDTOList) {
        relationSaveDTOList.stream().map(e -> {
            SysFileRelation fileRe = new SysFileRelation();
            fileRe.setFileId(e.getFileId());
            fileRe.setFileModule(e.getFileModule());
            fileRe.setInfoId(e.getInfoId());
            return fileRe;
        }).forEach(e -> fileRelationRepository.save(e));
    }

    @Override
    public void deleteFileRelations(FileModuleEnum fileModuleEnum, Long infoId) {
        fileRelationRepository.deleteByFileModuleAndInfoId(fileModuleEnum, infoId);

    }

    @Override
    public Optional<FileResVO> findByFileModuleAndInfoId(FileModuleEnum fileModuleEnum, Long infoId) {
        List<FileResVO> fileResVOS = this.findByFileModuleInAndInfoId(Arrays.asList(fileModuleEnum), infoId);
        if (fileResVOS == null || fileResVOS.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(fileResVOS.get(0));
    }

    @Override
    public List<FileResVO> findByFileModuleInAndInfoId(List<FileModuleEnum> fileModuleEnums, Long infoId) {
        // 此处采用dsl查询的方式实现
        List<FileInfoDTO> fileInfoDTOS = fileRepository.findFilesByModuleInAndInfoIdIn(
            CollectionUtil.newHashSet(fileModuleEnums), CollectionUtil.newHashSet(infoId));
        if (fileInfoDTOS == null || fileInfoDTOS.isEmpty()) {
            return Collections.emptyList();
        }

        return fileInfoDTOS.stream().map(e -> new FileResVO(e.getFileId(),
            uploadProperty.getNetworkFullPath(e.getRelativePath()), e.getFileModule())).collect(Collectors.toList());
    }

    @Override
    public List<FileListResVO> findByFileModuleInAndInfoIdIn(List<FileModuleEnum> fileModuleEnums, List<Long> infoIds) {
        List<SysFileRelation> sysFileRelations =
            fileRelationRepository.findByFileModuleInAndInfoIdIn(fileModuleEnums, infoIds);
        if (sysFileRelations == null || sysFileRelations.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> fileIds =
            sysFileRelations.stream().map(SysFileRelation::getFileId).distinct().collect(Collectors.toList());
        List<SysFile> files = fileRepository.findAllById(fileIds);

        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, List<SysFile>> fileMap = files.stream().collect(Collectors.groupingBy(SysFile::getId));

        List<FileListResVO> list = sysFileRelations.stream().collect(Collectors.groupingBy(SysFileRelation::getInfoId))
            .entrySet().stream().map(e -> {
                FileListResVO resVo = new FileListResVO();

                resVo.setInfoId(e.getKey());

                List<FileResVO> resVos = e.getValue().stream().map(v -> {
                    String relativePath =
                        fileMap.containsKey(v.getFileId()) ? fileMap.get(v.getFileId()).get(0).getRelativePath() : "";

                    FileModuleEnum fileModuleEnum = v.getFileModule();
                    return new FileResVO(v.getFileId(), uploadProperty.getNetworkFullPath(relativePath),
                        fileModuleEnum);
                }).collect(Collectors.toList());

                resVo.setFiles(resVos);

                return resVo;

            }).collect(Collectors.toList());

        return list;
    }

    @Override
    public List<FileByFileIdVO> findByModuleAndInfoIds(FileModuleEnum fileModuleEnum, List<Long> infoIds) {
        List<SysFileRelation> sysFileRelations =
            fileRelationRepository.findByFileModuleInAndInfoIdIn(Arrays.asList(fileModuleEnum), infoIds);
        if (CollectionUtil.isBlank(sysFileRelations)) {
            return Collections.emptyList();
        }
        List<Long> fileIds =
            sysFileRelations.stream().map(SysFileRelation::getFileId).distinct().collect(Collectors.toList());
        List<SysFile> files = fileRepository.findAllById(fileIds);

        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, List<SysFile>> fileMap = files.stream().collect(Collectors.groupingBy(SysFile::getId));

        List<FileByFileIdVO> list = sysFileRelations.stream().collect(Collectors.groupingBy(SysFileRelation::getFileId))
            .entrySet().stream().filter(e -> fileMap.containsKey(e.getKey())).map(e -> {
                SysFile sysFile = fileMap.get(e.getKey()).get(0);

                FileResVO fileResVo = new FileResVO(sysFile.getId(),
                    uploadProperty.getNetworkFullPath(sysFile.getRelativePath()), fileModuleEnum);

                List<Long> infoIdList =
                    e.getValue().stream().map(SysFileRelation::getInfoId).collect(Collectors.toList());

                return new FileByFileIdVO(fileResVo, infoIdList);
            }).collect(Collectors.toList());

        return list;

    }

    @Override
    public List<FileAndInfoIdResVO> findByModulesAndInfoIds(List<FileModuleEnum> fileModuleEnums, List<Long> infoIds) {
        List<SysFileRelation> sysFileRelations =
            fileRelationRepository.findByFileModuleInAndInfoIdIn(fileModuleEnums, infoIds);
        if (sysFileRelations == null || sysFileRelations.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> fileIds =
            sysFileRelations.stream().map(SysFileRelation::getFileId).distinct().collect(Collectors.toList());

        List<SysFile> files = fileRepository.findAllById(fileIds);

        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, List<SysFile>> fileMap = files.stream().collect(Collectors.groupingBy(SysFile::getId));

        List<FileAndInfoIdResVO> list = sysFileRelations.stream().map(e -> {
            String relativePath =
                fileMap.containsKey(e.getFileId()) ? fileMap.get(e.getFileId()).get(0).getRelativePath() : "";

            FileModuleEnum fileModuleEnum = e.getFileModule();
            return new FileAndInfoIdResVO(e.getFileId(), uploadProperty.getNetworkFullPath(relativePath),
                fileModuleEnum, e.getInfoId());
        }).collect(Collectors.toList());
        return list;
    }
}
