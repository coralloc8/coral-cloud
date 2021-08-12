package com.coral.base.common.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className FileInfo
 * @description 文件信息
 * @date 2021/7/30 14:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileInfo {

    /**
     * file对应的参数名称
     */
    private String fileKey;

    /**
     * 文件流
     */
    private byte[] fileBytes;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件类型
     */
    private String mediaType;

}
