package ud2.examples.pitstop;

public class ScrewMechanic extends Mechanic {
    private Tire tire;

    public ScrewMechanic(Car car, Tire tire) {
        super(car);
        this.tire = tire;
    }

    @Override
    public void run() {
        try {
            this.car.unscrew(tire);
            System.out.printf("Tire %s replaced.\n", tire);
            this.car.screw(tire);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
