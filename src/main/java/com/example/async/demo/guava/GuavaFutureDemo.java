package com.example.async.demo.guava;

import com.google.common.util.concurrent.*;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * @description : TODO
 * @author: liuchuang
 * @date: 2018/8/17 上午10:21
 * @modified by:
 */
public class GuavaFutureDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long l = System.currentTimeMillis();
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        ListenableFuture<Integer> future1 = service.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                System.out.println("future1-执行耗时操作...");
                timeConsumingOperation();
                return 100;
            }
        });//<1>
        //Guava的回调式
        Futures.addCallback(future1, new FutureCallback<Integer>() {
            public void onSuccess(Integer result) {
                System.out.println("future1-回调计算结果:" + (result+100));
            }

            public void onFailure(Throwable throwable) {
                System.out.println("future1-异步处理失败,e=" + throwable);
            }
        });//<2>
        //Guava的将来式
        System.out.println("do something: "+new Date());
        Integer futureResult = future1.get();
        System.out.println("do something: "+new Date());
        System.out.println("future1 result: "+futureResult);
        ListenableFuture<Integer> future2 = service.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                System.out.println("future2-执行耗时操作...");
                timeConsumingOperation();
                return 100+futureResult;
            }
        });//<3>
        System.out.println("future2 result: "+future2.get());
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
