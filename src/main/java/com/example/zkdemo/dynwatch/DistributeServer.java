package com.example.zkdemo.dynwatch;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class DistributeServer {
    private static String connectString = "8.136.114.62:2181";
    private static int sessionTimeout = 10000;
    private ZooKeeper zk = null;
    private String parentNode = "/servers";


    public static void main(String[] args) throws Exception {
        // 1 获取 zk 连接
        DistributeServer server = new DistributeServer();
        server.getConnect();

        // 2 利用 zk 连接注册服务器信息
        server.registServer(args[0]); // args[0]:节点数据

        // 3 模仿启动业务功能 [延时阻塞，使程序不退出]
        server.business(args[0]);
    }

    // 创建到 zk 的客户端连接
    public void getConnect() throws IOException {
        zk = new ZooKeeper(connectString, sessionTimeout,
                new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                    }
                }
        );
    }

    // 【注册服务器】注册服务器就是创建对应的节点
    public void registServer(String hostname) throws Exception {
        // 参数1：hostname 是 hadoop102 代表创建hadoop102节点，代表hadoop102服务上线
        // 同时 节点的类型是临时的，一旦hadoop102关闭则/Service/hadoop102目录删除，就代表hadoop102服务下线
        String create = zk.create(
                parentNode + hostname,     //参数1:要创建的节点的路径
                hostname.getBytes(),            // 参数2:节点数据
                Ids.OPEN_ACL_UNSAFE,            // 参数3:节点权限
                CreateMode.EPHEMERAL_SEQUENTIAL // 参数4:节点的类型
        );
        System.out.println(hostname + " is online " + create);
    }

    // 业务功能
    public void business(String hostname) throws Exception {
        System.out.println(hostname + " is working ...");
        // 延时阻塞，使程序不退出
        Thread.sleep(Long.MAX_VALUE);
    }
}
