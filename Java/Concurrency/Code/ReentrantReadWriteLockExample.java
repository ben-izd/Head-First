/*
 * This code was written in 2022-11 by @ben-izd (https://github.com/ben-izd)
 * This code along with other files and their license can be obtained at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Code
 * Illustrations accompanying this code can be accessed at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Images
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.LockSupport;

public class ReentrantReadWriteLockExample {
    private static final int NANO_TO_MILLI = 1_000_000;
    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        
        // Thread 1
        new Thread(() -> {
            var readLock = reentrantReadWriteLock.readLock();
            readLock.lock();
            System.out.println("Thread 1 acquired read lock");
            LockSupport.parkNanos(200 * NANO_TO_MILLI);
            readLock.unlock();
            System.out.println("Thread 1 released read lock");
        }).start();

        // Thread 2
        new Thread(() -> {
            LockSupport.parkNanos(100 * NANO_TO_MILLI);
            var readLock = reentrantReadWriteLock.readLock();
            readLock.lock();
            System.out.println("Thread 2 acquired read lock");
            LockSupport.parkNanos(300 * NANO_TO_MILLI);
            readLock.unlock();
            System.out.println("Thread 2 released read lock");
        }).start();


        // Thread 3
        new Thread(() -> {
            LockSupport.parkNanos(300 * NANO_TO_MILLI);
            var writeLock = reentrantReadWriteLock.writeLock();
            writeLock.lock();
            System.out.println("Thread 3 acquired write lock");
            LockSupport.parkNanos(200 * NANO_TO_MILLI);
            writeLock.unlock();
            System.out.println("Thread 3 released write lock");
        }).start();


    }
}
