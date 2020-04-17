package com.cy.leetcode;

import java.util.concurrent.atomic.AtomicInteger;

public class FooBar {
    private int n;

    AtomicInteger atomicInteger = new AtomicInteger(0);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            synchronized (atomicInteger) {
                if (atomicInteger.get() == 1) {
                    atomicInteger.wait();
                }
                if (atomicInteger.get() == 0) {
                    // printFoo.run() outputs "foo". Do not change or remove this line.
                    printFoo.run();
                    atomicInteger.incrementAndGet();
                    atomicInteger.notifyAll();
                }
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            synchronized (atomicInteger) {
                if (atomicInteger.get() == 0){
                    atomicInteger.wait();
                }
                if(atomicInteger.get() == 1) {
                        // printBar.run() outputs "bar". Do not change or remove this line.
                    printBar.run();
                    atomicInteger.addAndGet(-1);
                    atomicInteger.notifyAll();
                }
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        FooBar fooBar = new FooBar(10);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fooBar.foo(()->{
                        System.out.print("foo");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fooBar.bar(()->{
                        System.out.print("bar");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        thread2.start();
    }
}
