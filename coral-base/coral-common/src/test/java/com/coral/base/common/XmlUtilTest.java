package com.coral.base.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className XmlUtilTest
 * @description xml 工具类测试
 * @date 2021/6/11 10:40
 */
public class XmlUtilTest {

    @Test
    @DisplayName("创建xml测试")
    public void createXml() {
        XmlBean xmlBean = XmlBean.builder()
                .name("test1")
                .age(12D)
                .sex(true)
                .list(Arrays.asList("1", "2", "3", "4", "6"))
                .items(
                        Arrays.asList(
                                new XmlItemBean("122"),
                                new XmlItemBean("23331"),
                                new XmlItemBean("432"),
                                new XmlItemBean("12")
                        )
                )
                .build();

        String xml = XmlUtil.createXml(xmlBean, "row", StandardCharsets.UTF_8);
        System.out.println(xml);
    }

    @Test
    @DisplayName("解析xml测试")
    public void parseXml() throws IOException {
        String path = "D:\\projects\\mine\\coral-cloud\\coral-base\\coral-common\\src\\test\\java\\com\\coral\\base\\common\\res.xml";
        System.out.println(Paths.get(path));
        List<String> lines = FileUtil.readFile(new FileInputStream(Paths.get(path).toFile()), "gb2312");
        String xml = String.join("", lines);
        System.out.println("xml:" + xml);
        XmlUtil.XmlParseConfig xmlParseConfig = new XmlUtil.XmlParseConfig(false, null, Map.class);
        List<Map> list = XmlUtil.parseXml(xml, xmlParseConfig);
        System.out.println("-------------------------------------------------------");
        System.out.println(list);

    }


    @Data
    @Builder
    private static class XmlBean {

        private String name;

        private Double age;

        private Boolean sex;

        private List<String> list;

        private List<XmlItemBean> items;

    }

    @AllArgsConstructor
    @Data
    private static class XmlItemBean {
        private String value;
    }
}
