package com.coral.database.test.repository.dsl;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.coral.database.test.dto.FileInfoDTO;
import com.coral.database.test.entity.QSysFile;
import com.coral.database.test.entity.QSysFileRelation;
import com.coral.database.test.entity.SysFile;
import com.coral.database.test.enums.file.FileModuleEnum;
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
        super(SysFile.class);
    }

    @Override
    public FileInfoDTO findFilesByModuleAndInfoNo(FileModuleEnum fileModule, String infoNo) {
        FileInfoDTO fileInfoDTO = from(QSysFile.sysFile).leftJoin(QSysFileRelation.sysFileRelation)
            .on(QSysFile.sysFile.no.eq(QSysFileRelation.sysFileRelation.fileNo)).select(Projections.bean(
            //@formatter:off
                FileInfoDTO.class,
                QSysFile.sysFile.saveType,
                QSysFile.sysFile.relativePath,
                QSysFile.sysFile.no,
                QSysFile.sysFile.remark,
                QSysFile.sysFile.md5,
                QSysFileRelation.sysFileRelation.fileModule,
                QSysFileRelation.sysFileRelation.infoNo
            //@formatter:on
            )).where(QSysFileRelation.sysFileRelation.fileModule.eq(fileModule)
                .and(QSysFileRelation.sysFileRelation.infoNo.eq(infoNo)))
            .fetchOne();

        return fileInfoDTO;
    }

    @Override
    public List<FileInfoDTO> findFilesByModuleInAndInfoNoIn(Set<FileModuleEnum> modules, Set<String> infoNos) {
        List<FileInfoDTO> fileInfoDTOs = from(QSysFile.sysFile).leftJoin(QSysFileRelation.sysFileRelation)
            .on(QSysFile.sysFile.no.eq(QSysFileRelation.sysFileRelation.fileNo)).select(Projections.bean(
            //@formatter:off
                FileInfoDTO.class,
                QSysFile.sysFile.saveType,
                QSysFile.sysFile.relativePath,
                QSysFile.sysFile.no.as(FileInfoDTO.FILE_NO_ALIAS),
                QSysFile.sysFile.remark,
                QSysFile.sysFile.md5,
                QSysFileRelation.sysFileRelation.fileModule,
                QSysFileRelation.sysFileRelation.infoNo
            //@formatter:on
            )).where(QSysFileRelation.sysFileRelation.fileModule.in(modules)
                .and(QSysFileRelation.sysFileRelation.infoNo.in(infoNos)))
            .fetch();

        return fileInfoDTOs;
    }
}
