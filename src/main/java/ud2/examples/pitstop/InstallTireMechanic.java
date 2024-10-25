package ud2.examples.pitstop;

public class InstallTireMechanic extends Mechanic {
    private Tire tire;

    public InstallTireMechanic(Car car, Tire tire) {
        super(car);
        this.tire = tire;
    }

    @Override
    public void run() {
        try {
            this.car.installTire(tire);
            System.out.printf("Tire %s installed.\n", tire);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
