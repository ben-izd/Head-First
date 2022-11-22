/*
 * This code was written in 2022-11 by @ben-izd (https://github.com/ben-izd)
 * This code along with other files and their license can be obtained at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Code
 * Illustrations accompanying this code can be accessed at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Images
 */

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {
    private static final StampedLock stampedLock = new StampedLock();
    private static final int NANO_TO_MILLI = 1_000_000;

    public static void main(String[] args) {

        // Thread 1
        new Thread(() -> {
            var or = stampedLock.tryOptimisticRead();
            LockSupport.parkNanos(200 * NANO_TO_MILLI);
            System.out.println("Thread 1 validation: " + stampedLock.validate(or));
            LockSupport.parkNanos(200 * NANO_TO_MILLI);
            System.out.println("Thread 1 validation: " + stampedLock.validate(or));
            LockSupport.parkNanos(200 * NANO_TO_MILLI);
        }).start();

        // Thread 2
        new Thread(() -> {
            LockSupport.parkNanos(100 * NANO_TO_MILLI);

            var readLock = stampedLock.readLock();
            System.out.println("Thread 2 acquired read lock");
            LockSupport.parkNanos(200 * NANO_TO_MILLI);
            var writeLock = stampedLock.tryConvertToWriteLock(readLock);
            if (writeLock != 0) {
                System.out.println("Thread 2 converted read lock to write lock");
                LockSupport.parkNanos(200 * NANO_TO_MILLI);
                stampedLock.unlockWrite(writeLock);
                System.out.println("Thread 2 released write lock");
            } else {
                System.out.println("Thread 2 couldn't convert read lock to write lock");
            }
        }).start();
    }
}
