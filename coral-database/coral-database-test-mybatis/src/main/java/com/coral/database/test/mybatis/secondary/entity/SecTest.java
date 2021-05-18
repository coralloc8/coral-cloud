package com.coral.database.test.mybatis.secondary.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author huss
 */
@Data
@TableName("test")
public class SecTest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    private Integer age;

    private Double money;

    private LocalDateTime createTime;
}
