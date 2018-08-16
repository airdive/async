package com.example.async.demo.service;

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
public class ServiceB implements Callable<String> {

    private CountDownLatch curLatch;

    private CountDownLatch nextLatch;

    private String param1;

    private String param2;

    public ServiceB(String param1,String param2){
        this.param1=param1;
        this.param2=param2;
    }

    @Override
    public String call() throws Exception {
//        System.out.println(this.getClass().getSimpleName()+": curLatch="+curLatch.getCount()+", nextLatch="+nextLatch.getCount());
        if (curLatch!=null)
            curLatch.await();
        Thread.sleep(3000);
        if (nextLatch!=null)
            nextLatch.countDown();
        System.out.println(Thread.currentThread().getId()+":"+this.getClass().getSimpleName()+": "+param1+param2);
        return param1+" + "+param2;
    }
}
