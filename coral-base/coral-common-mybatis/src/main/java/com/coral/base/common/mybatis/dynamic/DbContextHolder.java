package com.coral.base.common.mybatis.dynamic;

import com.coral.base.common.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huss
 * @version 1.0
 * @className DbContextHolder
 * @description
 * @date 2021/5/21 13:19
 */
@Slf4j
public class DbContextHolder {

    /**
     * 项目中配置数据源
     */
    private static Map<String, DataSource> dataSources = new ConcurrentHashMap<>();

    /**
     * 默认数据源
     */
    private static String defaultDs = "";

    /**
     * 为什么要用链表存储(准确的是栈)
     * <pre>
     * 为了支持嵌套切换，如ABC三个service都是不同的数据源
     * 其中A的某个业务要调B的方法，B的方法需要调用C的方法。一级一级调用切换，形成了链。
     * 传统的只设置当前线程的方式不能满足此业务需求，必须模拟栈，后进先出。
     * </pre>
     */
    private static final ThreadLocal<Deque<String>> contextHolder = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return new ArrayDeque();
        }
    };

    /**
     * 设置当前线程使用的数据源
     *
     * @param dsName
     */
    public static void setCurrentDsStr(String dsName) {
        if (StringUtils.isBlank(dsName)) {
            log.error("==========>dbType is null,throw NullPointerException");
            throw new NullPointerException();
        }
        if (!dataSources.containsKey(dsName)) {
            log.error("==========>datasource not exists,dsName={}", dsName);
            throw new RuntimeException("==========>datasource not exists,dsName={" + dsName + "}");
        }
        contextHolder.get().push(dsName);
    }


    /**
     * 获取当前使用的数据源
     *
     * @return
     */
    public static String getCurrentDsStr() {
        return contextHolder.get().peek();
    }

    /**
     * 清空当前线程数据源
     * <p>
     * 如果当前线程是连续切换数据源
     * 只会移除掉当前线程的数据源名称
     * </p>
     */
    public static void clearCurrentDsStr() {
        Deque<String> deque = contextHolder.get();
        deque.poll();
        if (deque.isEmpty()) {
            contextHolder.remove();
        }
    }

    /**
     * 添加数据源
     *
     * @param dsName
     * @param dataSource
     */
    public static void addDataSource(String dsName, DataSource dataSource) {
        if (dataSources.containsKey(dsName)) {
            log.error("==========>dataSource={} already exist", dsName);
            //throw new RuntimeException("dataSource={" + dsName + "} already exist");
            return;
        }
        dataSources.put(dsName, dataSource);
    }

    /**
     * 获取指定数据源
     *
     * @return
     */
    public static DataSource getDefaultDataSource() {
        if (StringUtils.isBlank(defaultDs)) {
            log.error("==========>default datasource must be configured");
            throw new RuntimeException("default datasource must be configured.");
        }
        if (!dataSources.containsKey(defaultDs)) {
            log.error("==========>The default datasource must be included in the datasources");
            throw new RuntimeException("==========>The default datasource must be included in the datasources");
        }
        return dataSources.get(defaultDs);
    }

    /**
     * 设置默认数据源
     *
     * @param defaultDsStr
     */
    public static void setDefaultDs(String defaultDsStr) {
        defaultDs = defaultDsStr;
    }

    /**
     * 获取所有 数据源
     *
     * @return
     */
    public static Map<String, DataSource> getDataSources() {
        return dataSources;
    }

    /**
     * @return
     */
    public static String getDefaultDs() {
        return defaultDs;
    }
}
