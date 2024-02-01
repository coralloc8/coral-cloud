package com.coral.test.flink.dto.desktop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInfo {

    private Long id;

    /**
     * 项目标识
     */
    private String project;

    /**
     * 项目名称
     */
    private String projectName;


    /**
     * 项目地址
     */
    private String url;

    /**
     * 接口地址
     */
    private String apiUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 状态
     */
    private Integer status;
}
