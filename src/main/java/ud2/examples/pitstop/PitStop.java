package ud2.examples.pitstop;

import java.util.ArrayList;
import java.util.List;

public class PitStop {
    public static void main(String[] args) {
        Car car = new Car();
        car.drive(50);

        List<Mechanic> mechanics = new ArrayList<>();
        mechanics.add(new RaiseMechanic(car));
        mechanics.add(new RemoveTireMechanic(car, car.getFrontLeftTire()));
        mechanics.add(new RemoveTireMechanic(car, car.getFrontRightTire()));
        mechanics.add(new RemoveTireMechanic(car, car.getBackLeftTire()));
        mechanics.add(new RemoveTireMechanic(car, car.getBackRightTire()));

        for(Mechanic m : mechanics)
            m.start();

        for(Mechanic m : mechanics) {
            try {
                m.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
