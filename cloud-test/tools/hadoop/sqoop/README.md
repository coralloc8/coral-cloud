#### HCatalog 是 Hadoop 的一个 table 与存储管理的一个服务，用户可以更容易地使用不同的数据处理工具 Pig、MapReduce 和 Hive 读写数据。HCatalog 表的抽象呈现给用户一个 HDFS 分布式文件系统（HDFS）中的关系视图，用户不需要担心数据存储在哪里及数据的存储格式：RCFile 格式、text 文件、或者 SequenceFile。

# 参数

| 参数                                                     |                                                      功能                                                       |
|--------------------------------------------------------|:-------------------------------------------------------------------------------------------------------------:|
| --hcatalog-database                                    |                                   库名，默认为default，必须与--hcatalog-table 一起使用，可选                                   |
| --hcatalog-table                                       |                                                     表名，必填                                                     |
| --hcatalog-home                                        |                                    HCatalog安装目录，包含lib,share/hcatalog子目录，可选                                    |
| --create-hcatalog-table                                |                                          导入过程中如果不存在则创建表，表名自动转为小写，可选                                           |
| --hcatalog-storage-stanza                              |                                     对于新建的表指定存储格式，默认值为stored as rcfile，可选                                      |
| --hcatalog-partition-keys，--hcatalog-partition-values  | 指定多级静态分区，逗号分隔，可选，如--hcatalog-partition-keys year,month,day和 --hcatalog-partition-values 1999,12,31，两个参数必须一起使用 |
| --map-column-hive                                      |                                                    数据类型映射                                                     |
| --hive-home                                            |                                                  hive home路径                                                  |
| --hive-partition-key                                   |                                           静态分区列，必须为string类型，只能指定一个                                            |
| --hive-partition-value                                 |                                                    静态态分区值                                                     |

### 不支持的参数

* --hive-import
* --hive-overwrite
* --export-dir
* --target-dir
* --warehouse-dir
* --append
* --as-sequencefile
* --as-avrodatafile
* --as-parquetfile

### 忽略的参数:

* 输入分隔符选项
* 除了 --hive-drop-import-delims
  和 --hive-delims-replacement
  的输出分隔符选项。

# 分区

### 支持以下类型的表：

* 无分区表
* 指定一个静态分区键的分区表
* 数据库结果集中包含动态分区的分区表
* 包含一个静态分区和附加的动态分区的分区表

# schema映射

* sqoop目前不支持列名映射，但是用户可以指定类型映射
* 默认SQL中的float和real类型会被映射成HCatalog的float
* 除了binary的SQL类型都能映射成string类型
* SQL中的数字类型（int, shortint, tinyint, bigint 和 bigdecimal, float 和 double）可以被映射成HCatalog中任意的数字类型，根据精度的不同，数据有可能发生截断
* date/time/timestamps会被映射为Hive的date/timestamp类型，也可以映射成bigint类型（代表新纪元后的毫秒数）
* BLOBS 和 CLOBS只支持导入
* 数据库的列名会被映射成对应的小写形式，当前不支持大小写敏感的数据库对象名
* 允许列的投影，如果有动态分区列的话，必须是投影的一部分
* 动态分区列在db中必须有非空的属性，如果导入过程中动态分区列有null值的话会终止导入任务

# 数据类型支持

### Hive中所有的基本类型都支持，HCatalog的复杂类型都不支持

* TINYINT
* SMALLINT
* INTEGER
* BIGINT
* FLOAT
* DOUBLE
* DECIMAL
* TIMESTAMP
* DATE
* STRING
* VARCHAR
* CHAR
* BOOLEAN
* BINARY