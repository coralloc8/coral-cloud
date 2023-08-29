package com.coral.base.common;

import com.coral.base.common.exception.Exceptions;
import com.coral.base.common.json.JsonUtil;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huss
 * @version 1.0
 * @className XmlUtil
 * @description xml工具类
 * @date 2021/6/11 9:53
 */
@Slf4j
public class XmlUtil {

    private static final String LIST_KEY = "value";


    /**
     * 转义内容
     *
     * @param content
     * @return
     */
    public static String escapeText(String content) {
        return StringUtils.isBlank(content) ? "" : String.format("<![CDATA[%s]]>", content);
    }


    /**
     * 创建xml 默认不带<?xml version="1.0" encoding="UTF-8"?>头，并且字段值不会添加CDATA前缀 <![CDATA[xxx]]>
     *
     * @param bean
     * @param rootName
     * @return
     */
    public static String createXml(Object bean, String rootName) {
        return createXml(bean, rootName, true, false, StandardCharsets.UTF_8);
    }

    /**
     * 创建xml 去掉根节点 默认不带<?xml version="1.0" encoding="UTF-8"?>头，并且字段值不会添加CDATA前缀 <![CDATA[xxx]]>
     *
     * @param bean
     * @param rootName
     * @return
     */
    public static String createXmlWithoutRootNode(Object bean, String rootName) {
        String xml = createXml(bean, rootName, true, false, StandardCharsets.UTF_8);
        String regex = String.format("\\</?%s\\>", rootName);
        return xml.replaceAll(regex, "");
    }

    /**
     * 创建xml
     *
     * @param bean                需要转换的map。
     * @param rootName            xml根节点
     * @param suppressDeclaration 禁止xml头部声明 false：添加
     * @param isCdata             字段内容是否需要CDATA前缀 <![CDATA[xxx]]>
     * @return String-->XML
     */
    public static String createXml(Object bean, String rootName, boolean suppressDeclaration, boolean isCdata) {
        return createXml(bean, rootName, suppressDeclaration, isCdata, StandardCharsets.UTF_8);
    }

    /**
     * 创建xml
     *
     * @param bean                需要转换的map。
     * @param rootName            xml根节点
     * @param suppressDeclaration 禁止xml头部声明 false：添加
     * @param isCdata             字段内容是否需要CDATA前缀 <![CDATA[xxx]]>
     * @param charset             字符集
     * @return String-->XML
     */
    @SuppressWarnings("unchecked")
    public static String createXml(Object bean, String rootName, boolean suppressDeclaration, boolean isCdata, Charset charset) {
        if (Objects.isNull(bean)) {
            return "";
        }
        Map<String, Object> map = JsonUtil.toMap(JsonUtil.toJson(bean));
        // 获取map的key对应的value
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding(charset.name());
        // 设置根节点
        doc.addElement(rootName);
        iteratorXml(doc.getRootElement(), "", map, isCdata);
        return formatXML(doc, suppressDeclaration);
    }

