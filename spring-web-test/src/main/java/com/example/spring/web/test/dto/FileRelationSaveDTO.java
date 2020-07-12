package com.example.spring.web.test.dto;

import com.example.spring.web.test.vo.response.file.FileResVO;

import lombok.Data;
import lombok.ToString;

/**
 * @author huss
 */
@Data
@ToString(callSuper = true)
public class FileRelationSaveDTO extends FileResVO {

    private String fileNo;

    private String infoNo;

}
