package ud2.practices.swimmingpool;

import java.util.concurrent.ThreadLocalRandom;

public class PersonThread extends Thread {
    private SwimmingPool pool;

    public PersonThread(String name, SwimmingPool pool) {
        super(name);
        this.pool = pool;
    }

    /**
     * TODO: The persons rests between 1 and 5 seconds
     * @throws InterruptedException
     */
    public void rest() throws InterruptedException {
        int milis = ThreadLocalRandom.current().nextInt(1000, 5000);
        System.out.printf("%s està descansant %.2f segons.\n", getName(), milis / 1000.0);
        Thread.sleep(milis);
    }

    /**
     * TODO: The persons takes a shower for 2 seconds
     * - Tries to get into a shower
     * - Takes a shower
     * - Leaves the showers
     * @throws InterruptedException
     */
    public void takeShower() throws InterruptedException {
        int milis = 2000;
        System.out.printf("%s vol dutxar-se.\n", getName());
        pool.getShowersSemaphore().acquire();
        System.out.printf("%s està dutxant-se.\n", getName());
        Thread.sleep(milis);
        System.out.printf("%s ha acabat de dutxar-se.\n", getName());
        pool.getShowersSemaphore().release();
    }

    /**
     * TODO: The persons swims between 1 and 10 seconds
     * - Tries to get into the swimming pool
     * - Swims
     * - Leaves the swimming pool
     * @throws InterruptedException
     */
    public void swim() throws InterruptedException {
        int milis = ThreadLocalRandom.current().nextInt(1000, 10000);
        System.out.printf("%s vol nadar.\n", getName());
        pool.getPoolSemaphore().acquire();
        System.out.printf("%s està nadant %.2f segons.\n", getName(), milis / 1000.0);
        Thread.sleep(milis);
        System.out.printf("%s ha eixit de la piscina.\n", getName());
        pool.getPoolSemaphore().release();
    }

    @Override
    public void run() {
        while(true) {
            // TODO: Rests
            // TODO: Takes a shower
            // TODO: Swims
            try {
                rest();
                takeShower();
                swim();
            } catch (InterruptedException e) {
                System.out.printf("%s ha abandonat les instal·lacions.\n", getName());
            }
        }
    }
}
