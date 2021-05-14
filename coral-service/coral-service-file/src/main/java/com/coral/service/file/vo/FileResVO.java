package com.coral.service.file.vo;

import javax.persistence.Convert;

import com.coral.database.test.jpa.enums.file.FileModuleEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResVO {

    private String fileNo;

    private String url;

    @Convert(converter = FileModuleEnum.Convert.class)
    private FileModuleEnum fileModule;

    private String md5;
}
