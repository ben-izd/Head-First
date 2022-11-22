/*
 * This code was written in 2022-11 by @ben-izd (https://github.com/ben-izd)
 * This code along with other files and their license can be obtained at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Code
 * Illustrations accompanying this code can be accessed at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Images
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

public class CountDownLatchExample {
    private static final int NANO_TO_MILLI = 1_000_000;
    private static final CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) {


        // Thread 1
        new Thread(() -> {
            countDownLatch.countDown();
            System.out.println("Thread 1: CountDown");
        }).start();

        // Thread 2
        new Thread(() -> {
            LockSupport.parkNanos(100 * NANO_TO_MILLI);

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Thread 2: after waiting");
        }).start();

        // Thread 3
        new Thread(() -> {
            LockSupport.parkNanos(200 * NANO_TO_MILLI);
            countDownLatch.countDown();
            System.out.println("Thread 3: CountDown");
        }).start();

        LockSupport.parkNanos(500 * NANO_TO_MILLI);
        System.out.println("CountDownLatch counter: " + countDownLatch.getCount());
    }
}
