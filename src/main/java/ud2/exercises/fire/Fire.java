package ud2.exercises.fire;

public class Fire {
    private int remainingLiters;

    public Fire(int remainingLiters) {
        this.remainingLiters = remainingLiters;
    }

    public void extinguish(int liters) {
        remainingLiters -= liters;
    }

    public boolean isExtinguished() {
        return remainingLiters <= 0;
    }
}
