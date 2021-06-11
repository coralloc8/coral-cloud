package com.coral.base.common;

import com.coral.base.common.exception.Exceptions;
import com.coral.base.common.json.JsonUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

/**
 * @author huss
 * @version 1.0
 * @className XmlUtil
 * @description xml工具类
 * @date 2021/6/11 9:53
 */
public class XmlUtil {

    private static final String LIST_KEY = "value";

    /**
     * 创建xml
     *
     * @param bean     需要转换的map。
     * @param rootName 就是map的根key,如果map没有根key,就输入转换后的xml根节点。
     * @return String-->XML
     */
    @SuppressWarnings("unchecked")
    public static String createXml(Object bean, String rootName) {
        if (Objects.isNull(bean)) {
            return "";
        }
        Map<String, Object> map = JsonUtil.toMap(JsonUtil.toJson(bean));

        System.out.println("map :" + map);

        //获取map的key对应的value
        Map<String, Object> rootMap = (Map<String, Object>) map.get(rootName);
        if (rootMap == null) {
            rootMap = map;
        }
        Document doc = DocumentHelper.createDocument();
        //设置根节点
        doc.addElement(rootName);
        String xml = iteratorXml(doc.getRootElement(), rootName, rootMap);
        return formatXML(xml);
    }

    /**
     * 循环遍历params创建xml节点
     *
     * @param element    根节点
     * @param parentName 子节点名字
     * @param params     map数据
     * @return String-->Xml
     */
    @SuppressWarnings("unchecked")
    private static String iteratorXml(Element element, String parentName, Map<String, Object> params) {
        Element e = element.addElement(parentName);
        Set<String> set = params.keySet();
        for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
            String key = it.next();
            if (params.get(key) instanceof Map) {
                iteratorXml(e, key, (Map<String, Object>) params.get(key));
            } else if (params.get(key) instanceof List) {
                List<Object> list = (List<Object>) params.get(key);
                for (Object obj : list) {
                    if (obj instanceof Map) {
                        iteratorXml(e, key, (Map<String, Object>) obj);
                    } else {
                        Map<String, Object> tempMap = new HashMap<>(2);
                        tempMap.put(LIST_KEY, obj);
                        iteratorXml(e, key, tempMap);
                    }
                }

            } else {
                String value = params.get(key) == null ? "" : params.get(key).toString();
                e.addElement(key).addText(value);
                // e.addElement(key).addCDATA(value);
            }
        }
        return e.asXML();
    }


    /**
     * 格式化xml
     *
     * @param xml
     * @return
     */
    private static String formatXML(String xml) {
        String requestXML = null;
        XMLWriter writer = null;
        Document document;
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(new StringReader(xml));
            if (document != null) {
                StringWriter stringWriter = new StringWriter();
                // 格式化，每一级前的空格
                OutputFormat format = new OutputFormat(" ", true);
                // xml声明与内容是否添加空行
                format.setNewLineAfterDeclaration(false);
                // 是否设置xml声明头部 false：添加
                format.setSuppressDeclaration(false);
                // 设置分行
                format.setNewlines(true);
                writer = new XMLWriter(stringWriter, format);
                writer.write(document);
                writer.flush();
                requestXML = stringWriter.getBuffer().toString();
            }
            return requestXML;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
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
     * 解析xml to java bean
     *
     * @param xmlStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseXml(String xmlStr, Class<T> clazz) {
        Map<String, Object> map = xmlStrToMap(xmlStr);
        if (Map.class.isAssignableFrom(clazz)) {
            return (T) map;
        }
        return JsonUtil.toPojo(map, clazz);
    }

    private static Map<String, Object> xmlStrToMap(String xmlStr) {
        try {
            if (StringUtils.isBlank(xmlStr)) {
                return Collections.emptyMap();
            }
            Map<String, Object> map = new HashMap<>(16);
            Document document = DocumentHelper.parseText(xmlStr);
            // 获取根节点
            Element rootElt = document.getRootElement();
            //遍历子节点
            map = elementToMap(rootElt, map);
            return map;
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /***
     * XmlToMap核心方法，里面有递归调用
     * @param outEle
     * @param outMap
     */
    @SuppressWarnings("unchecked")
    private static Map<String, Object> elementToMap(Element outEle, Map<String, Object> outMap) {
        List<Element> list = outEle.elements();
        if (list.isEmpty()) {
            outMap.put(outEle.getName(), outEle.getTextTrim());
        } else {
            Map<String, Object> innerMap = new HashMap<>(16);
            for (Element ele1 : list) {
                String eleName = ele1.getName();
                Object obj = innerMap.get(eleName);
                if (obj == null) {
                    elementToMap(ele1, innerMap);
                } else {
                    if (obj instanceof java.util.Map) {
                        List<Map<String, Object>> list1 = new ArrayList<>();
                        list1.add((Map<String, Object>) innerMap.remove(eleName));
                        elementToMap(ele1, innerMap);
                        list1.add((Map<String, Object>) innerMap.remove(eleName));
                        innerMap.put(eleName, list1);
                    } else {
                        elementToMap(ele1, innerMap);
                        ((List<Map<String, Object>>) obj).add(innerMap);
                    }
                }
            }
            outMap.put(outEle.getName(), innerMap);
        }
        return outMap;
    }


}
