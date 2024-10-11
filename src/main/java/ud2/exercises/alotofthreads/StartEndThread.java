package ud2.exercises.alotofthreads;

import java.util.concurrent.ThreadLocalRandom;

public class StartEndThread extends Thread {
    public StartEndThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            int sleepTime = ThreadLocalRandom.current().nextInt(1000, 10000);
            System.out.printf("%s: Comença execució (%.2f segons).\n", Thread.currentThread().getName(), sleepTime/1000.0);
            Thread.sleep(sleepTime);
            System.out.printf("%s: Acaba execució.\n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.printf("%s ha segut interromput\n", Thread.currentThread().getName());
        }
    }
}
