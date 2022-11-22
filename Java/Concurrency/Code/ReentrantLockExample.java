/*
 * This code was written in 2022-11 by @ben-izd (https://github.com/ben-izd)
 * This code along with other files and their license can be obtained at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Code
 * Illustrations accompanying this code can be accessed at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Images
 */

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
public class ReentrantLockExample {
    private static final int NANO_TO_MILLI = 1_000_000;

    private static final ReentrantLock reentrantLock =new ReentrantLock();

    public static void main(String[] args) {

        // Thread 1
        new Thread(() -> {
            reentrantLock.lock();
            System.out.println("Thread 1 - acquired");
            LockSupport.parkNanos(200*NANO_TO_MILLI);
            reentrantLock.unlock();
            System.out.println("Thread 1 - released");
        }).start();

        // Thread 2
        new Thread(() -> {
            LockSupport.parkNanos(100*NANO_TO_MILLI);
            reentrantLock.lock();
            System.out.println("Thread 2 - acquired");
            LockSupport.parkNanos(100*NANO_TO_MILLI);
            reentrantLock.unlock();
            System.out.println("Thread 2 - released");
        }).start();
    }
}
