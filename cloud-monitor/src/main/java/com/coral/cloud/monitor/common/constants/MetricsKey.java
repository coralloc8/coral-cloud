package com.coral.cloud.monitor.common.constants;

/**
 * @author huss
 * @version 1.0
 * @className MetricsKey
 * @description 指标key
 * @date 2023/4/12 10:49
 */
public interface MetricsKey {
    /**
     *
     * {application="user-server",exception="None",method="GET",outcome="SUCCESS",status="200",uri="/actuator/prometheus",}
     * {application="user-server",class="com.coral.cloud.user.controller.UserController",exception="none",method="findUserInfos",result="success",}
     *
     */
    /**
     * 指标默认名称
     */
    String METRICS_DEF_NAME = "monitor";

    /**
     * 指标默认值
     */
    String METRICS_DEF_VAL = "zhyx_metrics";

    /**
     * 服务名称
     */
    String METRICS_SERVICE_NAME = "server";

    String METRICS_SERVICE_DESC_NAME = "server_name";

    String METRICS_HOSPITAL_NAME = "hospital";

    String METRICS_HOSPITAL_DESC_NAME = "hospital_name";

    String METRICS_METRICS_DESC_NAME = "metrics_name";

    /**
     * 指标源类型
     */
    String METRICS_TYPE_NAME = "type";


    /**
     * 指标异常名称
     */
    String METRICS_EXCEPTION_NAME = "exception";

    String METRICS_DATASOURCE_NAME = "datasource";

    String METRICS_SQL_NAME = "sql";

    String METRICS_HTTP_URL_NAME = "uri";

    String METRICS_HTTP_METHOD_NAME = "method";

    String METRICS_HTTP_ROUTE_PARAM_NAME = "route_param";

    String METRICS_HTTP_STATUS_NAME = "status";

    String METRICS_HTTP_ERR_CODE_NAME = "error_code";

    String METRICS_HTTP_ERR_MSG_NAME = "error_msg";

    /**
     * 默认指标标签值
     */
    String METRICS_DEF_TAG_VALUE = "none";


    //##############################################################################
    // jdbc配置
    String EXEC_CONF_JDBC_USERNAME = "username";
    String EXEC_CONF_JDBC_PASSWORD = "password";
    String EXEC_CONF_JDBC_DRIVER_CLASS_NAME = "driverClassName";

    //##############################################################################
    // 执行参数
    String EXEC_PARAM_API_HEADERS = "headers";
    String EXEC_PARAM_API_PARAMS = "params";
    String EXEC_PARAM_API_ROUTE_PARAMS = "routeParams";
    String EXEC_PARAM_API_METHOD = "method";
    String EXEC_PARAM_API_APPLICATION_TYPE = "applicationType";

    String EXEC_PARAM_API_ERR_CODE = "errorCode";

    String EXEC_PARAM_API_ERR_MSG = "errorMsg";

    String EXEC_PARAM_API_DATA_CODE = "data";

}
