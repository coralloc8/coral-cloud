#!/usr/bin/python3
# -*- coding: utf-8 -*-
import os

from config.global_var_model import DATABASES
from tools.setting import search_key


def run(query_start_date=None, query_end_date=None, origin_date="1970-01-01"):
    database = search_key(DATABASES, "zhyx_test")
    # org
    org_url = database.hostname + ":" + database.port
    org_username = database.username
    org_password = database.password
    org_database = "zhyx_survey"
    org_table = "form"

    # hive
    hive_database = "ods_" + org_database
    hive_table = "ods_" + org_table

    # 查询字段
    fields = """
        id
        , form_no
        , owner_no
        , project_no
        , project_name
        , form_name
        , form_title
        , create_time
        , update_time
        , creator
        , editor
        , is_scored
        , status
    """
    # 查询条件
    query_field = "create_time"

    # sql查询语句
    sql = """
        SELECT {} FROM {}.{}
        WHERE {} between '{} 00:00:00' and '{} 23:59:59'
        and \$CONDITIONS
        """.format(
        fields,
        org_database,
        org_table,
        query_field,
        query_start_date,
        query_end_date
    )

    sqoop_exec = """
        sqoop import \
            -D org.apache.sqoop.splitter.allow_text_splitter=true \
            --query "{}" \
            --connect "jdbc:mysql://{}/{}?characterEncoding=utf8&useSSL=false&tinyInt1isBit=false" \
            --username "{}" \
            --password "{}" \
            --driver "com.mysql.cj.jdbc.Driver" \
            --split-by "id" \
            --fields-terminated-by '\001' \
            --mapreduce-job-name "mysql {}.{} to hive {}.{}" \
            --hcatalog-database "{}" \
            --hcatalog-table "{}" \
            --hive-drop-import-delims \
            --create-hcatalog-table \
            --hcatalog-partition-keys "p_date" \
            --hcatalog-partition-values "{}" \
            --hcatalog-storage-stanza 'stored as orc tblproperties ("orc.compress"="SNAPPY")'
    """.format(
        sql,
        org_url,
        org_database,
        org_username,
        org_password,
        org_database,
        org_table,
        hive_database,
        hive_table,
        hive_database,
        hive_table,
        origin_date
    )

    # print("sql:", sql)
    # print("sqoop_exec:", sqoop_exec)

    # 执行
    print(">>>>> start run.")
    os.popen(sqoop_exec)


if __name__ == '__main__':
    # 执行
    run("2022-01-01", "2022-12-31")
