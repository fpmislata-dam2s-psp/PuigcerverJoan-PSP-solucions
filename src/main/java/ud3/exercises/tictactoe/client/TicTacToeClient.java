package ud3.exercises.tictactoe.client;

import ud3.exercises.tictactoe.models.Board;
import ud3.exercises.tictactoe.models.BoardChoice;
import ud3.exercises.tictactoe.models.TicTacToeMessage;
import ud3.exercises.tictactoe.models.TicTacToeMessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import static ud3.exercises.tictactoe.models.TicTacToeMessageType.*;

public class TicTacToeClient {
    private final Socket socket;
    private final Scanner scanner;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    private Board board;
    private boolean finished;

    public TicTacToeClient(String host, int port) throws IOException {
        this.scanner = new Scanner(System.in);
        this.socket = new Socket(host, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.board = new Board();
        this.finished = false;
    }

    private TicTacToeMessage readMessage() throws IOException, ClassNotFoundException {
        return (TicTacToeMessage) in.readObject();
    }
    private void sendMessage(TicTacToeMessageType type, String message, Object obj) throws IOException {
        TicTacToeMessage ticTacToeMessage = new TicTacToeMessage(type, message);
        ticTacToeMessage.setObject(obj);
        out.writeObject(ticTacToeMessage);
    }

    /**
     * TODO: Ask for the position where the user wants to play.
     * Send the user's choice to the server.
     */
    private void playMove() throws IOException {
    }

    /**
     * Client-side TicTacToe game.
     * <p>
     * TODO: This method should recevie a TicTacToeMessage from the server and implement
     * the accions based on the message type.
     *
     */
    public void game() throws IOException, ClassNotFoundException {
    }

    public static void main(String[] args) {
        System.out.println("Connectant-se amb el servidor...");
        try {
            TicTacToeClient ticTacToeClient = new TicTacToeClient("localhost", 1234);
            ticTacToeClient.game();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}