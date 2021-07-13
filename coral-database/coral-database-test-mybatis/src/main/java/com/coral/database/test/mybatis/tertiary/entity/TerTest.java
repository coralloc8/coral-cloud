package com.coral.database.test.mybatis.tertiary.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class TerTest implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    private Integer age;

    private Double money;

    private LocalDateTime createTime;
}
