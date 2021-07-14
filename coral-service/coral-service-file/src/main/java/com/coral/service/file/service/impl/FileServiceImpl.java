package com.coral.service.file.service.impl;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.coral.service.file.dto.FileRelationSaveDTO;
import com.coral.service.file.dto.FileSaveDTO;
import com.coral.service.file.vo.FileAndInfoIdResVO;
import com.coral.service.file.vo.FileByFileIdVO;
import com.coral.service.file.vo.FileListResVO;
import com.coral.service.file.vo.FileResVO;
import com.coral.service.core.support.NumberCreatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coral.base.common.CollectionUtil;
import com.coral.base.common.FileUtil;
import com.coral.base.common.jpa.enums.GlobalDeletedEnum;
import com.coral.database.test.jpa.primary.dto.FileInfoDTO;
import com.coral.database.test.jpa.primary.entity.SysFile;
import com.coral.database.test.jpa.primary.entity.SysFileRelation;
import com.coral.database.test.jpa.primary.enums.file.FileModuleEnum;
import com.coral.database.test.jpa.primary.enums.file.FileSaveTypeEnum;
import com.coral.database.test.jpa.primary.repository.SysFileRelationRepository;
import com.coral.database.test.jpa.primary.repository.SysFileRepository;
import com.coral.service.file.config.UploadProperty;
import com.coral.service.file.service.IFileService;

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

    @Autowired
    private NumberCreatorFactory numberCreatorFactory;

    @Override
    public FileResVO saveFile(FileSaveDTO fileSaveDTO) {
        String savePath = uploadProperty.getSavePath();
        FileUtil.FilePath filePath = FileUtil.writeFile((FileInputStream)fileSaveDTO.getInputStream(),
            fileSaveDTO.getFileName(), Paths.get(savePath));
        String relativePath = filePath.getRelativePath();

        SysFile sysFile = new SysFile();
        sysFile.setNo(numberCreatorFactory.createUniqueNo().toString());
        sysFile.setRelativePath(relativePath);
        sysFile.setRemark(fileSaveDTO.getRemark());
        sysFile.setSaveType(FileSaveTypeEnum.LOCAL_DISK);
        sysFile.setMd5(filePath.getMd5());
        fileRepository.save(sysFile);

        return new FileResVO(sysFile.getNo(), uploadProperty.getNetworkFullPath(relativePath), null, sysFile.getMd5());
    }

    @Override
    public void deleteFile(Set<String> fileNos) {
        for (String no : fileNos) {
            SysFile sysFile = fileRepository.findByNo(no);
            if (sysFile != null) {
                sysFile.setDeleted(GlobalDeletedEnum.DELETED);
                fileRepository.save(sysFile);
            }
        }
    }

    @Override
    public void saveFileRelations(List<FileRelationSaveDTO> relationSaveDTOList) {
        relationSaveDTOList.stream().map(e -> {
            SysFileRelation fileRe = new SysFileRelation();
            fileRe.setFileNo(e.getFileNo());
            fileRe.setFileModule(e.getFileModule());
            fileRe.setInfoNo(e.getInfoNo());
            return fileRe;
        }).forEach(e -> fileRelationRepository.save(e));
    }

    @Override
    public void deleteFileRelations(FileModuleEnum fileModuleEnum, String infoNo) {
        fileRelationRepository.deleteByFileModuleAndInfoNo(fileModuleEnum, infoNo);

    }

    @Override
    public Optional<FileResVO> findByFileModuleAndInfoNo(FileModuleEnum fileModuleEnum, String infoNo) {
        List<FileResVO> fileResVOS = this.findByFileModuleInAndInfoNo(Arrays.asList(fileModuleEnum), infoNo);
        if (fileResVOS == null || fileResVOS.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(fileResVOS.get(0));
    }

    @Override
    public List<FileResVO> findByFileModuleInAndInfoNo(List<FileModuleEnum> fileModuleEnums, String infoNo) {
        // 此处采用dsl查询的方式实现
        List<FileInfoDTO> fileInfoDTOS = fileRepository.findFilesByModuleInAndInfoNoIn(
            CollectionUtil.newHashSet(fileModuleEnums), CollectionUtil.newHashSet(infoNo));
        if (fileInfoDTOS == null || fileInfoDTOS.isEmpty()) {
            return Collections.emptyList();
        }

        return fileInfoDTOS.stream().map(e -> new FileResVO(e.getFileNo(),
            uploadProperty.getNetworkFullPath(e.getRelativePath()), e.getFileModule(), e.getMd5()))
            .collect(Collectors.toList());
    }

    @Override
    public List<FileListResVO> findByFileModuleInAndInfoNoIn(List<FileModuleEnum> fileModuleEnums,
                                                             List<String> infoNos) {
        List<SysFileRelation> sysFileRelations =
            fileRelationRepository.findByFileModuleInAndInfoNoIn(fileModuleEnums, infoNos);
        if (sysFileRelations == null || sysFileRelations.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> fileNos =
            sysFileRelations.stream().map(SysFileRelation::getFileNo).distinct().collect(Collectors.toList());
        List<SysFile> files = fileRepository.findByNoIn(fileNos);

        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, List<SysFile>> fileMap = files.stream().collect(Collectors.groupingBy(SysFile::getNo));

        List<FileListResVO> list = sysFileRelations.stream().collect(Collectors.groupingBy(SysFileRelation::getInfoNo))
            .entrySet().stream().map(e -> {
                FileListResVO resVo = new FileListResVO();

                resVo.setInfoNo(e.getKey());

                List<FileResVO> resVos = e.getValue().stream().map(v -> {
                    String relativePath = "", md5 = "";
                    if (fileMap.containsKey(v.getFileNo())) {
                        SysFile sysFile = fileMap.get(v.getFileNo()).get(0);
                        relativePath = sysFile.getRelativePath();
                        md5 = sysFile.getMd5();
                    }

                    FileModuleEnum fileModuleEnum = v.getFileModule();
                    return new FileResVO(v.getFileNo(), uploadProperty.getNetworkFullPath(relativePath), fileModuleEnum,
                        md5);
                }).collect(Collectors.toList());

                resVo.setFiles(resVos);

                return resVo;

            }).collect(Collectors.toList());

        return list;
    }

    @Override
    public List<FileByFileIdVO> findByModuleAndInfoNos(FileModuleEnum fileModuleEnum, List<String> infoNos) {
        List<SysFileRelation> sysFileRelations =
            fileRelationRepository.findByFileModuleInAndInfoNoIn(Arrays.asList(fileModuleEnum), infoNos);
        if (CollectionUtil.isBlank(sysFileRelations)) {
            return Collections.emptyList();
        }
        List<String> fileNos =
            sysFileRelations.stream().map(SysFileRelation::getFileNo).distinct().collect(Collectors.toList());
        List<SysFile> files = fileRepository.findByNoIn(fileNos);

        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, List<SysFile>> fileMap = files.stream().collect(Collectors.groupingBy(SysFile::getNo));

        List<FileByFileIdVO> list = sysFileRelations.stream().collect(Collectors.groupingBy(SysFileRelation::getFileNo))
            .entrySet().stream().filter(e -> fileMap.containsKey(e.getKey())).map(e -> {
                SysFile sysFile = fileMap.get(e.getKey()).get(0);

                FileResVO fileResVo = new FileResVO(sysFile.getNo(),
                    uploadProperty.getNetworkFullPath(sysFile.getRelativePath()), fileModuleEnum, sysFile.getMd5());

                List<String> infoNoList =
                    e.getValue().stream().map(SysFileRelation::getInfoNo).collect(Collectors.toList());

                return new FileByFileIdVO(fileResVo, infoNoList);
            }).collect(Collectors.toList());

        return list;

    }

    @Override
    public List<FileAndInfoIdResVO> findByModulesAndInfoNos(List<FileModuleEnum> fileModuleEnums,
                                                            List<String> infoNos) {
        List<SysFileRelation> sysFileRelations =
            fileRelationRepository.findByFileModuleInAndInfoNoIn(fileModuleEnums, infoNos);
        if (sysFileRelations == null || sysFileRelations.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> fileNos =
            sysFileRelations.stream().map(SysFileRelation::getFileNo).distinct().collect(Collectors.toList());

        List<SysFile> files = fileRepository.findByNoIn(fileNos);

        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, List<SysFile>> fileMap = files.stream().collect(Collectors.groupingBy(SysFile::getNo));

        List<FileAndInfoIdResVO> list = sysFileRelations.stream().map(e -> {
            String relativePath = "", md5 = "";
            if (fileMap.containsKey(e.getFileNo())) {
                SysFile sysFile = fileMap.get(e.getFileNo()).get(0);
                relativePath = sysFile.getRelativePath();
                md5 = sysFile.getMd5();
            }
            FileModuleEnum fileModuleEnum = e.getFileModule();
            return new FileAndInfoIdResVO(e.getFileNo(), uploadProperty.getNetworkFullPath(relativePath),
                fileModuleEnum, e.getInfoNo(), md5);
        }).collect(Collectors.toList());
        return list;
    }
}