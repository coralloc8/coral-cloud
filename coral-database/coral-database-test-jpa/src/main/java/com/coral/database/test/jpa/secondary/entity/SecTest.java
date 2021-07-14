package com.coral.database.test.jpa.secondary.entity;

import com.coral.base.common.jpa.entity.IdentityIdKey;
import com.coral.base.common.jpa.enums.GlobalSexEnum;
import lombok.Data;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author huss
 */
@Entity
@Table(name = "test2")
@Data
public class SecTest extends IdentityIdKey {

    private String name;

    private Integer age;

    private Double money;

    private LocalDateTime createTime;


    @Convert(converter = GlobalSexEnum.Convert.class)
    private GlobalSexEnum sex;
}
