package com.example.spring.service.file.dto;

import com.example.spring.service.file.vo.FileResVO;

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
