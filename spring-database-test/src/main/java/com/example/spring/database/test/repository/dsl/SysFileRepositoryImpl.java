package com.example.spring.database.test.repository.dsl;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.example.spring.database.test.dto.FileInfoDTO;
import com.example.spring.database.test.entity.QSysFile;
import com.example.spring.database.test.entity.QSysFileRelation;
import com.example.spring.database.test.enums.file.FileModuleEnum;
import com.querydsl.core.types.Projections;

/**
 * @description: 查询file
 * @author: huss
 * @time: 2020/6/22 16:43
 */
@Repository
public class SysFileRepositoryImpl extends QuerydslRepositorySupport implements SysFileDslRepository {

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * must not be {@literal null}.
     */
    public SysFileRepositoryImpl() {
        super(FileInfoDTO.class);
    }

    @Override
    public FileInfoDTO findFilesByModuleAndInfoId(FileModuleEnum fileModule, Long infoId) {
        FileInfoDTO fileInfoDTO = from(QSysFile.sysFile).leftJoin(QSysFileRelation.sysFileRelation)
            .on(QSysFile.sysFile.id.eq(QSysFileRelation.sysFileRelation.fileId)).select(Projections.bean(
            //@formatter:off
                FileInfoDTO.class,
                QSysFile.sysFile.saveType,
                QSysFile.sysFile.relativePath,
                QSysFile.sysFile.id,
                QSysFile.sysFile.remark,
                QSysFile.sysFile.md5,
                QSysFileRelation.sysFileRelation.fileModule,
                QSysFileRelation.sysFileRelation.infoId
            //@formatter:on
            )).where(QSysFileRelation.sysFileRelation.fileModule.eq(fileModule)
                .and(QSysFileRelation.sysFileRelation.infoId.eq(infoId)))
            .fetchOne();

        return fileInfoDTO;
    }

    @Override
    public List<FileInfoDTO> findFilesByModuleInAndInfoIdIn(Set<FileModuleEnum> modules, Set<Long> infoIds) {
        List<FileInfoDTO> fileInfoDTOs = from(QSysFile.sysFile).leftJoin(QSysFileRelation.sysFileRelation)
            .on(QSysFile.sysFile.id.eq(QSysFileRelation.sysFileRelation.fileId)).select(Projections.bean(
            //@formatter:off
                FileInfoDTO.class,
                QSysFile.sysFile.saveType,
                QSysFile.sysFile.relativePath,
                QSysFile.sysFile.id,
                QSysFile.sysFile.remark,
                QSysFile.sysFile.md5,
                QSysFileRelation.sysFileRelation.fileModule,
                QSysFileRelation.sysFileRelation.infoId
            //@formatter:on
            )).where(QSysFileRelation.sysFileRelation.fileModule.in(modules)
                .and(QSysFileRelation.sysFileRelation.infoId.in(infoIds)))
            .fetch();

        return fileInfoDTOs;
    }
}
