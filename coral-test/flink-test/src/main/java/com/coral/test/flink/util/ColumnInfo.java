package com.coral.test.flink.util;

import com.coral.base.common.StringUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * 字段信息
 */
@Data
public class ColumnInfo implements Serializable {

    /**
     * 字段名称
     */
    private String name;
    /**
     * 注释
     */
    private String comment;
    /**
     * 字段类型
     *
     * @see java.sql.Types
     */
    private int dataType;
    /**
     * 字段类型
     */
    private String type;

    /**
     * 是否是主键列
     */
    private Boolean ifPrimaryKey;
    /**
     * 是否可为null   0 不可为空  1 可以为null
     */
    private Integer isnull;
    /**
     * 是否唯一   0 不唯一，1唯一
     */
    private Integer isunique;

    private String length;

    private String defaultValue;

    private String columnKey;

    private String extra;


    public ColumnInfo() {
    }

    public String getComment() {
        // 注释太长影响页面展示
        if (StringUtils.isNotBlank(comment) && comment.length() > 20) {
            return comment.substring(0, 20);
        }
        return comment;
    }

    public ColumnInfo(String name, String comment) {
        this.name = name;
        if (comment != null) {
            comment = comment.trim();
        }
        this.comment = comment;
    }

    public void setIsnull(Integer isnull) {
        this.isnull = isnull;
    }

    public void setIsNull(String isNullable) {
        switch (isNullable) {
            case "YES":
                this.isnull = 0;
                break;
            case "NO":
                this.isnull = 1;
                break;
            case "t":
                this.isnull = 0;
                break;
            case "f":
                this.isnull = 1;
                break;
            default:
                this.isnull = null;
        }
    }
}