# 服务器准备

    * 192.168.29.186
    * 192.168.29.185
    * 192.168.29.61
# hadoop版本
    3.3.1
# 集群规划

|     主机名      |       IP       |    组件     |          |                      |             |          |            |                 |
|:------------:|:--------------:|:---------:|:--------:|:--------------------:|:-----------:|:--------:|:----------:|-----------------|
|    ywzfz     | 192.168.29.185 | Zookeeper |          |                      | Journalnode | DataNode | NodeManage | ResourceManager |
|   sfserver   | 192.168.29.186 | Zookeeper | NameNode | ZKFailoverController | Journalnode | DataNode | NodeManage | ResourceManager |
| sfserver-dev | 192.168.29.61  | Zookeeper | NameNode | ZKFailoverController | Journalnode | DataNode | NodeManage | ResourceManager |

    节点规划说明：
    
        Zookeeper集群: 需要至少3个节点，并且节点数为奇数个，可以部署在任意独立节点上，NameNode及ResourceManager依赖zookeeper进行主备选举和切换
        
        NameNode: 至少需要2个节点，一主多备，可以部署在任意独立节点上，用于管理HDFS的名称空间和数据块映射，依赖zookeeper和zkfc实现高可用和自动故障转移，并且依赖journalnode实现状态同步
        
        ZKFailoverController: 即zkfc，在所有NameNode节点上启动，用于监视和管理NameNode状态，参与故障转移
        
        Journalnode: 至少需要3个节点，并且节点数为奇数个，可以部署在任意独立节点上，用于主备NameNode状态信息同步
        
        ResourceManager: 至少需要2个节点，一主多备，可以部署在任意独立节点上，依赖zookeeper实现高可用和自动故障转移，用于资源分配和调度
        
        DataNode: 至少需要3个节点，因为hdfs默认副本数为3，可以部署在任意独立节点上，用于实际数据存储
        
        NodeManage: 部署在所有DataNode节点上，用于节点资源管理和监控

# 配置目录规划

|       服务        |          目录           |
|:---------------:|:---------------------:|
| hadoop namenode | /data/hadoop/dfs/name |
| hadoop datanode | /data/hadoop/dfs/data |
|   hadoop 临时目录   |   /data/hadoop/tmp    |
| zookeeper 数据目录  | /data/zookeeper/data  |
| zookeeper Log目录 | /data/zookeeper/logs  |


# 环境配置


