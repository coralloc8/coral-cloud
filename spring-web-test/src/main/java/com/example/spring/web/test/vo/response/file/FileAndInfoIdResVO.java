package com.example.spring.web.test.vo.response.file;

import com.example.spring.database.test.enums.file.FileModuleEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class FileAndInfoIdResVO extends FileResVO {

    private String infoNo;

    public FileAndInfoIdResVO(String fileNo, String url, FileModuleEnum fileModule, String infoNo, String md5) {
        super(fileNo, url, fileModule, md5);
        this.infoNo = infoNo;
    }
}
