package com.example.spring.web.test.vo.response.file;

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
