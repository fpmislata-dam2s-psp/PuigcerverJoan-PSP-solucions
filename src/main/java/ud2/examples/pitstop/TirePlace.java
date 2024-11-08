package ud2.examples.pitstop;

public class TirePlace {
    private Tire tire;

    public TirePlace(Tire tire) {
        this.tire = tire;
    }

    public synchronized Tire getTire() {
        return tire;
    }

    public synchronized void setTire(Tire tire) {
        this.tire = tire;
    }
}
