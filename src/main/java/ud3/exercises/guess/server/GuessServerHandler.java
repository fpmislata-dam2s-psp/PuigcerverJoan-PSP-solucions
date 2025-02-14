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
    private int counter;

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
            GuessMessage clientMessage;
            while ((clientMessage = receiveMessage()) != null){
                switch (clientMessage.getType()){
                    case GUESS -> {
                        processGuess(clientMessage);
                    }
                    case GENERATE_NUMBER -> {
                        this.generateNumber();
                        this.sendReady();
                    }
                    default -> {
                        System.err.println("Invalid request.");
                    }
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

    private void processGuess(GuessMessage message) throws IOException {
        int guessedN = message.getN();
        this.counter++;

        if (this.counter >= 2) {
            GuessMessage response = new GuessMessage(GuessMessageType.TOO_MANY_ATTEMPTS, this.n);
            this.sendMessage(response);
        } else
            this.checkIfGuessIsCorrect(guessedN);
    }

    private void checkIfGuessIsCorrect(int n) throws IOException {
        GuessMessageType type;
        if (n < 0 || n > 1000)
            type = GuessMessageType.INVALID;
        else if(n == this.n) {
            type = GuessMessageType.CORRECT;
        } else if (n > this.n)
            type = GuessMessageType.TOO_HIGH;
        else
            type = GuessMessageType.TOO_LOW;

        GuessMessage response = new GuessMessage(type);
        sendMessage(response);
    }

    private void generateNumber(){
        // Generar nombre aleatòri entre 0 i 1000
        this.n = ThreadLocalRandom.current().nextInt(0, 1000);
        this.counter = 0;
    }

    private void sendReady() throws IOException {
        // Enviar READY al client
        GuessMessage message = new GuessMessage(GuessMessageType.READY);
        sendMessage(message);
    }
}
