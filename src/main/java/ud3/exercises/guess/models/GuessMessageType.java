package ud3.exercises.guess.models;

import java.io.Serializable;

public enum GuessMessageType implements Serializable {
    READY,
    GENERATE_NUMBER,
    GUESS,
    TOO_MANY_ATTEMPTS,
    TOO_LOW,
    TOO_HIGH,
    CORRECT,
    INVALID
}