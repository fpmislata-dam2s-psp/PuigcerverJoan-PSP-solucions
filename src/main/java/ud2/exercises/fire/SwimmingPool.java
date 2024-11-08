package ud2.exercises.fire;

public class SwimmingPool {
    private final int capacitatTotal;
    private int capacitatActual;

    public SwimmingPool(int capacitatTotal) {
        this.capacitatTotal = capacitatTotal;
        this.capacitatActual = 0;
    }

    public int getTotalCapacity() {
        return capacitatTotal;
    }

    public synchronized int getActualCapacity() {
        return capacitatActual;
    }

    public synchronized boolean isFull() {
        return capacitatActual >= capacitatTotal;
    }

    public synchronized void fill(int litres) throws InterruptedException {
        while (isFull()) wait();

        capacitatActual = Math.min(capacitatTotal, capacitatActual + litres);

        notifyAll();
    }

    public synchronized int empty(int litres) throws InterruptedException {
        while (!isFull()) wait();

        capacitatActual -= litres;

        notifyAll();
        return litres;
    }
}