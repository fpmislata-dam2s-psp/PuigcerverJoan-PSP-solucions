package ud2.exercises.fire;

public class Helicopter extends Thread {
    private final int totalCapacity;
    private int actualCapacity;

    private final SwimmingPool swimmingPool;
    private final Fire fire;

    public Helicopter(int totalCapacity, SwimmingPool swimmingPool, Fire fire) {
        this.totalCapacity = totalCapacity;
        this.actualCapacity = 0;
        this.swimmingPool = swimmingPool;
        this.fire = fire;
    }

    public void fill(int litres) {
        actualCapacity += litres;
    }

    public int empty() {
        int actualCapacity = this.actualCapacity;
        this.actualCapacity = 0;
        return actualCapacity;
    }

    @Override
    public void run() {
        try {
            while(!fire.isExtinguished()){
                System.out.printf("Helicopter (%d/%d) wants to fill from swimming pool\n", actualCapacity, totalCapacity);
                int liters = swimmingPool.empty(totalCapacity);
                fill(liters);
                System.out.printf("Helicopter (%d/%d) has filled from swimming pool\n", actualCapacity, totalCapacity);

                Thread.sleep(5000);

                fire.extinguish(empty());
                System.out.printf("Helicopter (%d/%d) has emptied\n", actualCapacity, totalCapacity);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