    /**
     * 循环遍历params创建xml节点
     *
     * @param element    根节点
     * @param parentName 子节点名字
     * @param params     map数据
     * @param isCdata    字段内容是否需要CDATA前缀 <![CDATA[xxx]]>
     * @return String-->Xml
     */
    @SuppressWarnings("unchecked")
    private static Element iteratorXml(Element element, String parentName, Map<String, Object> params, boolean isCdata) {
        Element e;
        if (StringUtils.isNotBlank(parentName)) {
            e = element.addElement(parentName);
        } else {
            e = element;
        }
        Set<String> set = params.keySet();
        for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
            String key = it.next();
            if (params.get(key) instanceof Map) {
                iteratorXml(e, key, (Map<String, Object>) params.get(key), isCdata);
            } else if (params.get(key) instanceof List) {
                List<Object> list = (List<Object>) params.get(key);
                for (Object obj : list) {
                    if (obj instanceof Map) {
                        iteratorXml(e, key, (Map<String, Object>) obj, isCdata);
                    } else {
                        Map<String, Object> tempMap = new HashMap<>(2);
                        tempMap.put(LIST_KEY, obj);
                        iteratorXml(e, key, tempMap, isCdata);
                    }
                }

            } else {
                String value = params.get(key) == null ? "" : params.get(key).toString();
                if (isCdata) {
                    e.addElement(key).addCDATA(value);
                } else {
                    e.addElement(key).addText(value);
                }
            }
        }
        return e;
    }

    /**
     * 格式化xml
     *
     * @param document
     * @param suppressDeclaration 是否设置xml声明头部 false：添加
     * @return
     */
    private static String formatXML(Document document, boolean suppressDeclaration) {
        if (Objects.isNull(document)) {
            return "";
        }
        String requestXML = null;
        XMLWriter writer = null;
        try (StringWriter stringWriter = new StringWriter()) {
            // 格式化，每一级前的空格
            OutputFormat format = new OutputFormat(" ", true);
            // xml声明与内容是否添加空行
            format.setNewLineAfterDeclaration(false);
            // 是否设置xml声明头部 false：添加
            format.setSuppressDeclaration(suppressDeclaration);
            format.setEncoding(document.getXMLEncoding());
            // 设置分行
            format.setNewlines(true);
            writer = new XMLWriter(stringWriter, format);
            //字段中的内容特殊字符不需要转义
            writer.setEscapeText(false);
            writer.write(document);
            writer.flush();
            requestXML = stringWriter.getBuffer().toString();
            return requestXML;
        } catch (Exception e1) {
            e1.printStackTrace();
            return "";
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {

                }
            }
        }
    }

    /**
     * 格式化xml
     *
     * @param xml
     * @param charset 字符集
     * @return
     */
    private static String formatXML(String xml, boolean suppressDeclaration, Charset charset) {
        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(new StringReader(xml));
            document.setXMLEncoding(charset.name());
            return formatXML(document, suppressDeclaration);
        } catch (DocumentException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 解析xml
     *
     * @param xmlStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseXmlWithoutRootNode(String xmlStr, Class<T> clazz) {
        return parseXml(xmlStr, new XmlParseConfig(true, null, clazz));
    }

    /**
     * 解析xml
     *
     * @param xmlStr
     * @param clazz
     * @param needRoot 是否需要根节点。有的xmlStr没有根节点，此时直接解析会失败
     * @param <T>
     * @return
     */
    public static <T> List<T> parseXmlWithoutRootNode(String xmlStr, Class<T> clazz, boolean needRoot) {
        xmlStr = needRoot && StringUtils.isNotBlank(xmlStr) ? "<root>" + xmlStr + "</root>" : xmlStr;
        return parseXml(xmlStr, new XmlParseConfig(true, null, clazz));
    }

    /**
     * 解析xml
     *
     * @param xmlStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseXml(String xmlStr, Class<T> clazz) {
        return parseXml(xmlStr, new XmlParseConfig(false, null, clazz));
    }

    /**
     * 解析xml to java bean
     *
     * @param xmlStr
     * @param xmlParseConfig xml解析配置
     * @return List 如果不删除根节点的话返回的一定是只有一个元素的list集合，如果删除根节点的话，返回的有可能是有多个元素的list集合
     */
    public static <T> List<T> parseXml(String xmlStr, @NonNull XmlParseConfig xmlParseConfig) {
        // xml解析成map
        Map<String, Object> map = xmlStrToMap(xmlStr, xmlParseConfig);
        //
        boolean removeRootNode = xmlParseConfig.isRemoveRootNode();
        Class<T> clazz = (Class<T>) xmlParseConfig.getClazz();

        Object obj = removeRootNode && map.size() == 1 ? map.values().iterator().next() : map;
//        System.out.println(">>>>> :" + JsonUtil.toJson(obj));
        if (obj instanceof Map) {
            return Arrays.asList(JsonUtil.toPojo((Map) obj, clazz));
        } else if (obj instanceof List) {
            return JsonUtil.parseArray(JsonUtil.toJson(obj), clazz);
        }
        return Collections.emptyList();
    }

    private static Map<String, Object> xmlStrToMap(String xmlStr, XmlParseConfig xmlParseConfig) {
        try {
            if (StringUtils.isBlank(xmlStr)) {
                return Collections.emptyMap();
            }
            List<String> arrayNodeNames = xmlParseConfig.getArrayNodeNames();
            Map<String, Object> map = new HashMap<>(16);

            SAXReader reader = SAXReader.createDefault();
            reader.setDocumentFactory(new DocumentFactory());

            if (Objects.nonNull(xmlParseConfig.getXpathNamespaces())) {
                reader.getDocumentFactory().setXPathNamespaceURIs(xmlParseConfig.getXpathNamespaces());
            }
            String encoding = getEncoding(xmlStr);
//            xmlStr = xmlStr.replaceAll("<\\?xml.*\\?>", "");
            InputSource source = new InputSource(new StringReader(xmlStr));
            source.setEncoding(encoding);

            Document document = reader.read(source);
            // if the XML parser doesn't provide a way to retrieve the encoding,
            // specify it manually
            if (document.getXMLEncoding() == null) {
                document.setXMLEncoding(encoding);
            }
            // 获取根节点
            Element rootElt = document.getRootElement();

            if (StringUtils.isNotBlank(xmlParseConfig.getXpathExpression())) {
                rootElt = (Element) rootElt.selectSingleNode(xmlParseConfig.getXpathExpression());
                if (Objects.isNull(rootElt)) {
                    throw Exceptions.unchecked(
                            new RuntimeException("对应的节点不存在,xpathExpression: " + xmlParseConfig.getXpathExpression())
                    );
                }
            }
//            List<Element> eles = new ArrayList<>(64);
//            eles.add(rootElt);
            if (xmlParseConfig.isXpathValParse()) {
                InputSource valSource = new InputSource(new StringReader(rootElt.getTextTrim()));
                valSource.setEncoding(encoding);
                Document valDoc = reader.read(valSource);
                valDoc.setXMLEncoding(encoding);
                rootElt = valDoc.getRootElement();

                if (StringUtils.isNotBlank(xmlParseConfig.getXpathValExpression())) {
                    rootElt = (Element) rootElt.selectSingleNode(xmlParseConfig.getXpathValExpression());
                }
                if (Objects.isNull(rootElt)) {
                    throw Exceptions.unchecked(
                            new RuntimeException("对应的节点不存在,xpathValExpression: " + xmlParseConfig.getXpathValExpression())
                    );
                }
            }
            // 遍历子节点
            map = elementToMap(rootElt, map, arrayNodeNames);
            return map;
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /***
     * XmlToMap核心方法，里面有递归调用
     *
     * @param outEle
     * @param outMap
     */
    @SuppressWarnings("unchecked")
    private static Map<String, Object> elementToMap(Element outEle, Map<String, Object> outMap, List<String> arrayNodeNames) {
        List<Element> list = outEle.elements();
        String currentNodeName = outEle.getName();

        if (list.isEmpty()) {
            outMap.put(currentNodeName, outEle.getTextTrim());
            return outMap;
        }
        Map<String, Object> innerMap = new HashMap<>(16);

        for (Element ele : list) {
            String perNodeName = ele.getName();

            if (!innerMap.containsKey(perNodeName)) {
                elementToMap(ele, innerMap, arrayNodeNames);
            } else {
                Object obj = innerMap.get(perNodeName);
                List innerList;
                if (!(obj instanceof List)) {
                    // map 转 list
                    innerList = new ArrayList<>();
                    innerList.add(innerMap.remove(perNodeName));
                } else {
                    innerList = (List) obj;
                }
                elementToMap(ele, innerMap, arrayNodeNames);
                innerList.add(innerMap.remove(perNodeName));
                innerMap.put(perNodeName, innerList);
            }
        }

        if (!arrayNodeNames.isEmpty() && arrayNodeNames.contains(currentNodeName)) {
            List<Map<String, Object>> temp = new ArrayList<>();
            temp.add(innerMap);
            outMap.put(currentNodeName, temp);
        } else {
            outMap.put(currentNodeName, innerMap);
        }
        return outMap;
    }

    private static String getEncoding(String text) {
        String result = null;
        String xml = text.trim();
        Pattern r = Pattern.compile("<\\?xml.*\\?>");
        Matcher m = r.matcher(xml);
        if (m.find()) {
            xml = m.group();
        }
        if (xml.startsWith("<?xml")) {
            int end = xml.indexOf("?>");
            String sub = xml.substring(0, end);
            StringTokenizer tokens = new StringTokenizer(sub, " =\"\'");

            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken();

                if ("encoding".equals(token)) {
                    if (tokens.hasMoreTokens()) {
                        result = tokens.nextToken();
                    }

                    break;
                }
            }
        }

        return result;
    }


    @Getter
    @Data
    public static class XmlParseConfig {

        /**
         * 解析时是否删除根节点
         */
        private boolean removeRootNode;

        /**
         * xpath查找的命名空间
         */
        private Map<String, String> xpathNamespaces;

        /**
         * xpath表达式 只查找单个节点
         */
        private String xpathExpression;

        /**
         * xpath值是否继续解析
         */
        private boolean xpathValParse;

        /**
         * xpath值继续解析xpath表达式 查找单个节点
         */
        private String xpathValExpression;

        /**
         * 是集合的节点列表
         */
        private List<String> arrayNodeNames;

        /**
         * 需要转换成的class bean
         */
        private Class<?> clazz;


        public XmlParseConfig(boolean removeRootNode, List<String> arrayNodeNames, Class<?> clazz) {
            this.removeRootNode = removeRootNode;
            this.arrayNodeNames = Objects.isNull(arrayNodeNames) ? Collections.emptyList() : arrayNodeNames;
            this.clazz = Objects.isNull(clazz) ? Map.class : clazz;
        }

        public XmlParseConfig(boolean removeRootNode, Class<?> clazz) {
            this(removeRootNode, null, clazz);
        }

    }

}
