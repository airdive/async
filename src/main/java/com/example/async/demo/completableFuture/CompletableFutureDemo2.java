package com.example.async.demo.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * @description : TODO
 * @author: liuchuang
 * @date: 2018/8/16 下午6:02
 * @modified by:
 */
public class CompletableFutureDemo2 {
    public static void main(String[] args) throws InterruptedException {
        long l = System.currentTimeMillis();
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行耗时操作...");
            timeConsumingOperation();
            return 100;
        });
        completableFuture.whenComplete((result, e) -> {
            System.out.println("结果：" + result);
        });
        System.out.println("主线程运算耗时:" + (System.currentTimeMillis() - l)+" ms");
        new CountDownLatch(1).await();
    }

    static void timeConsumingOperation(){
        try{
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
