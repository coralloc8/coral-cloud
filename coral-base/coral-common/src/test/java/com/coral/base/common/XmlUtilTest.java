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

    @Test
    @DisplayName("解析xml测试2")
    public void parseXml2() throws IOException {
        String xml = "<xml>\n" +
                "  <result>\n" +
                "    <code>1000</code>\n" +
                "    <msg>操作成功</msg>\n" +
                "  </result>\n" +
                "  <dataSet class=\"com.alpha.commons.core.pojo.HospitalizedPatientInfoNew1\">\n" +
                "    <id>3</id>\n" +
                "    <patientName>吕瑶瑶</patientName>\n" +
                "    <sex>2</sex>\n" +
                "    <age>3岁4月</age>\n" +
                "    <birthday>2018-12-21 00:00:00</birthday>\n" +
                "    <nation>1</nation>\n" +
                "    <nationality>1</nationality>\n" +
                "    <outPatientNo>8800000090</outPatientNo>\n" +
                "    <contactPhone>13655287268</contactPhone>\n" +
                "    <contactName>何处</contactName>\n" +
                "    <contactAddr>黑龙江省哈尔滨市道里区新阳路街道过去陪不了我</contactAddr>\n" +
                "    <relationship>2</relationship>\n" +
                "    <patientCertiNo></patientCertiNo>\n" +
                "    <homePlace>10</homePlace>\n" +
                "    <signUrl>dat=</signUrl>\n" +
                "    <mailingAddress>河北省石家庄市长安区远处自有归处</mailingAddress>\n" +
                "    <nativePlace>8375</nativePlace>\n" +
                "    <xzz>黑龙江省哈尔滨市道里区新阳路街道@过去陪不了我</xzz>\n" +
                "    <patientType>45</patientType>\n" +
                "    <noticeId>88000009020181221</noticeId>\n" +
                "    <rStatus>0</rStatus>\n" +
                "  </dataSet>\n" +
                "</xml>";
        System.out.println("xml:" + xml);
        XmlUtil.XmlParseConfig xmlParseConfig = new XmlUtil.XmlParseConfig(true, null, Result2.class);
        List<Result2> list = XmlUtil.parseXml(xml, xmlParseConfig);
        System.out.println("-------------------------------------------------------");
        System.out.println(list.get(0));

    }


    @Data
    private static class Result2 {

        private Map<String, Object> dataSet;
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
