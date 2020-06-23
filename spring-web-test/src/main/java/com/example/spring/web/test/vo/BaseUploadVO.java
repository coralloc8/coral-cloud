package com.example.spring.web.test.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author huss
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BaseUploadVO {

    protected MultipartFile file;

    private String remark;

}
