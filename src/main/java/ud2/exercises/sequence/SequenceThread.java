package ud2.exercises.sequence;

public class SequenceThread extends Thread {
    private final char letter;
    private int index;
    private Sequence sequence;

    public SequenceThread(char letter) {
        this.letter = letter;
        setName(Character.toString(letter));
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void run(){
        try {
            while (sequence.isRunning()){
                sequence.printLetter(index, letter);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
