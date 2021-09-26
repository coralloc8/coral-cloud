package com.coral.base.common.pdf;

import lombok.Data;
import lombok.Getter;

import java.io.FileOutputStream;

/**
 * @author huss
 * @version 1.0
 * @className PdfModel
 * @description pdf实体
 * @date 2021/9/23 16:47
 */
@Data
public class PdfModel {
    /**
     * 纸张大小
     */
    private PageSize pageSize;
    /**
     * 左边距
     */
    private Float marginLeft;
    /**
     * 右边距
     */
    private Float marginRight;
    /**
     * 上边距
     */
    private Float marginTop;
    /**
     * 底边距
     */
    private Float marginBottom;

    /**
     * 文件输出路径
     */
    private FileOutputStream fileOutputStream;


    /**
     * 标题
     */
    private String title;


    /**
     * 作者
     */
    private String author;


    /**
     * 主题
     */
    private String subject;


    /**
     * 关键字
     */
    private String keywords;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 水印内容
     */
    private String waterContent;


    private class Content {

        private String content;

        private String fontName;

        private String fontEncoding;
        /**
         * 字体是否需要嵌入
         */
        private Boolean fontEmbedded = false;

        /**
         * 字体大小
         */
        private Integer fontSize;

        /**
         * 字体样式
         */
        private FontStyle fontStyle;

        /**
         * 字体位置
         */
        private Alignment alignment;
    }

    /**
     * 字体位置
     */
    public enum Alignment {
        LEFT(0),
        CENTER(1),
        RIGHT(2);

        @Getter
        private Integer code;

        Alignment(Integer code) {
            this.code = code;
        }
    }

    /**
     * 字体样式
     */
    public enum FontStyle {
        NORMAL, BOLD;
    }


    /**
     * 页面大小
     */
    public enum PageSize {
        A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B0, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10;
    }


}
