/*
 * This code was written in 2022-11 by @ben-izd (https://github.com/ben-izd)
 * This code along with other files and their license can be obtained at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Code
 * Illustrations accompanying this code can be accessed at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Images
 */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

    public static void main(String[] args) throws InterruptedException {

        // Thread 1
        new CyclicBarrierSampleThread(cyclicBarrier, 0).start();

        // Thread 2
        new CyclicBarrierSampleThread(cyclicBarrier, 100).start();

        // Thread 3
        new CyclicBarrierSampleThread(cyclicBarrier, 200).start();

        Thread.sleep(400);
        System.out.println("Parties need to arrive: " + (cyclicBarrier.getParties() - cyclicBarrier.getNumberWaiting()));
    }
}

class CyclicBarrierSampleThread extends Thread {
    private static int counter = 1;
    private final int delay, threadNumber;
    private final CyclicBarrier cyclicBarrier;

    public CyclicBarrierSampleThread(CyclicBarrier cyclicBarrier, int delay) {
        this.cyclicBarrier = cyclicBarrier;
        this.delay = delay;
        this.threadNumber = counter++;

    }

    @Override
    public void run() {
        try {
            Thread.sleep(delay);
            System.out.printf("Thread %d started%n", threadNumber);
            cyclicBarrier.await();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.printf("Thread %d finished%n", threadNumber);
    }
}

