package ud2.examples.pitstop;

import java.util.ArrayList;
import java.util.List;

public class PitStop {
    public static void main(String[] args) {
        Car car = new Car();
        car.drive(50);

        List<Mechanic> mechanics = new ArrayList<>();

        mechanics.add(new RaiseMechanic(car));

        mechanics.add(new StabilizeMechanic(car));
        mechanics.add(new StabilizeMechanic(car));

        // Front left tire
        mechanics.add(new ScrewMechanic(car, car.getFrontLeftTirePlace()));
        mechanics.add(new RemoveTireMechanic(car, car.getFrontLeftTirePlace()));
        mechanics.add(new InstallTireMechanic(car, car.getFrontLeftTirePlace(), new Tire("newFrontLeftTire", false)));

        // Front right tire
        mechanics.add(new ScrewMechanic(car, car.getFrontRightTirePlace()));
        mechanics.add(new RemoveTireMechanic(car, car.getFrontRightTirePlace()));
        mechanics.add(new InstallTireMechanic(car, car.getFrontRightTirePlace(), new Tire("newFrontRightTire", false)));

        // Back left tire
        mechanics.add(new ScrewMechanic(car, car.getBackLeftTirePlace()));
        mechanics.add(new RemoveTireMechanic(car, car.getBackLeftTirePlace()));
        mechanics.add(new InstallTireMechanic(car, car.getBackLeftTirePlace(), new Tire("newBackLeftTire", false)));

        // Back right tire
        mechanics.add(new ScrewMechanic(car, car.getBackRightTirePlace()));
        mechanics.add(new RemoveTireMechanic(car, car.getBackRightTirePlace()));
        mechanics.add(new InstallTireMechanic(car, car.getBackRightTirePlace(), new Tire("newBackRightTire", false)));

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
