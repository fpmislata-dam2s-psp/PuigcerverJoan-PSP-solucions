package ud3.exercises.tictactoe.server;

import ud3.exercises.tictactoe.models.Board;
import ud3.exercises.tictactoe.models.BoardChoice;
import ud3.exercises.tictactoe.models.TicTacToeMessageType;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static ud3.exercises.tictactoe.models.TicTacToeMessageType.*;

public class TicTacToeGame extends Thread {
    private final int id;
    private final List<TicTacToeServerPlayerHandler> players;

    private final Board board;
    private boolean finished;
    private int currentPlayer;

    public TicTacToeGame(int id) {
        this.id = id;
        this.players = new ArrayList<>();
        board = new Board();
        finished = false;
    }

    public void addPlayer(Socket client) throws IOException {
        if (players.size() < 2)
            players.add(new TicTacToeServerPlayerHandler(client));
    }

    public boolean enoughPlayers(){
        return players.size() >= 2;
    }

    public TicTacToeServerPlayerHandler getCurrentPlayer(){
        return players.get(currentPlayer);
    }

    public void sendGlobalMessage(TicTacToeMessageType type) throws IOException{
        for(TicTacToeServerPlayerHandler player : players){
            player.sendMessage(type);
        }
    }
    public void sendGlobalMessage(TicTacToeMessageType type, String message) throws IOException{
        for(TicTacToeServerPlayerHandler player : players){
            player.sendMessage(type, message);
        }
    }
    public void sendGlobalMessage(TicTacToeMessageType type, String message, Object obj) throws IOException{
        for(TicTacToeServerPlayerHandler player : players){
            player.sendMessage(type, message, obj);
        }
    }

    /**
     * TODO: Send the choice to all players, so they update the board.
     */
    public void updateClientBoards(BoardChoice choice) throws IOException {
    }

    public void changeTurn(){
        currentPlayer = (currentPlayer + 1) % players.size();
    }

    private void close() throws IOException {
        for(TicTacToeServerPlayerHandler player : players){
            player.close();
        }
    }

    /**
     * Server side of TicTacToe game.
     * <p>
     * TODO: The server should keep track of:
     * - Comunication with both connected players.
     * - The TicTacToe Board. It should be updated given
     *      the players choices.
     * - It's a turn based game.
     * - Winner/Loser or draw.
     */
    @Override
    public void run(){
        try {
            // TODO
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
