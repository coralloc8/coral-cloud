package com.example.spring.web.test.vo.response;

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

    private Long infoId;

    public FileAndInfoIdResVO(Long id, String url, FileModuleEnum fileModule, Long infoId) {
        super(id, url, fileModule);
        this.infoId = infoId;
    }
}
