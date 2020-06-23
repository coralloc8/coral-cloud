package com.example.spring.database.test.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.example.spring.common.jpa.entity.IdentityIdKey;

import lombok.Data;

/**
 * @author huss
 */
@Entity
@Table
@Data
public class Test extends IdentityIdKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    private Double money;

    private LocalDateTime createTime;
}
