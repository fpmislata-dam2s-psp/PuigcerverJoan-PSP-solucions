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
            System.out.printf("Tire %s unscrewed.\n", tirePlace.getTire());
            this.car.screw(tirePlace);
            System.out.printf("Tire %s screwed.\n", tirePlace.getTire());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
