package com.coral.database.test.mybatis.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huss
 * @date 2023-08-22
 */
public class DbConfig {
    private final static Map<DbType, String> DB_KEYWORD_MAPPING = new HashMap<DbType, String>(16) {{
        put(DbType.DM, "\"");
    }};
    /**
     * 数据库关键字（只处理在字段中有可能出现的关键字）
     */
    public final static List<String> DB_KEYWORDS = new ArrayList<String>(64) {{
        add("STATUS");
        add("SYNONYM");
    }};

    public static String getReplaceStr(DbType dbType) {
        return DB_KEYWORD_MAPPING.getOrDefault(dbType, "");
    }

    public static String replaceAll(String str, String replaceStr) {
        return str.replaceAll("`", replaceStr);
    }

    public static String appendAll(String columnName, String replaceStr) {
        if (columnName.startsWith(replaceStr) && columnName.endsWith(replaceStr)) {
            return columnName;
        }
        if (columnName.startsWith("`") && columnName.endsWith("`")) {
            return replaceAll(columnName, replaceStr);
        }
        return replaceStr + columnName + replaceStr;
    }
}
