package com.coral.database.test.mybatis.tertiary.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.coral.base.common.mybatis.enums.GlobalSexEnum;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author huss
 */
@Data
@TableName("test3")
public class TerTest implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    private Integer age;

    private Double money;

    private LocalDateTime createTime;

    private GlobalSexEnum sex;
}
