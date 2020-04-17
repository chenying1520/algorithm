package com.cy.leetcode;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

public class ZeroEvenOddMain {


    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(10);
        new Thread(() -> {
            try {
                zeroEvenOdd.zero((i)->{
                    System.out.print(i);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                zeroEvenOdd.even((i)->{
                    System.out.print(i);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                zeroEvenOdd.odd((i)->{
                    System.out.print(i);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    static class ZeroEvenOdd {
        private int n;
        private AtomicInteger atomicInteger = new AtomicInteger();

        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            while (atomicInteger.get() < 2*n){
                synchronized (atomicInteger) {
                    int num = atomicInteger.get() % 4;
                    if(num != 0 && num != 2) {
                        atomicInteger.wait();
                    }
                    if(num == 0 || num == 2){
                        printNumber.accept(0);
                        atomicInteger.incrementAndGet();
                        atomicInteger.notifyAll();
                    }
                }
            }
        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            while (atomicInteger.get() < 2*n) {
                synchronized (atomicInteger) {
                    int num = atomicInteger.get() % 4;
                    if(num != 3) {
                        atomicInteger.wait();
                    }
                    if(num == 3) {
                        printNumber.accept(atomicInteger.get()/2 + 1);
                        atomicInteger.incrementAndGet();
                        atomicInteger.notifyAll();
                    }
                }
            }
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            while (atomicInteger.get() < 2*n){
                synchronized (atomicInteger) {
                    int num = atomicInteger.get() % 4;
                    if(num != 1) {
                        atomicInteger.wait();
                    }
                    if(num == 1) {
                        printNumber.accept(atomicInteger.get()/2 + 1);
                        atomicInteger.incrementAndGet();
                        atomicInteger.notifyAll();
                    }
                }
            }
        }
    }
}
