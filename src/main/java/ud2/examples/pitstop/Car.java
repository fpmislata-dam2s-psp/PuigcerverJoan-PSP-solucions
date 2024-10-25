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

    public void hold() throws InterruptedException {
        Thread.sleep(250);

        holding += 1;

        synchronized (this) {
            notifyAll();
        }
    }

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

    public void removeTire(TirePlace tp) throws InterruptedException {
        synchronized (this) {
            while(tp.getTire().isScrewed()) wait();
        }

        Thread.sleep(200);
        tp.setTire(null);

        synchronized (this) {
            notifyAll();
        }
    }

    public void installTire(TirePlace tp, Tire t) throws InterruptedException {
        synchronized (this) {
            while(tp.getTire() != null) wait();
        }

        Thread.sleep(200);
        tp.setTire(t);

        synchronized (this) {
            notifyAll();
        }
    }

    public void screew(TirePlace tp) throws InterruptedException {
        synchronized (this) {
            while(tp.getTire().getUsedPercentage() < 100) wait();
        }

        Thread.sleep(100);
        tp.getTire().setScrewed(false);

        synchronized (this) {
            notifyAll();
        }
    }

    public boolean allTiresNew(){
        for (Tire t : tires)
            if (t.getUsedPercentage() != 100) return false;
        return true;
    }

    public void release() throws InterruptedException {
        synchronized (this) {
            // @TODO Check screw
            while(!allTiresNew()) wait();
        }

        Thread.sleep(500);
        raised = false;

        synchronized (this) {
            notifyAll();
        }
    }

}
