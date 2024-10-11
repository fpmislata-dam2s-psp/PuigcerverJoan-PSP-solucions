package ud2.examples.thread;

import java.util.concurrent.ThreadLocalRandom;

public class StartThreads {
    public static void main(String[] args) {
        HelloThread thread1 = new HelloThread("Fil1");
        HelloThread thread2 = new HelloThread("Fil2");
        HelloThread thread3 = new HelloThread("Fil3");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            for(int i = 0; i < 5; i++) {
                int sleepTime = ThreadLocalRandom.current().nextInt(500, 1000);
                    Thread.sleep(sleepTime);
                System.out.printf("El fil principal et saluda per %d vegada.\n", i);
            }
        } catch (InterruptedException e) {
            System.out.println("El fil principal ha segut interromput.");
        }
    }
}