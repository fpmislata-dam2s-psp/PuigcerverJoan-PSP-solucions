package ud2.examples.pitstop;

public class ScrewMechanic extends Mechanic {
    private final TirePlace tirePlace;

    public ScrewMechanic(Car car, TirePlace tirePlace) {
        super(car);
        this.tirePlace = tirePlace;
    }

    @Override
    public void run() {
        try {
            this.car.unscrew(tirePlace);
            System.out.printf("Tire %s replaced.\n", tirePlace.getTire());
            this.car.screw(tirePlace);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
