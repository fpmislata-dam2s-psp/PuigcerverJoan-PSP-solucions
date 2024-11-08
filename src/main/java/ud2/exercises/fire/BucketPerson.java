package ud2.exercises.fire;

public class BucketPerson extends Thread {
    private final int capacity;
    private final int delay;
    private final SwimmingPool swimmingPool;

    public BucketPerson(int capacity, int delay, SwimmingPool swimmingPool) {
        this.capacity = capacity;
        this.delay = delay;
        this.swimmingPool = swimmingPool;
    }

    @Override
    public void run() {
        try {
            while(true){
                    Thread.sleep(delay);
                    swimmingPool.fill(capacity);
                    System.out.printf("BucketPerson (%d) has filled swimming pool (%d/%d)\n", capacity, swimmingPool.getActualCapacity(), swimmingPool.getTotalCapacity());
            }
        } catch (InterruptedException e) {
            System.out.printf("BucketPerson (%d) has been interrupted\n", capacity);
        }
    }
}
