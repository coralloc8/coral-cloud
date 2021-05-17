package com.coral.service.file.vo;

import com.coral.database.test.jpa.primary.enums.file.FileModuleEnum;

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
