/*
 * This code was written in 2022-11 by @ben-izd (https://github.com/ben-izd)
 * This code along with other files and their license can be obtained at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Code
 * Illustrations accompanying this code can be accessed at https://github.com/ben-izd/head-first/tree/main/Java/Concurrency/Images
 */

import java.util.concurrent.Exchanger;
import java.util.concurrent.locks.LockSupport;

public class ExchangerExample {
    private static final Exchanger<Character> exchanger = new Exchanger<>();

    public static void main(String[] args) {

        // Thread 1
        new Thread(() -> {
            var a = 'a';
            try {
                System.out.printf("Thread 1: %s%n", exchanger.exchange(a));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        // Thread 2
        new Thread(() -> {
            LockSupport.parkNanos(100 * 1_000_000);
            var b = 'b';
            try {
                System.out.printf("Thread 2: %s%n", exchanger.exchange(b));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
