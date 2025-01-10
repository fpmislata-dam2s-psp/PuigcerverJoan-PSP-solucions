package ud3.exercises.guess.models;

import java.io.Serializable;

public class GuessMessage implements Serializable {
    private final GuessMessageType type;
    private final int n;

    public GuessMessage(GuessMessageType type, int n) {
        this.type = type;
        this.n = n;
    }

    public GuessMessage(GuessMessageType type) {
        this(type, -1);
    }

    public GuessMessageType getType() {
        return type;
    }

    public int getN() {
        return n;
    }

    @Override
    public String toString() {
        return "GuessMessage{" +
                "type=" + type +
                ", n=" + n +
                '}';
    }
}
