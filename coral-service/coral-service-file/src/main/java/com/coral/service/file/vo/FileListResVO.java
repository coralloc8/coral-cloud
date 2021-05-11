package com.coral.service.file.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileListResVO {

    private String infoNo;

    private List<FileResVO> files;

}
