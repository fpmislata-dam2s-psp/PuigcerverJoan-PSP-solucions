package ud2.examples.pitstop;

public class Tire {
    private int remainingKilometers;
    private final String name;

    public Tire(String name) {
        this.name = name;
        this.remainingKilometers = 100;
    }

    public void replace(){
        remainingKilometers = 100;
    }

    public int getRemainingKilometers() {
        return remainingKilometers;
    }

    public void decreaseKilometers(int km){
        this.remainingKilometers -= km;
    }

    @Override
    public String toString() {
        return "Tire{" +
                "name='" + name + '\'' +
                '}';
    }
}
