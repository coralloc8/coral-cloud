package com.example.spring.web.test.vo.response.file;

import javax.persistence.Convert;

import com.example.spring.database.test.enums.file.FileModuleEnum;

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
