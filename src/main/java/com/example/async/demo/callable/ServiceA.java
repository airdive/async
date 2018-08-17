package com.example.async.demo.callable;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @description : TODO
 * @author: liuchuang
 * @date: 2018/8/16 下午2:34
 * @modified by:
 */
@Setter
@Getter
public class ServiceA implements Callable<String> {
    private CountDownLatch curLatch;

    private CountDownLatch nextLatch;

    private String param1;

    public ServiceA(String param1) {
        this.param1 = param1;
    }


    @Override
    public String call() throws Exception {
//        System.out.println(this.getClass().getSimpleName()+": curLatch="+curLatch.getCount()+", nextLatch="+nextLatch.getCount());
        if (curLatch != null)
            curLatch.await();
        Thread.sleep(1000);
        if (nextLatch != null)
            nextLatch.countDown();
        System.out.println(Thread.currentThread().getId()+":"+this.getClass().getSimpleName()+": "+param1);
        return param1;
    }
}
