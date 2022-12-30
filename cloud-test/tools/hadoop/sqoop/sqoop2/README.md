1. 安装mysql成功、添加hive环境变量到/etc/profile
2. 参考`https://blog.51cto.com/u_12279910/5736286`
3. 将mysql驱动拷贝到`$SQOOP_HOME/server/lib`下
4. 设置环境变量 
    ```
    export SQOOP_HOME=/data/tools/sqoop/sqoop-1.99.7
    export PATH=${JAVA_HOME}/bin:${HADOOP_HOME}/bin:${HADOOP_HOME}/sbin:${HBASE_HOME}/bin:${HIVE_HOME}/bin:${SQOOP_HOME}/bin:$PATH

 
   ```
5. 切换 成mysql存储时会报错，因为内部sql中都是使用"",因此mysql需要设置 ` SET GLOBAL sql_mode = ANSI_QUOTES;`

