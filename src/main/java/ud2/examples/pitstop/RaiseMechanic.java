package ud2.examples.pitstop;

public class RaiseMechanic extends Mechanic {
    public RaiseMechanic(Car car) {
        super(car);
    }

    @Override
    public void run()  {
        try {
            System.out.println("Waiting for raising the car...");
            this.car.raise();
            System.out.println("Car raised!");
            this.car.release();
            System.out.println("Car released!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
