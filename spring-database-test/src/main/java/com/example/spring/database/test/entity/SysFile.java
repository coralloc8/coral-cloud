package com.example.spring.database.test.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.spring.common.jpa.entity.IdentityIdKey;
import com.example.spring.common.jpa.enums.GlobalDeletedEnum;
import com.example.spring.database.test.enums.file.FileSaveTypeEnum;

import lombok.Data;
import lombok.ToString;

/**
 * @author huss
 */
@Entity
@Table
@Data
@ToString(callSuper = true)
public class SysFile extends IdentityIdKey {

    @Convert(converter = FileSaveTypeEnum.Convert.class)
    private FileSaveTypeEnum saveType;

    private String remark;

    private String relativePath;

    private String md5;

    @Convert(converter = GlobalDeletedEnum.Convert.class)
    private GlobalDeletedEnum deleted = GlobalDeletedEnum.NO;

}
