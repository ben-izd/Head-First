/*
 * This code was written in 2022-11 by @ben-izd (https://github.com/ben-izd)
 * This code along with other files and their license can be obtained at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Code
 * Illustrations accompanying this code can be accessed at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Images
 */

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    private static final Semaphore semaphore = new Semaphore(2);

    public static void main(String[] args) {

        // Thread 1
        new SemaphoreSampleThread(semaphore, 0).start();

        // Thread 2
        new SemaphoreSampleThread(semaphore, 100).start();

        // Thread 3
        new SemaphoreSampleThread(semaphore, 200).start();

    }
}

class SemaphoreSampleThread extends Thread {
    private final Semaphore semaphore;
    private final int delay, threadNumber;
    private static int counter = 1;

    public SemaphoreSampleThread(Semaphore semaphore, int delay) {
        this.semaphore = semaphore;
        threadNumber = counter++;
        this.delay = delay;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(delay);
            semaphore.acquire();
            System.out.println("Thread " + threadNumber + " acquired.");
            Thread.sleep(100);
            System.out.println("Thread " + threadNumber + " released.");
            semaphore.release();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
