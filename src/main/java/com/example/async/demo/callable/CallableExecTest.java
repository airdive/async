package com.example.async.demo.callable;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @description : TODO
 * @author: liuchuang
 * @date: 2018/8/18 下午5:14
 * @modified by:
 */
public class CallableExecTest {

    @Test
    public void testCallableExecutor() throws InterruptedException, ExecutionException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Future<String> future = threadPool.submit(new ServiceA("a"));
        Thread.sleep(1000);
        //dosomething
        System.out.println("testCallableExecutor: "+future.get());
    }

    @Test
    public void testCallableFutureTask() throws InterruptedException, ExecutionException {
        ServiceA a = new ServiceA("a");
        FutureTask<String> futureTask = new FutureTask<>(a);
        futureTask.run();
        Thread.sleep(1000);
        //dosomething
        System.out.println("testCallableFutureTask: "+futureTask.get());
    }

    @Setter
    @Getter
    class ServiceA implements Callable<String> {
        private String param1;
        public ServiceA(String param1) {
            this.param1 = param1;
        }
        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getId()+":"+this.getClass().getSimpleName()+": "+param1);
            return param1;
        }
    }

}
