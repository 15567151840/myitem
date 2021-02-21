package com.atguigu.redis;

import jdk.nashorn.internal.ir.CallNode;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestThread {

    private int j;
    private Lock lock = new ReentrantLock();
     public static void main(String [] args){
        TestThread tt = new TestThread();
         new Thread(tt.new Adder()).start();
//         for (int i = 0; i < 2; i++) {
//             new Thread(tt.new Adder()).start();
//             new Thread(tt.new Subtractor()).start();
//         }
     }
     private class Subtractor implements Runnable{

         @Override
         public void run() {
             while(true){
                 lock.lock();
                 try{
                     System.out.println("j--"+j--);
                 }finally{
                    lock.unlock();
                 }
             }
         }
     }
     private class Adder implements Runnable{

         @Override
         public void run() {
             while(true){
                 lock.lock();
                 try{
                   System.out.println("j++"+j++);
                 }finally{
                   lock.unlock();
                 }
             }
         }
     }
}
