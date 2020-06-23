package com.example.spring.web.test.vo.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileListResVO {

    private Long infoId;

    private List<FileResVO> files;

}
