package com.coral.test.spring.event;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author huss
 * @version 1.0
 * @className TrinoDatasourceTest
 * @description trino 数据源测试
 * @date 2023/3/7 15:43
 */
public class TrinoDatasourceTest {

    private static Connection connection;


    @BeforeAll
    public static void init() {
        try {
            Class.forName("io.trino.jdbc.TrinoDriver");
            // hive 库表 default.t_hive
            connection = DriverManager.getConnection("jdbc:trino://192.168.29.101:8077/hive/default?user=test");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * select * from zhyx_mange.ods_emr_log_api;
     * select * from zhyx_mange.ods_emr_log_api where date_p between DATE('2023-01-01') and DATE('2023-01-02');
     * <p>
     * select * from zhyx_mange.ods_emr_log_decision;
     * select * from zhyx_mange.ods_emr_log_decision where date_p between DATE('2023-01-01') and DATE('2023-01-02');
     * <p>
     * select * from zhyx_mange.ods_emr_log_decision_detail;
     * select * from zhyx_mange.ods_emr_log_decision_detail where date_p between DATE('2023-01-01') and DATE('2023-01-02');
     * <p>
     * select * from zhyx_mange.ods_emr_log_decision_relation;
     * select * from zhyx_mange.ods_emr_log_decision_relation where date_p between DATE('2023-01-01') and DATE('2023-01-02');
     */

    @Test
    @DisplayName("trino 数据源测试1")
    public void test1() throws SQLException {
        StopWatch stopWatch = StopWatch.createStarted();
        Statement st = connection.createStatement();

//        String sql = "select count(*) totalCount from zhyx_mange.ods_emr_log_decision_detail "; //15759730

        String sql = "select count(*) totalCount from zhyx_mange.ods_emr_log_decision "; //7521241

        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            System.out.println("记录数:" + rs.getInt("totalCount"));
        }

        stopWatch.stop();
        System.out.println("执行时长：" + stopWatch.getTime(TimeUnit.SECONDS) + " 秒.");
    }

    @Test
    @DisplayName("trino 数据源测试2")
    public void test2() throws SQLException {
        StopWatch stopWatch = StopWatch.createStarted();
        Statement st = connection.createStatement();
        //String sql = "select * from zhyx_mange.ods_emr_log_decision where req_time between timestamp'2023-02-07 00:00:00' and timestamp'2023-02-11 00:00:00'";
        //String sql = "select * from zhyx_mange.ods_emr_log_decision where dept_code_now = '1121' and req_time between timestamp'2023-02-07 00:00:00' and timestamp'2023-02-09 00:00:00'";

        String sql = "select old.* from  zhyx_mange.ods_emr_log_decision old " +
                "inner join zhyx_mange.ods_emr_log_decision_detail oldd on old.sequence = oldd.sequence where " +
                "old.req_time between timestamp'2023-02-07 00:00:00' and timestamp'2023-02-09 00:00:00' "
               // "limit 100";
                ;


//        String sql = "select old.* from  zhyx_mange.ods_emr_log_decision old " +
//                "inner join zhyx_mange.ods_emr_log_decision_detail oldd on old.sequence = oldd.sequence limit 1000";

        //String sql  = "select * from zhyx_mange.ods_emr_log_decision_detail where req_time between timestamp'2023-02-07 00:00:00' and timestamp'2023-02-11 00:00:00'";
        ResultSet rs = st.executeQuery(sql);
        List<String> ids = new ArrayList<>();
        while (rs.next()) {
//            System.out.println(rs.getString("sequence"));
//            System.out.println(rs.getString("patient_name"));
//            System.out.println("======================================");
            ids.add(rs.getString("sequence"));
        }
        System.out.println(ids.get(0));
        System.out.println("总数量：" + ids.size());
        stopWatch.stop();
        System.out.println("执行时长：" + stopWatch.getTime(TimeUnit.SECONDS) + " 秒.");
    }


}
