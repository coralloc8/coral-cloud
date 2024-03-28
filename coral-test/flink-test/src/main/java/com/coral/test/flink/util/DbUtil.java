package com.coral.test.flink.util;

import com.coral.base.common.DateTimeFormatterUtil;
import com.coral.base.common.NameStyleUtil;
import com.coral.base.common.StrFormatter;
import com.coral.base.common.StringUtils;
import com.coral.base.common.json.JsonUtil;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.Instant;
import java.util.Date;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 数据库工具类
 *
 * @author huss
 * @date 2024/3/5 14:07
 * @packageName com.coral.test.flink.util
 * @className DbUtil
 */
@Slf4j
public class DbUtil {

    private static final String SQL_PARAM_PATTERN = "#\\{.*?\\}";

    private static final String TABLE_NAME_PATTERN = "#\\{.*?\\}";

    /**
     * 打开链接
     *
     * @param jdbcUrl
     * @param account
     * @param password
     * @return
     * @throws SQLException
     */
    public static Connection open(String jdbcUrl, String account, String password) throws SQLException {
        return DriverManager.getConnection(jdbcUrl, account, password);
    }

    /**
     * 批量数据插入
     *
     * @param connection
     * @param tableName
     * @param sql
     * @param list
     * @param <T>
     * @return
     * @throws SQLException
     */
    public static <T> long batchInsert(@NonNull Connection connection, String tableName, String sql, List<T> list) throws SQLException {
        if (Objects.isNull(list) || list.isEmpty()) {
            return 0;
        }
        PreparedStatement stmt = null;
        String sqlFormat = sqlFormat(sql);
        LinkedList<String> paramNames = parseParamNames(sql);
        List<ColumnInfo> columnInfos = findColumns(connection, tableName);
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareStatement(sqlFormat);
            for (T record : list) {
                fillPreparedStatement(stmt, paramNames, columnInfos, record);
                stmt.addBatch();
            }
            int count[] = stmt.executeBatch();
            connection.commit();
            return Arrays.stream(count).sum();
        } catch (Exception e) {
            log.error("批量写入数据库失败,回滚此次写入, 采用每次写入一行方式提交. 因为:{}", e.getMessage());
            connection.rollback();
            return insert(connection, tableName, sql, list);
        } finally {
            connection.setAutoCommit(true);
            if (Objects.nonNull(stmt)) {
                stmt.clearParameters();
                stmt.close();
            }
        }
    }

    /**
     * 单个数据插入
     *
     * @param connection
     * @param tableName
     * @param sql
     * @param list
     * @param <T>
     * @return
     * @throws SQLException
     */
    public static <T> long insert(@NonNull Connection connection, String tableName, String sql, List<T> list) throws SQLException {
        if (Objects.isNull(list) || list.isEmpty()) {
            return 0;
        }
        PreparedStatement stmt = null;
        String sqlFormat = sqlFormat(sql);
        LinkedList<String> paramNames = parseParamNames(sql);
        List<ColumnInfo> columnInfos = findColumns(connection, tableName);
        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareStatement(sqlFormat);
            int total = 0;
            for (T record : list) {
                fillPreparedStatement(stmt, paramNames, columnInfos, record);
                stmt.execute();
                int count = stmt.getUpdateCount();
                total = total + count;
            }
            return total;
        } finally {
            if (Objects.nonNull(stmt)) {
                stmt.clearParameters();
                stmt.close();
            }
        }
    }

    /**
     * 查询数据
     *
     * @param connection
     * @param clazz
     * @param sql
     * @param params
     * @param <T>
     * @return
     * @throws SQLException
     */
    public static <T> List<T> query(@NonNull Connection connection, Class<T> clazz, String sql, Object... params) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<T> results = new ArrayList<>(32);
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setQueryTimeout(30 * 1000);
            if (Objects.nonNull(params)) {
                int length = params.length;
                for (int i = 0; i < length; i++) {
                    Object obj = params[i];
                    // 简单测试用。只处理 int和String
                    if (Integer.class.isAssignableFrom(obj.getClass())) {
                        stmt.setInt(i + 1, toInt(obj));
                    }
                    if (String.class.isAssignableFrom(obj.getClass())) {
                        stmt.setString(i + 1, obj.toString());
                    }
                }
            }
            //结果集
            rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            while (rs.next()) {
                Map<String, Object> map = new LinkedHashMap<>();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Object columnValue = rs.getObject(columnLabel);
                    columnLabel = NameStyleUtil.lineToHump(columnLabel);
                    map.put(columnLabel, columnValue);
                }
                T t = JsonUtil.toPojo(map, clazz);
                results.add(t);
            }
        } finally {
            if (Objects.nonNull(rs)) {
                rs.close();
            }
            if (Objects.nonNull(stmt)) {
                stmt.close();
            }
        }
        return results;
    }

    public static List<ColumnInfo> findColumns(@NonNull Connection connection, String tableName) throws SQLException {
        return findColumns(connection, tableName, "");
    }

    /**
     * 查询表字段信息
     *
     * @param connection
     * @param tableName
     * @param schema
     * @return
     */
    public static List<ColumnInfo> findColumns(@NonNull Connection connection, String tableName, String schema) throws SQLException {
        List<ColumnInfo> fullColumn = Lists.newArrayList();
        ResultSet resultSet = null;
        //获取指定表的所有字段
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            if (StringUtils.isBlank(schema)) {
                schema = connection.getSchema();
            }
            //new String[]{"TABLE"}
            resultSet = metaData.getColumns(null, schema, tableName, null);
            while (resultSet.next()) {
                String COLUMN_NAME = (String) resultSet.getObject("COLUMN_NAME");
                String REMARKS = (String) resultSet.getObject("REMARKS");
                String COLUMN_DEF = (String) resultSet.getObject("COLUMN_DEF");
                int DATA_TYPE = (int) resultSet.getObject("DATA_TYPE");
                String TYPE_NAME = (String) resultSet.getObject("TYPE_NAME");
                String IS_AUTOINCREMENT = (String) resultSet.getObject("IS_AUTOINCREMENT");
                Integer NULLABLE = resultSet.getInt("NULLABLE");
                String CHAR_OCTET_LENGTH = resultSet.getString("CHAR_OCTET_LENGTH");
                ColumnInfo columnInfo = new ColumnInfo(COLUMN_NAME, REMARKS);
                columnInfo.setDataType(DATA_TYPE);
                columnInfo.setType(TYPE_NAME);
                columnInfo.setIfPrimaryKey(false);
                columnInfo.setIsnull(NULLABLE);
                columnInfo.setIsunique(0);
                columnInfo.setLength(CHAR_OCTET_LENGTH);
                columnInfo.setDefaultValue(COLUMN_DEF);
                columnInfo.setExtra(REMARKS);
                fullColumn.add(columnInfo);
            }
            //获取主键列
            List<String> primaryKeys = getPrimaryKeys(connection, tableName);
            //设置ifPrimaryKey标志
            fullColumn.forEach(e -> {
                if (primaryKeys.contains(e.getName())) {
                    e.setIfPrimaryKey(true);
                }
            });
        } catch (SQLException e) {
            log.error("[getColumns Exception] --> the exception message is:" + e.getMessage());
        } finally {
            if (Objects.nonNull(resultSet)) {
                resultSet.close();
            }
        }
        return fullColumn;
    }

    /**
     * 关闭连接
     *
     * @param connection
     * @throws Exception
     */
    public static void close(Connection connection) throws Exception {
        if (Objects.nonNull(connection)) {
            connection.close();
        }
    }

    /**
     * 单个插入
     *
     * @param stmt
     * @param paramNames
     * @param columnInfos
     * @param param
     * @param <T>
     * @throws SQLException
     */
    private static <T> void fillPreparedStatement(@NonNull PreparedStatement stmt,
                                                  LinkedList<String> paramNames,
                                                  List<ColumnInfo> columnInfos, T param) throws SQLException {
        Map<String, Object> map = JsonUtil.toMap(JsonUtil.toJson(param));
        Map<String, ColumnInfo> columnInfoMap = columnInfos.stream().collect(Collectors.toMap(ColumnInfo::getName, Function.identity()));
        for (int i = 0, size = paramNames.size(); i < size; i++) {
            String paramName = paramNames.get(i);
            String paramKey = getMapKey(map, paramName);
            Object value = map.get(paramKey);
            // 字段信息
            String columnKey = getMapKey(columnInfoMap, paramName);
            ColumnInfo columnInfo = columnInfoMap.get(paramName);
            if (Objects.isNull(columnInfo)) {
                throw new SQLException(StrFormatter.format("[{}]字段格式化后为[{}].最终格式化后的字段在数据库中不存在：", paramName, columnKey));
            }
            if (Objects.isNull(value) || StringUtils.isBlank(value.toString())) {
                log.error(">>>>>[{}]字段格式化后为[{}],最终格式化后的字段的数据中不存在", paramName, paramKey);
                stmt.setNull(i, columnInfo.getDataType());
                continue;
            }
            java.util.Date utilDate = null;
            String columName = columnInfo.getName();
            int columnSqlType = columnInfo.getDataType();
            String typeName = columnInfo.getType();
            String strValue = value.toString().trim();
            try {
                switch (columnSqlType) {
                    case Types.CHAR:
                    case Types.NCHAR:
                    case Types.CLOB:
                    case Types.NCLOB:
                    case Types.VARCHAR:
                    case Types.LONGVARCHAR:
                    case Types.NVARCHAR:
                    case Types.LONGNVARCHAR:
                        stmt.setString(i + 1, value.toString());
                        break;
                    case Types.TINYINT:
                    case Types.SMALLINT:
                        stmt.setShort(i + 1, Short.parseShort(strValue));
                        break;
                    case Types.INTEGER:
                        stmt.setInt(i + 1, Integer.parseInt(strValue));
                        break;
                    case Types.BIGINT:
                        stmt.setLong(i + 1, Long.parseLong(strValue));
                        break;
                    case Types.NUMERIC:
                    case Types.DECIMAL:
                    case Types.FLOAT:
                    case Types.REAL:
                    case Types.DOUBLE:
                        stmt.setDouble(i + 1, Double.parseDouble(strValue));
                        break;
                    //tinyint is a little special in some database like mysql {boolean->tinyint(1)}
//            case Types.TINYINT:
//                Long longValue = column.asLong();
//                if (null == longValue) {
//                    preparedStatement.setString(columnIndex + 1, null);
//                } else {
//                    preparedStatement.setShort(columnIndex + 1, Short.parseShort(strValue));
//                }
//                break;
                    // for mysql bug, see http://bugs.mysql.com/bug.php?id=35115
                    case Types.DATE:
                        if (typeName.equalsIgnoreCase("year")) {
                            stmt.setInt(i + 1, Integer.parseInt(strValue));
                        } else {
                            java.sql.Date sqlDate = null;
                            try {
                                Optional<Instant> instantOptional = DateTimeFormatterUtil.parseDateTime(strValue);
                                if (instantOptional.isPresent()) {
                                    utilDate = Date.from(instantOptional.get());
                                }
                            } catch (Exception e) {
                                throw new SQLException(String.format("Date 类型转换错误：[%s]", strValue));
                            }

                            if (Objects.nonNull(utilDate)) {
                                sqlDate = new java.sql.Date(utilDate.getTime());
                            }
                            stmt.setDate(i + 1, sqlDate);
                        }
                        break;
                    case Types.TIME:
                        java.sql.Time sqlTime = null;
                        try {
                            Optional<Instant> instantOptional = DateTimeFormatterUtil.parseDateTime(strValue);
                            if (instantOptional.isPresent()) {
                                utilDate = Date.from(instantOptional.get());
                            }
                        } catch (Exception e) {
                            throw new SQLException(String.format("TIME 类型转换错误：[%s]", strValue));
                        }
                        if (Objects.nonNull(utilDate)) {
                            sqlTime = new java.sql.Time(utilDate.getTime());
                        }
                        stmt.setTime(i + 1, sqlTime);
                        break;
                    case Types.TIMESTAMP:
                        java.sql.Timestamp sqlTimestamp = null;
                        try {
                            Optional<Instant> instantOptional = DateTimeFormatterUtil.parseDateTime(strValue);
                            if (instantOptional.isPresent()) {
                                utilDate = Date.from(instantOptional.get());
                            }
                        } catch (Exception e) {
                            throw new SQLException(String.format("TIMESTAMP 类型转换错误：[%s]", strValue));
                        }
                        if (null != utilDate) {
                            sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());
                        }
                        stmt.setTimestamp(i + 1, sqlTimestamp);
                        break;
                    case Types.BINARY:
                    case Types.VARBINARY:
                    case Types.BLOB:
                    case Types.LONGVARBINARY:
                        stmt.setBytes(i + 1, strValue.getBytes(StandardCharsets.UTF_8));
                        break;
                    case Types.BOOLEAN:
                        stmt.setString(i + 1, strValue);
                        break;
                    // warn: bit(1) -> Types.BIT 可使用setBoolean
                    // warn: bit(>1) -> Types.VARBINARY 可使用setBytes
                    case Types.BIT:
                        stmt.setBoolean(i + 1, "1".equals(strValue) || Boolean.TRUE.toString().equalsIgnoreCase(strValue));
//                        if (jobDatasource.getDatasource().equals("mysql")) {
//                            preparedStatement.setBoolean(columnIndex + 1, column.asBoolean());
//                        } else {
//                            preparedStatement.setString(columnIndex + 1, column.asString());
//                        }
                        break;
                    default:
                        throw new SQLException(String.format("您的配置文件中的列配置信息有误. 因为DataX 不支持数据库写入这种字段类型. 字段名:[%s]," +
                                " 字段类型:[%d], 字段Java类型:[%s]. 请修改表中该字段的类型或者不同步该字段.", typeName, columnSqlType, columName)
                        );
                }
            } catch (Exception e) {
                log.error("[fillPreparedStatementColumnType] ERROR:", e);
                stmt.setObject(i + 1, value, columnSqlType);
            }
        }
    }

    /**
     * 获取指定表的主键，可能是多个，所以用list
     *
     * @param connection
     * @param tableName
     * @return
     * @throws SQLException
     */
    private static List<String> getPrimaryKeys(Connection connection, String tableName) throws SQLException {
        List<String> res = Lists.newArrayList();
        ResultSet resultSet = null;
        //获取指定表的所有字段
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            //new String[]{"TABLE"}
            resultSet = metaData.getPrimaryKeys(null, connection.getSchema(), tableName);
            while (resultSet.next()) {
                String COLUMN_NAME = (String) resultSet.getObject("COLUMN_NAME");
                res.add(COLUMN_NAME);
            }
        } catch (SQLException e) {
            log.error("[getColumns Exception] --> {} the exception message is:{}", tableName, e.getMessage());
        } finally {
            if (Objects.nonNull(resultSet)) {
                resultSet.close();
            }
        }
        return res;
    }


    private static Integer toInt(Object obj) {
        try {
            return Objects.isNull(obj) ? 0 : Integer.parseInt(obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询map中的key
     *
     * @param map
     * @param originalKey
     * @return
     */
    private static String getMapKey(Map map, String originalKey) {
        String targetKey = originalKey;
        if (!map.containsKey(targetKey)) {
            targetKey = NameStyleUtil.lineToHump(originalKey);
        }
        if (!map.containsKey(targetKey)) {
            targetKey = NameStyleUtil.humpToLine(originalKey);
        }
        return targetKey;
    }

    /**
     * sql语句标准化
     *
     * @param sql
     * @return
     */
    private static String sqlFormat(String sql) {
        return sql.replaceAll(SQL_PARAM_PATTERN, "?");
    }

    /**
     * 根据sql解析sql参数名称
     *
     * @param sql
     * @return
     */
    private static LinkedList<String> parseParamNames(String sql) {
        Matcher matcher = sqlMatcher(sql);
        LinkedList<String> names = new LinkedList<>();
        while (matcher.find()) {
            String curVal = matcher.group().trim();
            curVal = curVal.substring(2, curVal.length() - 1);
            names.add(curVal);
        }
        return names;
    }

    private static Matcher sqlMatcher(String sql) {
        Pattern r = Pattern.compile(SQL_PARAM_PATTERN);
        return r.matcher(sql);
    }

    private static Matcher parseTableName(String sql) {
        Pattern r = Pattern.compile(SQL_PARAM_PATTERN);
        return r.matcher(sql);
    }
}
