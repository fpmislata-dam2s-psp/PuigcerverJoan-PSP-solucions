package ud2.exercises.mysemaphore;

public class MySemaphore {
    private int totalPermits;
    private int usedPermits;
    private int queueLength;

    public MySemaphore(int permits) {
        this.totalPermits = permits;
        this.usedPermits = 0;
        this.queueLength = 0;
    }

    public synchronized void acquire() throws InterruptedException {
        queueLength++;
        while (usedPermits >= totalPermits) wait();
        queueLength--;

        usedPermits++;
    }

    public synchronized void release(){
        usedPermits--;

        if (usedPermits < totalPermits)
            notify();
    }

    public synchronized int getFreePermits(){
        return totalPermits - usedPermits;
    }

    public synchronized int getQueueLength(){
        return queueLength;
    }

    public synchronized void setPermits(int permits){
        int oldPermits = this.totalPermits;
        this.totalPermits = permits;

        if (oldPermits < totalPermits)
            notifyAll();
    }
}
