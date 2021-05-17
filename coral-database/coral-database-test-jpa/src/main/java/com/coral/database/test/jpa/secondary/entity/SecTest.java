package com.coral.database.test.jpa.secondary.entity;

import com.coral.base.common.jpa.entity.IdentityIdKey;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author huss
 */
@Entity
@Table(name = "test")
@Data
public class SecTest extends IdentityIdKey {

    private String name;

    private Integer age;

    private Double money;

    private LocalDateTime createTime;
}
