package com.coral.test.groovy;

import com.coral.test.groovy.util.DataSourceGeneralConfig;
import com.coral.test.groovy.util.GroovyHelper;
import groovy.lang.GroovyShell;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

/**
 * @author huss
 * @version 1.0
 * @className GroovyHelperTest
 * @description GroovyHelperTest
 * @date 2022/12/7 13:41
 */
public class GroovyHelperTest {

    @Test
    public void test2() {
        String str = "1233";

        IntStream.rangeClosed(0, 1000).forEach(e -> {
            System.out.println(DigestUtils.md5Hex(str.getBytes()));
//            Md5Crypt.md5Crypt(str.getBytes(),"$1$QCc0NqLZ");
        });
    }

    @Test
    @DisplayName("groovy测试")
    public void test() {
        String sc = "[groovy]import com.coral.base.common.StringUtils\n" +
                "import com.coral.base.common.XmlUtil\n" +
                "\n" +
                "import java.util.stream.Collectors\n" +
                "\n" +
                "def run(String response) {\n" +
                "    long start = System.currentTimeMillis()\n" +
                "    if (StringUtils.isBlank(response)) {\n" +
                "        return error();\n" +
                "    }\n" +
                "    def xmlStr = \"<Root>\" + response + \"</Root>\"\n" +
                "\n" +
                "    XmlUtil.XmlParseConfig parseConfig = new XmlUtil.XmlParseConfig(true, null, Map.class);\n" +
                "    List<Map> list = XmlUtil.parseXml(xmlStr, parseConfig);\n" +
                "\n" +
                "    Map<String, Object> msg = list.get(0);\n" +
                "\n" +
                "    Object obj = msg.get(\"Msg\");\n" +
                "    List<String> rows = new ArrayList<>();\n" +
                "    if (obj instanceof String) {\n" +
                "        rows.add((String) obj);\n" +
                "    } else {\n" +
                "        rows = (List<String>) obj;\n" +
                "    }\n" +
                "\n" +
                "    List<Map> lines = new ArrayList<>()\n" +
                "    for (String row : rows) {\n" +
                "        parseConfig = new XmlUtil.XmlParseConfig(false, null, Map.class);\n" +
                "        List<Map> rowList = XmlUtil.parseXml(row, parseConfig);\n" +
                "        Map rowMsg = (Map) rowList.get(0).get(\"msg\");\n" +
                "        Map rowBody = (Map) rowMsg.get(\"body\");\n" +
                "        Map<String, Object> rowData = (Map) rowBody.get(\"row\");\n" +
                "\n" +
                "        rowData = rowData.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().toLowerCase(), e -> e.getValue()));\n" +
                "\n" +
                "        lines.add(rowData);\n" +
                "    }\n" +
                "    long end = System.currentTimeMillis()\n" +
                "    println \"耗时：${end - start} ms\"\n" +
                "    return success(lines);\n" +
                "\n" +
                "}\n" +
                "\n" +
                "def success(List<Map> list) {\n" +
                "    Map<String, Objects> map = new LinkedHashMap<>();\n" +
                "    map.put(\"success\", true);\n" +
                "    map.put(\"data\", list);\n" +
                "    return map;\n" +
                "}\n" +
                "\n" +
                "def error() {\n" +
                "    Map<String, Objects> map = new LinkedHashMap<>();\n" +
                "    map.put(\"success\", false);\n" +
                "    return map;\n" +
                "}\n" +
                "\n" +
                "\n" +
/*                "def response = \"\"\"\n" +
                "    <Msg>\n" +
                "     <![CDATA[<msg><body><row action=\"select\"><PATIENTNAME>钟子杰</PATIENTNAME><PNO>13021455</PNO><PNONEW/><SEX>1</SEX><BIRTHDAY>2009-09-23T00:00:00</BIRTHDAY><PATIENTCARDNO>440304200909232015</PATIENTCARDNO><DOCTORNAME/><DEPTCODE>0440</DEPTCODE><DEPTNAME>急诊科</DEPTNAME><OUTPATIENTNO>5301283</OUTPATIENTNO><VISITTIME>2021-11-03T01:08:12</VISITTIME><PHONE>18926768299</PHONE><PHONENEW>18926768299</PHONENEW><CARDNO>5301283</CARDNO><CREATETIME>2021-11-03T00:05:59</CREATETIME><TYPE/><INTERVALTIME>-</INTERVALTIME><OID/></row></body></msg>]]>\n" +
                "    </Msg>\n" +
                "    <Msg>\n" +
                "     <![CDATA[<msg><body><row action=\"select\"><PATIENTNAME>钟子杰2</PATIENTNAME><PNO>13021455</PNO><PNONEW/><SEX>1</SEX><BIRTHDAY>2009-09-23T00:00:00</BIRTHDAY><PATIENTCARDNO>440304200909232015</PATIENTCARDNO><DOCTORNAME/><DEPTCODE>0440</DEPTCODE><DEPTNAME>急诊科</DEPTNAME><OUTPATIENTNO>5301283</OUTPATIENTNO><VISITTIME>2021-11-03T01:08:12</VISITTIME><PHONE>18926768299</PHONE><PHONENEW>18926768299</PHONENEW><CARDNO>5301283</CARDNO><CREATETIME>2021-11-03T00:05:59</CREATETIME><TYPE/><INTERVALTIME>-</INTERVALTIME><OID/></row></body></msg>]]>\n" +
                "    </Msg>\n" +
                "\"\"\"\n" +*/
                "\n" +
                "println run(response)\n";

        String response = "   <Msg>\n" +
                "     <![CDATA[<msg><body><row action=\"select\"><PATIENTNAME>钟子杰</PATIENTNAME><PNO>13021455</PNO><PNONEW/><SEX>1</SEX><BIRTHDAY>2009-09-23T00:00:00</BIRTHDAY><PATIENTCARDNO>440304200909232015</PATIENTCARDNO><DOCTORNAME/><DEPTCODE>0440</DEPTCODE><DEPTNAME>急诊科</DEPTNAME><OUTPATIENTNO>5301283</OUTPATIENTNO><VISITTIME>2021-11-03T01:08:12</VISITTIME><PHONE>18926768299</PHONE><PHONENEW>18926768299</PHONENEW><CARDNO>5301283</CARDNO><CREATETIME>2021-11-03T00:05:59</CREATETIME><TYPE/><INTERVALTIME>-</INTERVALTIME><OID/></row></body></msg>]]>\n" +
                "    </Msg>\n" +
                "    <Msg>\n" +
                "     <![CDATA[<msg><body><row action=\"select\"><PATIENTNAME>钟子杰2</PATIENTNAME><PNO>13021455</PNO><PNONEW/><SEX>1</SEX><BIRTHDAY>2009-09-23T00:00:00</BIRTHDAY><PATIENTCARDNO>440304200909232015</PATIENTCARDNO><DOCTORNAME/><DEPTCODE>0440</DEPTCODE><DEPTNAME>急诊科</DEPTNAME><OUTPATIENTNO>5301283</OUTPATIENTNO><VISITTIME>2021-11-03T01:08:12</VISITTIME><PHONE>18926768299</PHONE><PHONENEW>18926768299</PHONENEW><CARDNO>5301283</CARDNO><CREATETIME>2021-11-03T00:05:59</CREATETIME><TYPE/><INTERVALTIME>-</INTERVALTIME><OID/></row></body></msg>]]>\n" +
                "    </Msg>";

        DataSourceGeneralConfig generalConfig = new DataSourceGeneralConfig();
        generalConfig.setDsUuid("001");
        GroovyHelper.GroovyProperty property = new GroovyHelper.GroovyProperty("response", response);
        IntStream.rangeClosed(0, 1000).forEach(e -> {
            System.out.println(GroovyHelper.buildAndRun(generalConfig, sc, property));
//            System.out.println(groovyShell.parse(parseScript(sc), buildBinding()).run());
        });

        System.out.println("================================");

        GroovyShell groovyShell = GroovyHelper.buildGroovyShell(generalConfig);

        GroovyHelper.GroovyProperty property2 = new GroovyHelper.GroovyProperty("response2", response);
        System.out.println(GroovyHelper.buildAndRun(generalConfig, sc, property2));

        System.out.println("response:" + groovyShell.getVariable("response"));

        System.out.println("response2:" + groovyShell.getVariable("response2"));


    }
}
