package com.example.spring.web.test.dto;

import com.example.spring.web.test.vo.response.FileResVO;

import lombok.Data;
import lombok.ToString;

/**
 * @author huss
 */
@Data
@ToString(callSuper = true)
public class FileRelationSaveDTO extends FileResVO {

    private Long fileId;

    private Long infoId;

}
