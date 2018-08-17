package com.example.async.demo.guava;

import com.google.common.util.concurrent.*;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description : TODO
 * @author: liuchuang
 * @date: 2018/8/17 下午6:19
 * @modified by:
 */
public class ChainInvokeDemo {
    @Test
    public void testLisenableFutureChain() throws InterruptedException, ExecutionException {
        //线程执行器
        ExecutorService normalService = Executors.newFixedThreadPool(100);
        final ListeningExecutorService service = MoreExecutors.listeningDecorator(normalService);
        ListenableFuture<Integer> future1 = service.submit(new TaskA());
        //异步调用函数
        AsyncFunction<Integer,String> asyncFunction = new AsyncFunction<Integer,String>(){
            @Override
            public ListenableFuture<String> apply(Integer input) throws Exception {
                return service.submit(new TaskB(input));
            }};
        //通过给定的future，异步调用后得到一个新的Future
        ListenableFuture<String> futures2 =  Futures.transformAsync(future1, asyncFunction);
        System.out.println(futures2.get());
    }
    class TaskA implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("taskA begin");
            Thread.sleep(2000);
            System.out.println("taskA end");
            return new Random().nextInt();
        }
    }

    class TaskB implements Callable<String> {
        private Integer i;
        public TaskB(Integer i){
            this.i = i;
        }
        @Override
        public String call() throws Exception {
            System.out.println("taskB begin");
            Thread.sleep(4000);
            System.out.println("taskB end");
            return i+"suffix";
        }
    }
}
