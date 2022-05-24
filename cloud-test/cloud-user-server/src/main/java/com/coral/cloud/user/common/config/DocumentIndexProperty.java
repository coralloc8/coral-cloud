package com.coral.cloud.user.common.config;

import com.coral.base.common.StrFormatter;
import com.coral.base.common.StringUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huss
 * @version 1.0
 * @className DocumentIndex
 * @description 文档索引
 * @date 2022/5/23 16:17
 */
@Component("documentIndex")
@ConfigurationProperties(prefix = "elasticsearch.index")
@Data
public class DocumentIndexProperty {

    private static final String INIT_INDEX_PATTERN = "<index-{}-{now/d}-1>";
    private static final String SEARCH_PATTERN = "{}-search";
    private static final String WRITE_PATTERN = "{}-write";

    /**
     * 医院用户门诊病历记录
     */
    private DocumentIndex hospitalUserRecordMz;


    /**
     * index
     */
    @Data
    public static class DocumentIndex {

        /**
         * 索引前缀 可以只填写索引前缀，后续的参数会根据索引前缀自动生成默认格式
         */
        private String prefix;

        /**
         * 读索引
         */
        private String search;

        /**
         * 写索引
         */
        private String write;

        /**
         * 初始化indexName
         */
        private String initIndex;


        public String getSearch() {
            return this.format(SEARCH_PATTERN, this.search);
        }

        public String getWrite() {
            return this.format(WRITE_PATTERN, this.write);
        }

        /**
         * <index-xxxx-{now/d}-1>
         *
         * @return
         */
        public String getInitIndex() {
            return this.format(INIT_INDEX_PATTERN, this.initIndex);
        }

        private String format(String pattern, String text) {
            return StringUtils.isBlank(text) ?
                    StringUtils.isBlank(this.getPrefix()) ? "" : StrFormatter.format(pattern, this.getPrefix())
                    : text
                    ;
        }

    }

}
