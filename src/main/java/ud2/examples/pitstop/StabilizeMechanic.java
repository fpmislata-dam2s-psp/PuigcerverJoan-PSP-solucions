package ud2.examples.pitstop;

public class StabilizeMechanic extends Mechanic {
    public StabilizeMechanic(Car car) {
        super(car);
    }

    @Override
    public void run()  {
        try {
            String name = Thread.currentThread().getName();
            this.car.hold();
            System.out.printf("%s is holding the car.",name);
            this.car.letgo();
            System.out.printf("%s has let go the car.", name);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
