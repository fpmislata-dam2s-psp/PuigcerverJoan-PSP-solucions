package ud2.examples.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Classroom {
    private final Semaphore seatSemaphore;

    public Classroom(int numberSeats) {
        this.seatSemaphore = new Semaphore(numberSeats);
    }

    public void takeSeat() throws InterruptedException {
        seatSemaphore.acquire();
    }

    public void emptySeat() {
        seatSemaphore.release();
    }

    public static void main(String[] args) {
        Classroom classroom = new Classroom(10);

        List<StudentThread> studentThreads = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            StudentThread studentThread = new StudentThread("Student" + i, classroom);
            studentThreads.add(studentThread);
            studentThread.start();
        }
    }
}
