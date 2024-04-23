package ud2.examples.semaphore;

import java.util.concurrent.ThreadLocalRandom;

public class StudentThread extends Thread {
    private final String name;
    private final Classroom classroom;

    public StudentThread(String name, Classroom classroom) {
        super.setName(name);
        this.name = name;
        this.classroom = classroom;
    }

    @Override
    public void run() {
        try {
            System.out.printf("%s is trying to take a seat\n", name);
            classroom.takeSeat();
            int sleepTime = ThreadLocalRandom.current().nextInt(2000, 5000);
            System.out.printf("%s is sitting for %.2f seconds\n", name, sleepTime / 1000.0);
            Thread.sleep(sleepTime);
            classroom.releaseSeat();
            System.out.printf("%s has left the classroom\n", name);
        } catch (InterruptedException e) {
            System.out.printf("%s has been interrupted\n", name);
        }
    }
}
