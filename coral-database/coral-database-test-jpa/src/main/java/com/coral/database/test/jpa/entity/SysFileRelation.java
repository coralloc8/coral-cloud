package com.coral.database.test.jpa.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;


import com.coral.base.common.jpa.entity.IdentityIdKey;
import com.coral.database.test.jpa.enums.file.FileModuleEnum;

import lombok.Data;
import lombok.ToString;

/**
 * @author huss
 */
@Entity
@Table
@Data
@ToString(callSuper = true)
public class SysFileRelation extends IdentityIdKey {

    private String fileNo;

    @Convert(converter = FileModuleEnum.Convert.class)
    private FileModuleEnum fileModule;

    private String infoNo;

}
