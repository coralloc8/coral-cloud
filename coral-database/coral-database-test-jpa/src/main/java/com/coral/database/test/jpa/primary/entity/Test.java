package com.coral.database.test.jpa.primary.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.coral.base.common.jpa.entity.IdentityIdKey;

import lombok.Data;

/**
 * @author huss
 */
@Entity
@Table
@Data
public class Test extends IdentityIdKey {

    private String name;

    private Integer age;

    private Double money;

    private LocalDateTime createTime;
}
