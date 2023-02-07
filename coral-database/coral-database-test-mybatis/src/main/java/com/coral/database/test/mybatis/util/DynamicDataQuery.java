package com.coral.database.test.mybatis.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * @author huss
 * @version 1.0
 * @className DynamicDataQuery
 * @description 动态数据查询
 * @date 2023/2/7 13:30
 */
public class DynamicDataQuery {
    private QueryWrapper<Serializable> query;

    /**
     * 数据源
     */
    @Getter
    private String dataSource;

    /**
     * 表名
     */
    @Getter
    private String tableName;

    public DynamicDataQuery(String tableName) {
        this(null, tableName);
    }

    public DynamicDataQuery(String dataSource, String tableName) {
        this.query = new QueryWrapper();
        this.dataSource = dataSource;
        if (StringUtils.isBlank(tableName)) {
            throw new RuntimeException("tableName cannot be null...");
        }
        this.tableName = tableName.startsWith("`") && tableName.endsWith("`") ? tableName : "`" + tableName + "`";
    }


    public QueryWrapper<Serializable> queryWrapper() {
        return this.query;
    }

    /**
     * 获取sql where 语句
     *
     * @return
     */
    public String getWhereSql() {
        return query.getTargetSql();
    }

    /**
     * 获取 sql 中的查询字段
     *
     * @return
     */
    public List<String> getSqlSelect() {
        String select = query.getSqlSelect();
        return StringUtils.isBlank(select) ? Collections.emptyList() : Arrays.asList(select.split(","));
    }

    /**
     * 获取sql where语句对应的参数
     *
     * @return
     */
    public Map<String, Object> getWhereParams() {
        Map<String, Object> params = new TreeMap<>(Comparator.comparing(e ->
                Integer.valueOf(e.substring(Constants.WRAPPER_PARAM.length())))
        );
        // 格式化后才有对应的值
        query.getTargetSql();
        params.putAll(query.getParamNameValuePairs());
        return params;
    }


//    public static void main(String[] args) {
//        DynamicDataQuery dataQuery = new DynamicDataQuery("c_user");
//
//        dataQuery.queryWrapper()
//                .select("name", "sex")
//                .in("status", Arrays.asList("1111", "2222"))
//                .eq("name", "chema")
//                .and(true, e -> {
//                            e.eq("sex", 1);
//                            e.or(true, e2 -> e2.eq("type", 1));
//                        }
//                );
//        System.out.println(dataQuery.getSqlSelect());
//        System.out.println(dataQuery.getWhereSql());
//        System.out.println(dataQuery.getWhereParams());
//    }

}
