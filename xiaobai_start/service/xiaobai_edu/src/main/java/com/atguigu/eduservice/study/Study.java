package com.atguigu.eduservice.study;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.concurrent.*;

/**
 * 异步的操作
 * 使用线程
 *
 * 1)、继承Thread
 * 2)、实现Runnable接口
 * 3)、实现callable接口、+FutureTask（可以拿到返回结果处理异常）
 * 4)、线程池
 *  区别：
 *  1、2不能得到返回值
 *  3、能得到返回值
 *  但是他们都不能达到资源控制的效果
 *  只有4的方式能够达到资源控制的效果 性能稳定
 *
 *
 * **/
public class Study  {

    public static ExecutorService service = Executors.newFixedThreadPool(10);//线程池

    public static void main(String [] args){
        System.out.println("main方法启动了");
//        Thread01 thread01 = new Thread01();
//        thread01.start();//启动线程
//        Runnable01 runnable01 = new Runnable01();
//        new Thread(runnable01).start();
//        FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
//        new Thread(futureTask).start();
        //等到线程执行完了之后最后返回结果 阻塞式等待
//        try{
//            Integer n = futureTask.get();
//            System.out.println(n);
//        }catch (Exception e){
//            e.printStackTrace();
        //       }
        //在实际业务中我们以上三种启动线程的方法我们都不用,用线程池启动
        //将实际的业务交给线程池去启异步任务
        service.execute(new Runnable01());



        System.out.println("main方法结束了");
        /***
         * 启动结果:
         *main方法启动了
         * main方法结束了
         * 当前线程12
         * 运行结果5
         *这样就启动了一个异步结果
         * */
       //线程池的七大参数

        /**
         int corePoolSize:[5],（核心线程数一直存在除非(allowCoreThreadTimeout)等待异步任务来执行）
         相当于new了五个Thread()只是他还没有启动，当线程池中分配了异步任务的时候他就会启动
         int maximumPoolSize[200],最大线程数量相当于控制资源
         long keepAliveTime,存活时间。如果当前线程数量大与core的数量
                      释放空闲的线程，释放的部分(200-5)
         TimeUnit unit,时间单位
         BlockingQueue<Runnable> workQueue,阻塞队列，如果任务有很多，就会将目前多的任务放在队列里面
                     只要线程空闲就会去队列里面取出新任务执行
         ThreadFactory threadFactory,线程的创建工厂
         RejectedExecutionHandler handler 如果队列满了，按照我门指定的拒绝策略执行拒绝任务
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
                200,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());


        System.out.println("main... end");
    }
    public static class Callable01 implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("Runnable01当前线程号 -> " + Thread.currentThread().getId());
            int n = 10 /5;
            return n;
        }

    }
    public static class Thread01 extends Thread{

        @Override
        public void run(){
           System.out.println("当前线程"+Thread.currentThread().getId());//得到当前线程的线程号
            int i=10/2;
            System.out.println("运行结果"+i);
       }

    }
    public static class Runnable01 implements Runnable {

        @Override
        public void run() {
            System.out.println("当前线程"+Thread.currentThread().getId());//得到当前线程的线程号
            int i=10/2;
            System.out.println("运行结果"+i);
        }
    }
}
