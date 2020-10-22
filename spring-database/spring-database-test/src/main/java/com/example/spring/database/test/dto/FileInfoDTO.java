package com.example.spring.database.test.dto;

import com.example.spring.database.test.enums.file.FileModuleEnum;
import com.example.spring.database.test.enums.file.FileSaveTypeEnum;

import lombok.*;

/**
 * @description: TODO
 * @author: huss
 * @time: 2020/6/22 16:13
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FileInfoDTO {

    public static final String FILE_NO_ALIAS = "fileNo";

    private FileSaveTypeEnum saveType;

    private String relativePath;

    private String fileNo;

    private String remark;

    private String md5;

    private FileModuleEnum fileModule;

    private String infoNo;

}
