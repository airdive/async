package com.example.async.demo.callable;

import com.example.async.demo.callable.ServiceA;
import com.example.async.demo.callable.ServiceB;
import com.example.async.demo.callable.ServiceC;

import java.util.concurrent.*;

/**
 * @description : TODO
 * @author: liuchuang
 * @date: 2018/8/16 下午2:47
 * @modified by:
 */
public class CallableMainTest {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ConcurrentLinkedQueue<Callable> queue = new ConcurrentLinkedQueue();
        String resultA = null;
        String resultB = null;
        ServiceA a = new ServiceA("a");
        ServiceB b = new ServiceB("a","b");
        ServiceC c = new ServiceC("c");
        queue.offer(a);
        queue.offer(b);
        queue.offer(c);
        CountDownLatch latchB = new CountDownLatch(1);
        CountDownLatch latchC = new CountDownLatch(1);
        a.setNextLatch(latchB);
        b.setCurLatch(latchB);
        b.setNextLatch(latchC);
        c.setCurLatch(latchC);

        while (!queue.isEmpty()){
            threadPool.submit(queue.poll());
            System.out.println("latch: "+latchB.getCount());
            System.out.println("queue: "+queue.size());
        }

        System.out.println("pool: "+threadPool.isShutdown());
    }
}
