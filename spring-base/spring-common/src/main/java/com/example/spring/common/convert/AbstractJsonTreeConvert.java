package com.example.spring.common.convert;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.reflect.MethodUtils;

import com.example.spring.common.CollectionUtil;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * @description: jsonTree转换器
 * @author: huss
 * @time: 2020/7/14 17:24
 */
public abstract class AbstractJsonTreeConvert<T, R extends SimpleJsonTree> implements JsonTreeConvert<T, R> {

    /**
     * 设置树的根节点parentId值
     */
    private static final String DEF_ROOT_ID = "";

    /**
     * 附属属性
     */
    @Getter(AccessLevel.PRIVATE)
    private List<String> extraColumns;

    public AbstractJsonTreeConvert() {

    }

    public AbstractJsonTreeConvert(List<String> extraColumns) {
        this.extraColumns = extraColumns;
    }

    /**
     * 后续会根据该传入的值获取对应节点值下的子节点数据
     * 
     * @param t
     *            每个原始数据集合中的节点数据
     * @return String
     */
    protected abstract String getOwnChildrenKeyVal(T t);

    /**
     * 后续会根据该传入的值来将原始数据分组
     * 
     * @param t
     *            每个原始数据集合中的节点数据
     * @return String
     */
    protected abstract String getGroupByColumnVal(T t);

    /**
     * 自定义排序
     * 
     * @return Comparator
     */
    protected abstract Comparator<T> sorted();

    /**
     * 创建树
     * 
     * @param t
     *            每个原始数据集合中的节点数据
     * @return
     */
    protected abstract R buildTree(T t);

    /**
     * 是否需要排序
     *
     * @return boolean
     */
    protected boolean isSort() {
        return false;
    }

    private String getRootParentIdDefVal() {
        return DEF_ROOT_ID;
    }

    /**
     * 数据转换
     * 
     * @param list
     *            源数据
     * @return 返回树形结构数据
     */
    @Override
    public List<R> convert(List<T> list) {
        if (CollectionUtil.isBlank(list)) {
            return Collections.emptyList();
        }

        return doConvert(list, this.getExtraColumns());
    }

    private List<R> doConvert(List<T> sourceData, List<String> extraColumns) {
        Map<String, List<T>> mapByParentId = sourceData.parallelStream().collect(Collectors.groupingBy(this.groupBy()));

        if (isSort()) {
            mapByParentId.values().forEach(list -> list.sort(sorted()));
        }

        // root数据
        List<T> roots = mapByParentId.get(this.getRootParentIdDefVal());

        return roots.parallelStream().map(c -> this.createJsonTree(mapByParentId, c, extraColumns))
            .collect(Collectors.toList());
    }

    /**
     * 递归创建树
     * 
     * @param mapByParentId
     *            分组后的原始数据
     * @param t
     *            每个原始数据集合中的节点数据
     * @param extraColumns
     *            附加属性
     * @return R
     */
    private R createJsonTree(Map<String, List<T>> mapByParentId, T t, List<String> extraColumns) {
        R jTree = this.buildTree(t);
        jTree.setValue(this.setTreeValue(t));
        jTree.setLabel(this.setTreeLabel(t));
        jTree.setExtraAttributes(this.buildExtraAttributes(t, extraColumns));
        return this.buildChildrenNodes(mapByParentId, this.getOwnChildrenKeyVal(t), jTree, extraColumns);
    }

    /**
     * 设置附加属性
     * 
     * @param t
     *            每个原始数据集合中的节点数据
     * @param extraColumns
     *            附加属性
     * @return Map
     */
    private Map<String, Object> buildExtraAttributes(T t, List<String> extraColumns) {
        if (CollectionUtil.isBlank(extraColumns)) {
            return Collections.emptyMap();
        }
        return extraColumns.parallelStream().collect(Collectors.toMap(key -> key, key -> this.invokeMethod(t, key)));
    }

    /**
     * 创建子节点
     * 
     * @param mapByParentId
     *            分组后的原始数据
     * @param nodeParentId
     *            父id
     * @param jTree
     *            父节点
     * @param extraColumns
     *            附加属性
     * @return R
     */
    private R buildChildrenNodes(Map<String, List<T>> mapByParentId, String nodeParentId, R jTree,
        List<String> extraColumns) {
        List<T> nodes = mapByParentId.get(nodeParentId);
        if (nodes != null) {
            if (this.isSort()) {
                nodes.sort(this.sorted());
            }

            List<SimpleJsonTree> currentNodes = nodes.parallelStream()
                .map(c -> this.createJsonTree(mapByParentId, c, extraColumns)).collect(Collectors.toList());
            jTree.setChildren(currentNodes);
        }
        return jTree;
    }

    private Function<T, String> groupBy() {
        return c -> this.isBlank(this.getGroupByColumnVal(c)) ? this.getRootParentIdDefVal()
            : this.getGroupByColumnVal(c);
    }

    private Object invokeMethod(T t, String key) {
        Object val;
        String newKey = key.substring(0, 1).toUpperCase() + key.substring(1);
        String methodName = "get" + newKey;
        try {
            val = MethodUtils.invokeMethod(t, methodName);
            val = this.isBlank(val) ? "" : val;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            methodName = "is" + newKey;
            try {
                val = MethodUtils.invokeMethod(t, methodName);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e2) {
                val = "";
            }
        }
        return val;
    }

    private boolean isBlank(Object obj) {
        boolean flag = Objects.isNull(obj);
        if (!flag) {
            if (obj instanceof String) {
                return obj.toString().trim().isEmpty();
            }
            if (obj instanceof Collection) {
                return ((Collection<?>)obj).size() == 0;
            }
        }
        return flag;
    }

}
