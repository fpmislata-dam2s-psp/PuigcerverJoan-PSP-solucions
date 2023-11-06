package ud2.practices.swimmingpool;

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
        int milis = 0;
        System.out.printf("%s està descansant %.2f segons.", getName(), milis / 1000.0);
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
        System.out.printf("%s vol dutxar-se.", getName());
        System.out.printf("%s està dutxant-se.", getName());
        System.out.printf("%s ha acabat de dutxar-se.", getName());
    }

    /**
     * TODO: The persons swims between 1 and 10 seconds
     * - Tries to get into the swimming pool
     * - Swims
     * - Leaves the swimming pool
     * @throws InterruptedException
     */
    public void swim() throws InterruptedException {
        int milis = 0;
        System.out.printf("%s vol nadar.", getName());
        System.out.printf("%s està nadant %.2f segons.", getName(), milis / 1000.0);
        System.out.printf("%s ha eixit de la piscina.", getName());
    }

    @Override
    public void run() {
        while(true) {
            // TODO: Rests
            // TODO: Takes a shower
            // TODO: Swims
        }
        System.out.printf("%s ha abandonat les instal·lacions.", getName());
    }
}
