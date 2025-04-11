package ud2.exercises.sequence;

import java.util.ArrayList;
import java.util.List;

public class Sequence {
    private final int totalLoops;
    private int currentLoop;
    private int currentIndex;
    private final List<SequenceThread> threads;

    public Sequence(int loops) {
        this.threads = new ArrayList<>();
        this.totalLoops = loops;
        this.currentLoop = 0;
        this.currentIndex = 0;
    }

    public void add(SequenceThread thread){
        thread.setSequence(this);
        this.threads.add(thread);
    }

    public synchronized boolean isRunning(){
        return this.currentLoop < this.totalLoops;
    }

    public void printLetter(int index, char letter) throws InterruptedException {
        synchronized (this){
            while (index != currentIndex) wait();
        }
        System.out.println(letter);
        System.out.flush();
        synchronized (this) {
            this.incrementIndex();
            notifyAll();
        }
    }

    public void incrementIndex(){
        this.currentIndex++;

        if (currentIndex >= this.threads.size()){
            this.currentIndex = 0;
            this.currentLoop++;
            System.out.println("LOOP " + currentLoop);
            System.out.flush();
        }
    }

    public void start(){
        for(SequenceThread t : threads)
            t.start();
    }

    public void join() throws InterruptedException {
        for(SequenceThread t : threads)
            t.join();
    }

    public static void main(String[] args) throws InterruptedException {
        Sequence sequence = new Sequence(3);
        sequence.add(new SequenceThread('A'));
        sequence.add(new SequenceThread('D'));
        sequence.add(new SequenceThread('C'));
        sequence.start();
        sequence.join();
        System.out.println("La seqüència ha acabat");
    }
}
