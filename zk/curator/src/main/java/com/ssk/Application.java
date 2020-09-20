package com.ssk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ssk
 * @date 2020/9/7
 */
public class Application {

    private final static  String CONNECT_ADDR = "127.0.0.1:2181";

    public static void main(String[] args) throws Exception {
        // 重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,1);
        // 创建连接
        CuratorFramework cf = CuratorFrameworkFactory.builder().connectString(CONNECT_ADDR)
                .retryPolicy(retryPolicy).build();
        // 开启连接
        cf.start();

        cf.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/supper/c1","c1内容".getBytes());
        cf.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/supper/c2","c2内容".getBytes());

        String ret1 = new String(cf.getData().forPath("/supper/c2"));
        System.out.println("读取节点内容,节点数据为:"+ret1);

        cf.setData().forPath("/supper/c2","修改c2的内容".getBytes());
        String ret2 = new String(cf.getData().forPath("/supper/c2"));
        System.out.println("读取节点内容,节点数据为:"+ret2);

        // 绑定回调函数
        ExecutorService pool = Executors.newCachedThreadPool();
        cf.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework cf, CuratorEvent ce) throws Exception {
                        System.out.println("绑定回调成功");
                        System.out.println("code:" + ce.getResultCode());
                        System.out.println("type:" + ce.getType());
                        System.out.println("线程为:" + Thread.currentThread().getName());
                    }
                }, pool)
                .forPath("/super/c3","c3内容".getBytes());


        // 读取子节点getChildren方法 和 判断节点是否存在checkExists方法

        List<String> list = cf.getChildren().forPath("/super");
        for(String p : list){
            System.out.println("获取子节点的路径（相对路径）:"+p);
        }

        Stat stat = cf.checkExists().forPath("/super/c3");
        System.out.println(stat);
        cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");
        cf.close();

    }


















}
