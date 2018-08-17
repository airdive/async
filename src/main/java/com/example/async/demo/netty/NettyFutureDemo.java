package com.example.async.demo.netty;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * @description : TODO
 * @author: liuchuang
 * @date: 2018/8/17 下午5:31
 * @modified by:
 */
public class NettyFutureDemo {
    public static void main(String[] args) throws InterruptedException {
        long l = System.currentTimeMillis();
        EventExecutorGroup group = new DefaultEventExecutorGroup(4); // 4 threads
        Future<Integer> f = group.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("执行耗时操作...");
                timeConsumingOperation();
                return 100;
            }
        });
        //netty的回调式
        f.addListener(new FutureListener<Object>() {
            @Override
            public void operationComplete(Future<Object> objectFuture) throws Exception {
                System.out.println("计算结果:："+objectFuture.get());
            }
        });
        //netty的将来式
        try {
            System.out.println("f.get() = "+f.get());;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("主线程运算耗时:" + (System.currentTimeMillis() - l)+" ms");
        new CountDownLatch(1).await();//不让守护线程退出
    }
    static void timeConsumingOperation() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
