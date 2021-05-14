package com.coral.database.test.jpa.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;


import com.coral.base.common.jpa.entity.IdentityIdKey;
import com.coral.base.common.jpa.enums.GlobalDeletedEnum;
import com.coral.database.test.jpa.enums.file.FileSaveTypeEnum;

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

    private String no;

    @Convert(converter = FileSaveTypeEnum.Convert.class)
    private FileSaveTypeEnum saveType;

    private String remark;

    private String relativePath;

    private String md5;

    @Convert(converter = GlobalDeletedEnum.Convert.class)
    private GlobalDeletedEnum deleted = GlobalDeletedEnum.NO;

}
