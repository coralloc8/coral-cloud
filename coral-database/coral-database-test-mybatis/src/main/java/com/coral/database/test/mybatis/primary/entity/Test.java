package com.coral.database.test.mybatis.primary.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author huss
 */
@Data
@TableName("test")
public class Test implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    private Integer age;

    private Double money;

    private LocalDateTime createTime;
}
