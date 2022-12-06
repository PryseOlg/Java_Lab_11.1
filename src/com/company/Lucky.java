package com.company;

import java.util.concurrent.atomic.AtomicInteger;

public class Lucky {
    static AtomicInteger x = new AtomicInteger(0);
    static AtomicInteger count = new AtomicInteger(0);

    static class LuckyThread extends Thread {

        private static final Object lock = new Object();

        @Override
        public void run() {
            int temp;
            while (x.get() < 999999) {
                    temp = x.incrementAndGet();
                if ((temp % 10) + (temp / 10) % 10 + (temp / 100) % 10 == //Проверка "счастливого билета"
                        (temp / 1000) % 10 + (temp / 10000) % 10 + (temp / 100000) % 10) {
                    System.out.println(Thread.currentThread().getName() + ": " + temp);
                    synchronized (lock) {
                        count.incrementAndGet();
                    }
                }
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new LuckyThread();
        Thread t2 = new LuckyThread();
        Thread t3 = new LuckyThread();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Total: " + count);
    }
}