package ud2.examples.pitstop;

public class RemoveTireMechanic extends Mechanic {
    private Tire tire;

    public RemoveTireMechanic(Car car, Tire tire) {
        super(car);
        this.tire = tire;
    }

    @Override
    public void run() {
        try {
            this.car.removeTire(tire);
            System.out.printf("Tire %s removed.\n", tire);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
