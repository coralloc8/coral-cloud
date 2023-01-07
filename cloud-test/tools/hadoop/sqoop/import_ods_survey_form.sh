#!/bin/bash

# 数据库
org_url=192.168.29.188:3306
org_username=zhyx
org_password=zhyx
org_database=zhyx_survey
org_table=form

query_field="create_time"
query_start_date="2022-01-01"
query_end_date="2022-12-31"

# hive
hive_database=ods_$org_database
hive_table=ods_$org_table
# hdfs目录
target_dir=/user/zhyx/$hive_database.$hive_table

#同步的时间
origin_date="1970-01-01"



## 把mysql表查询字段写到这里  日期字段进行先格式化
fields="
 id
 ,form_no
 ,owner_no
 ,project_no
 ,project_name
 ,form_name
 ,form_title
 ,create_time
 ,update_time
 ,creator
 ,editor
 ,is_scored
 ,status
"


sql="
SELECT $fields FROM $org_database.$org_table
WHERE $query_field between '$query_start_date 00:00:00' and '$query_end_date 23:59:59'
and \$CONDITIONS
"

sqoop import \
-Dorg.apache.sqoop.splitter.allow_text_splitter=true \
--query "$sql" \
--connect "jdbc:mysql://$org_url/$org_database?characterEncoding=utf8&useSSL=false&tinyInt1isBit=false" \
--username "$org_username" \
--password "$org_password" \
--driver "com.mysql.cj.jdbc.Driver" \
--split-by "id" \
--fields-terminated-by '\001' \
--mapreduce-job-name "mysql $org_database.$org_table to hive $hive_database.$hive_table" \
--hcatalog-database "$hive_database" \
--hcatalog-table "$hive_table" \
--hive-drop-import-delims \
--create-hcatalog-table \
--hcatalog-partition-keys "p_date" \
--hcatalog-partition-values "$origin_date" \
--hcatalog-storage-stanza 'stored as orc tblproperties ("orc.compress"="SNAPPY")'
