package ud2.examples.thread;

import java.util.concurrent.ThreadLocalRandom;

class HelloThread extends Thread {
    public HelloThread(String name) {
        super(name);
    }

    @Override
    public void run(){
        try {
            for(int i = 0; i < 5; i++) {
                int sleepTime = ThreadLocalRandom.current().nextInt(500, 1000);
                Thread.sleep(sleepTime);
                System.out.printf("El fil %s et saluda per %d vegada.\n",
                        Thread.currentThread().getName(), i
                );
            }
        } catch (InterruptedException e) {
            System.out.printf("El fil %s ha segut interromput\n",
                    Thread.currentThread().getName()
            );
        }
    }
}