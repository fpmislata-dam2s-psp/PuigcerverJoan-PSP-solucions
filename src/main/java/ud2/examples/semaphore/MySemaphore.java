package ud2.examples.semaphore;

public class MySemaphore {
    private final int totalSpots;
    private int occupiedSpots;

    public MySemaphore(int spots){
        this.totalSpots = spots;
        this.occupiedSpots = 0;
    }

    public synchronized void acquire() throws InterruptedException {
        while (occupiedSpots >= totalSpots) {
            System.out.printf("%s is waiting to acquire the semaphore.\n", Thread.currentThread().getName());
            wait();
        }
        System.out.printf("%s has acquired the semaphore.\n", Thread.currentThread().getName());
        occupiedSpots++;
    }

    public synchronized void release(){
        System.out.printf("%s has released the semaphore.\n", Thread.currentThread().getName());
        occupiedSpots--;
        notifyAll();
    }
}
