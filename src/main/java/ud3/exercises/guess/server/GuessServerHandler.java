package ud3.exercises.guess.server;

import ud3.exercises.guess.models.GuessMessage;
import ud3.exercises.guess.models.GuessMessageType;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe que gestiona la comunicació del servidor
 * amb un únic client en un fil d'execució independent.
 */
public class GuessServerHandler extends Thread {
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    private int n;

    public GuessServerHandler(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void close() throws IOException {
        socket.close();
    }

    private void sendMessage(GuessMessage message) throws IOException {
        System.out.println("-> " + message);
        this.out.writeObject(message);
    }

    private GuessMessage receiveMessage() throws IOException, ClassNotFoundException {
        GuessMessage m = (GuessMessage) this.in.readObject();
        System.out.println("<- " + m);
        return m;
    }

    /**
     * Fil d'execució independent per cada client.
     * <p>
     * Abans que res, el client s'identifica amb un nom.
     * Després, el servidor mostra els missatges que cada client ha enviat.
     */
    @Override
    public void run() {
        try {
            // Generar nombre aleatòri entre 0 i 1000
            this.n = ThreadLocalRandom.current().nextInt(0, 1000);

            // Enviar READY al client
            GuessMessage message = new GuessMessage(GuessMessageType.READY);
            sendMessage(message);

            boolean correct = false;
            GuessMessage clientMessage;
            while ((clientMessage = receiveMessage()) != null && !correct){
                if (clientMessage.getType() == GuessMessageType.GUESS) {
                    correct = processGuess(clientMessage);
                } else {
                    System.err.println("Invalid request.");
                }
            }

            close();
        } catch (IOException e) {
            System.err.println("Error while handling client.");
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Unknown message recived.");
        }
    }

    private boolean processGuess(GuessMessage message) throws IOException {
        int guessedN = message.getN();

        boolean correct = false;
        GuessMessageType type;
        if (guessedN < 0 || guessedN > 1000)
            type = GuessMessageType.INVALID;
        else if(guessedN == this.n) {
            type = GuessMessageType.CORRECT;
            correct = true;
        } else if (guessedN > this.n)
            type = GuessMessageType.TOO_HIGH;
        else
            type = GuessMessageType.TOO_LOW;

        GuessMessage response = new GuessMessage(type);
        sendMessage(response);

        return correct;
    }
}
