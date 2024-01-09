package ud3.exercises.tictactoe.models;

import java.io.Serializable;

public enum TicTacToeMessageType implements Serializable {
    INFO,
    ERROR,
    START_GAME,
    INVALID_CHOICE,
    START_TURN,
    WAIT_TURN,
    POST,
    UPDATE_BOARD,
    END_GAME
}
