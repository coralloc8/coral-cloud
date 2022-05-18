package com.coral.cloud.user.entity;

import com.coral.cloud.user.common.constants.IkConstant;
import com.coral.cloud.user.common.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className PersonDocument
 * @description person document
 * @date 2022/4/28 10:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setting(shards = 3, replicas = 2)
@Document(indexName = "person")
public class PersonDocument {

    /**
     * 人物id
     */
    @Id
    private Long id;

    /**
     * 人物姓名
     */
    @Field(type = FieldType.Text, analyzer = IkConstant.IK_SMART)
    private String personName;

    /**
     * 性别
     */
    @Field
    private Sex sex;

    /**
     * 资产
     */
    @Field
    private Long money;

    /**
     * 孩子数量
     */
    @Field
    private Integer childrenCount;

    /**
     * 孩子信息
     */
    @Field(type = FieldType.Nested)
    private List<ChildDocument> children;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createTime;

    /**
     * 出生日期
     */
    @Field(type = FieldType.Date, format = DateFormat.year_month_day)
    private LocalDate birthday;

    /**
     * 性格
     */
    @Field(type = FieldType.Text, analyzer = IkConstant.IK_SMART)
    private List<String> character;

    /**
     * 人物简介
     */
    @Field(type = FieldType.Text, index = false)
    private String personRemark;


}
