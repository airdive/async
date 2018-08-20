package com.example.async.demo.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * @description : TODO
 * @author: liuchuang
 * @date: 2018/8/16 下午8:01
 * @modified by:
 */
public class CompletableFutureDemo3 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long l = System.currentTimeMillis();
        //不指定线程池，则使用默认ForkJoinPool.commonPool()系统级公共线程池
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("在回调中执行耗时操作..."+l);
            timeConsumingOperation();
            System.out.println("date1-"+System.currentTimeMillis());
            return 100;
        });
        //thenCompose接收一个Function，返回一个CompletableFuture，组成一个chain，CompletableFuture的返回类型不能变
        completableFuture = completableFuture.thenCompose(i -> {
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("date2-"+System.currentTimeMillis());
                System.out.println("在回调的回调中执行耗时操作...");
                timeConsumingOperation();
                return i + 100;
            });
        });//<1>
        System.out.println(System.currentTimeMillis());
        Thread.sleep(2000);
        System.out.println(System.currentTimeMillis());
        //thenApply组成的组成chain，CompletableFuture的返回类型可以变
        CompletableFuture<String> future = completableFuture
                .thenApply((s) -> s + " World");
        System.out.println("future = "+future.get());

        completableFuture.whenComplete((result,e)->{
            System.out.println("计算结果:" + result);
        });
        //处理异常
        completableFuture.exceptionally((e)->{e.printStackTrace(); return null;});
        System.out.println("主线程运算耗时:" + (System.currentTimeMillis() - l) + " ms");
        new CountDownLatch(1).await();
    }

    static void timeConsumingOperation() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
