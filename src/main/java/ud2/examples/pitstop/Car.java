package ud2.examples.pitstop;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private final TirePlace frontLeftTirePlace;
    private final TirePlace frontRightTirePlace;
    private final TirePlace backLeftTirePlace;
    private final TirePlace backRightTirePlace;
    private final List<Tire> tires;

    private boolean raised;
    private int holding;

    public Car() {
        holding = 0;
        raised = false;
        tires = new ArrayList<>();

        Tire frontLeft = new Tire("oldFrontLeftTire", true);
        frontLeftTirePlace = new TirePlace(frontLeft);
        tires.add(frontLeft);

        Tire frontRight = new Tire("oldFrontRightTire", true);
        frontRightTirePlace = new TirePlace(frontRight);
        tires.add(frontRight);

        Tire backLeft = new Tire("oldBackLeftTire", true);
        backLeftTirePlace = new TirePlace(backLeft);
        tires.add(backLeft);

        Tire backRight = new Tire("oldBackRightTire", true);
        backRightTirePlace = new TirePlace(backRight);
        tires.add(backRight);
    }

    public void drive(int km){
        for(Tire t : tires)
            t.decreasePercentage(km);
    }

    public TirePlace getFrontLeftTirePlace() {
        return frontLeftTirePlace;
    }

    public TirePlace getFrontRightTirePlace() {
        return frontRightTirePlace;
    }

    public TirePlace getBackLeftTirePlace() {
        return backLeftTirePlace;
    }

    public TirePlace getBackRightTirePlace() {
        return backRightTirePlace;
    }

    // StabilizeMechanic
    public void hold() throws InterruptedException {
        Thread.sleep(250);


        synchronized (this) {
            holding += 1;
            notifyAll();
        }
    }

    public void letgo() throws InterruptedException {
        synchronized (this) {
            while(!(!raised && allTiresNewAndScrewed())) wait();
        }

        Thread.sleep(250);

        holding -= 1;

        synchronized (this) {
            notifyAll();
        }
    }

    // RaiseMechanic
    public void raise() throws InterruptedException {
        synchronized (this) {
            while(holding < 2) wait();
        }

        Thread.sleep(500);
        raised = true;

        synchronized (this) {
            notifyAll();
        }
    }

    public boolean allTiresNewAndScrewed() {
        for (Tire t : tires) {
            if (t.getUsedPercentage() < 100) return false;
            if (!t.isScrewed()) return false;
        }
        return true;
    }

    public void release() throws InterruptedException {
        synchronized (this) {
            while(!allTiresNewAndScrewed()) wait();
        }

        Thread.sleep(500);
        raised = false;

        synchronized (this) {
            notifyAll();
        }
    }

    // ScrewMechanic
    public void unscrew(TirePlace tp) throws InterruptedException {
        synchronized (this) {
            while(!raised) wait();
        }

        Thread.sleep(100);
        tp.getTire().setScrewed(false);

        synchronized (this) {
            notifyAll();
        }
    }

    public Tire removeTire(TirePlace tp) throws InterruptedException {
        synchronized (this) {
            while(tp.getTire().isScrewed()) wait();
        }

        Thread.sleep(200);
        Tire oldTire = tp.getTire();
        tp.setTire(null);

        synchronized (this) {
            tires.remove(oldTire);
            notifyAll();
        }

        return oldTire;
    }

    public void installTire(TirePlace tp, Tire t) throws InterruptedException {
        synchronized (this) {
            while(tp.getTire() != null) wait();
        }

        Thread.sleep(200);
        tp.setTire(t);

        synchronized (this) {
            tires.add(t);
            notifyAll();
        }
    }

    public void screw(TirePlace tp) throws InterruptedException {
        synchronized (this) {
            while(tp.getTire() == null || tp.getTire().getUsedPercentage() < 100) wait();
        }

        Thread.sleep(100);

        tp.getTire().setScrewed(true);

        synchronized (this) {
            notifyAll();
        }
    }

}
