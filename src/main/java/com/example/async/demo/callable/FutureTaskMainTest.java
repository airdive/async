package com.example.async.demo.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @description : TODO
 * @author: liuchuang
 * @date: 2018/8/17 上午11:16
 * @modified by:
 */
public class FutureTaskMainTest{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ServiceA a = new ServiceA("a");
        ServiceB b = new ServiceB("a","b");
        ServiceC c = new ServiceC("c");
        //FutureTask包装Callable或Runnable
        FutureTask<String> futureA = new FutureTask<>(a);// 将Callable写的任务封装到一个由执行者调度的FutureTask对象
        FutureTask<String> futureB = new FutureTask<>(b);
        FutureTask<String> futureC = new FutureTask<>(c);
        //通过线程池执行FutureTask
        threadPool.submit(futureA);
        threadPool.execute(futureB);
        threadPool.submit(futureC);
        //通过run()方法启动线程
        futureA.run();
        try {
            System.out.println("futureA="+futureA.get());;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        //FutureTask继承了Runnable接口，可以作为Thread的入参启动
        new Thread(futureA).start();
        System.out.println("thread: "+futureA.get());
    }


}
