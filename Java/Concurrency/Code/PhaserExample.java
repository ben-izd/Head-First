/*
 * This code was written in 2022-11 by @ben-izd (https://github.com/ben-izd)
 * This code along with other files and their license can be obtained at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Code
 * Illustrations accompanying this code can be accessed at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Images
 */

import java.util.concurrent.Phaser;
import java.util.concurrent.locks.LockSupport;

public class PhaserExample {
    private static final int NANO_TO_MILLI = 1_000_000;

    private static final Phaser phaser = new Phaser(2);

    private static void showStatus(int threadNumber) {
        System.out.printf("Thread %d: [%d %d]%n", threadNumber, phaser.getRegisteredParties(), phaser.getPhase());
    }

    public static void main(String[] args) {
        showStatus(0);

        // Thread 1
        new Thread(() -> {
            phaser.arrive();
            showStatus(1);
            LockSupport.parkNanos(500 * NANO_TO_MILLI);
            phaser.arrive();
            showStatus(1);
        }).start();

        // Thread 2
        new Thread(() -> {
            LockSupport.parkNanos(100 * NANO_TO_MILLI);
            phaser.arrive();
            showStatus(2);
            LockSupport.parkNanos(300 * NANO_TO_MILLI);
            phaser.arriveAndAwaitAdvance();
            showStatus(2);
        }).start();


        // Thread 3
        new Thread(() -> {
            LockSupport.parkNanos(200 * NANO_TO_MILLI);
            phaser.register();
            showStatus(3);
            LockSupport.parkNanos(100 * NANO_TO_MILLI);
            phaser.arriveAndAwaitAdvance();
            showStatus(3);
        }).start();
    }
}
