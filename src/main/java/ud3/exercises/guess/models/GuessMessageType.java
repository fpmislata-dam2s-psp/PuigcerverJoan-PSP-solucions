package ud3.exercises.guess.models;

import java.io.Serializable;

public enum GuessMessageType implements Serializable {
    READY,
    GUESS,
    TOO_LOW,
    TOO_HIGH,
    CORRECT,
    INVALID
}