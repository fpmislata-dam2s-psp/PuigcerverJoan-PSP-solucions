package ud2.examples.pitstop;

public class InstallTireMechanic extends Mechanic {
    private final TirePlace tirePlace;
    private Tire tire;

    public InstallTireMechanic(Car car, TirePlace tirePlace, Tire tire) {
        super(car);
        this.tirePlace = tirePlace;
        this.tire = tire;
    }

    @Override
    public void run() {
        try {
            this.car.installTire(tirePlace, tire);
            System.out.printf("Tire %s installed.\n", tire);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
