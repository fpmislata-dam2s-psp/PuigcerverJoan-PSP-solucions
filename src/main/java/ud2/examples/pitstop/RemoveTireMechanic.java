package ud2.examples.pitstop;

public class RemoveTireMechanic extends Mechanic {
    private final TirePlace tirePlace;
    private Tire tire;

    public RemoveTireMechanic(Car car, TirePlace tirePlace) {
        super(car);
        this.tirePlace = tirePlace;
        this.tire = null;
    }

    @Override
    public void run() {
        try {
            this.tire = this.car.removeTire(tirePlace);
            System.out.printf("Tire %s removed.\n", tire);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
