package com.example.spring.web.test.dto;

import java.io.InputStream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileSaveDTO {

    private InputStream inputStream;

    private String fileName;

    private String remark;

}
