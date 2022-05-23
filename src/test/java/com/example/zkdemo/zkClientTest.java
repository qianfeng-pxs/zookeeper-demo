package com.example.zkdemo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class zkClientTest {
    // 注意：逗号前后不能有空格
    private static String connectString = "8.136.114.62:2181";
    private static int sessionTimeout = 10000;
    ZooKeeper zkClient = null;

    @Before
    public void testConnection() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                // 收到事件通知后的回调函数（用户的业务逻辑）
//                System.out.println(watchedEvent.getType() + "--" + watchedEvent.getPath());
            }
        });
    }

    @Test
    public void createPoint() throws Exception {
        String nodeCreated = zkClient.create(
                "/pxs", // 参数 1：要创建的节点的路径
                "createTest".getBytes(), //参数 2：节点数据
                ZooDefs.Ids.OPEN_ACL_UNSAFE, //参数 3：节点权限
                CreateMode.PERSISTENT//参数 4：节点的类型
        );
    }

    @Test
    public void getChildPoint() throws InterruptedException, KeeperException {
        List<String> children = zkClient.getChildren("/", true);
        for (String point : children) {
            System.out.println("zk客户端有接口："+point);
        }
    }

    // 判断 znode 是否存在
    @Test
    public void ifExist() throws Exception {
        Stat stat = zkClient.exists("/pxs", false);
        System.out.println(stat == null ? "not exist" : "exist");
    }



}
