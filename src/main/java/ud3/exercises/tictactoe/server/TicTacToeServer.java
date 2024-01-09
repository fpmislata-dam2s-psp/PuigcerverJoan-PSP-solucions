package ud3.exercises.tictactoe.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicTacToeServer extends Thread {
    ServerSocket server;

    List<TicTacToeGame> games;
    TicTacToeGame nextGame;
    boolean running;

    public TicTacToeServer(int port) throws IOException {
        server = new ServerSocket(port);
        games = new ArrayList<>();
        nextGame = new TicTacToeGame(0);
        running = true;
    }

    public void close(){
        running = false;
        this.interrupt();
    }

    /**
     * TODO: Execució del servidor.
     * <p>
     * El servidor accepta les connexions dels clients.
     * Quan dos jugadors es connecten, els emparella per començar una partida.
     */
    @Override
    public void run() {
        while (running){
            try {
                // TODO
            } catch (IOException e) {
                System.err.println("Error while accepting new connection");
                System.err.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            TicTacToeServer server = new TicTacToeServer(1234);
            server.start();

            scanner.nextLine();

            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}