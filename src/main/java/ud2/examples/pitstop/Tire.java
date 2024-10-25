package ud2.examples.pitstop;

public class Tire {
    private int usedPercentage;
    private final String name;
    private boolean screwed;

    public Tire(String name, boolean screwed){
        this.name = name;
        this.usedPercentage = 100;
        this.screwed = screwed;
    }

    public void replace(){
        usedPercentage = 100;
    }

    public int getUsedPercentage() {
        return usedPercentage;
    }

    public void decreasePercentage(int percentage){
        this.usedPercentage -= percentage;
    }

    public boolean isScrewed() {
        return screwed;
    }

    public void setScrewed(boolean screwed) {
        this.screwed = screwed;
    }

    @Override
    public String toString() {
        return "Tire{" +
                "name='" + name + '\'' +
                '}';
    }
}
