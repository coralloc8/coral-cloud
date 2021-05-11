package com.coral.service.file.dto;

import com.coral.service.file.vo.FileResVO;

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
