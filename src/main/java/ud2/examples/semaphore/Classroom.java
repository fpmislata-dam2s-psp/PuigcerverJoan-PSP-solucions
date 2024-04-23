package ud2.examples.semaphore;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    private final MySemaphore seatSemaphore;
    private final int totalNumberSeats;
    private int availableNumberSeats;

    public Classroom(int numberSeats) {
        this.totalNumberSeats = numberSeats;
        this.availableNumberSeats = numberSeats;
        this.seatSemaphore = new MySemaphore(numberSeats);
    }

    public synchronized void takeSeat() throws InterruptedException {
        seatSemaphore.acquire();
        this.availableNumberSeats--;
        System.out.printf("%s has taken a seat. Available seats %d/%d\n", Thread.currentThread().getName(), availableNumberSeats, totalNumberSeats);
    }

    public void releaseSeat() {
        seatSemaphore.release();
        this.availableNumberSeats++;
        System.out.printf("%s has released a seat. Available seats %d/%d\n", Thread.currentThread().getName(), availableNumberSeats, totalNumberSeats);
    }

    public static void main(String[] args) throws InterruptedException {
        Classroom classroom = new Classroom(2);

        List<StudentThread> studentThreads = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            StudentThread studentThread = new StudentThread("Student" + i, classroom);
            studentThreads.add(studentThread);
            studentThread.start();
        }

        for(StudentThread st : studentThreads){
            st.join();
        }
    }
}
