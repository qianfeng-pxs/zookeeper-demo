package com.example.zkdemo;


import com.example.zkdemo.dislock.nativeapi.DistributedLock;
import org.apache.zookeeper.KeeperException;
import org.junit.Test;

import java.io.IOException;

public class DistributedLockTest {

    @Test
    public void testDistribute() throws InterruptedException, IOException, KeeperException {

        final DistributedLock lock1 = new DistributedLock();
        final DistributedLock lock2 = new DistributedLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock1.zklock();
                    System.out.println("线程1 启动，获取到锁");
                    Thread.sleep(10 * 1000);

                    lock1.unZkLock();
                    System.out.println("线程1 释放锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock2.zklock();
                    System.out.println("线程2 启动，获取到锁");
                    Thread.sleep(10 * 1000);

                    lock2.unZkLock();
                    System.out.println("线程2 释放锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

