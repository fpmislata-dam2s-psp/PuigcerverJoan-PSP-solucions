package ud2.examples.pitstop;

public class Mechanic extends Thread {
    protected Car car;

    public Mechanic(Car car) {
        this.car = car;
    }
}
