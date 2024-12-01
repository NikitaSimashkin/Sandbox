package ru.kram.sandbox;

import org.junit.jupiter.api.Test;

public class LocalVarInMultipleThreadsJavaTest {

    @Test
    public void localVarTwoThreads() throws InterruptedException {
        int localVar = 0;

        Thread write = new Thread(() -> {
            while (true) {
               // localVar++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        Thread read = new Thread(() -> {
            while (true) {
                System.out.println(localVar);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        write.start();
        read.start();

        write.join();
        read.join();
    }
}