1. 安装mysql成功、添加hive环境变量到/etc/profile
2. 将mysql驱动拷贝到`$HIVE_HOME/lib`下
3. 在mysql中创建数据库 `hbase-site.xml` 中配置的数据库名称`javax.jdo.option.ConnectionURL` hive_meta
4. 执行`$HIVE_HOME/bin`中的`schematool`命令 `schematool -dbType mysql -initSchema`来初始化hive元数据库
5. 拷贝hive目录到其他节点
6. 修改其他节点的`hive.server2.thrift.bind.host`值为各自节点的hostname
7. 每个节点后台启动`hiveserver2 &`
8. 测试连接集群 `beeline`
9. `!connect jdbc:hive2://sfserver-dev,sfserver,ywzfz:2181/;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2`