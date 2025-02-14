package ud3.exercises.guess.client;

import ud3.exercises.chat.client.ChatClientListener;
import ud3.exercises.guess.models.GuessMessage;
import ud3.exercises.guess.models.GuessMessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class GuessClient {
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final Scanner kbd;

    private boolean correct;
    private boolean attemptsLeft;

    public GuessClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        kbd = new Scanner(System.in);
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

    public void play() throws IOException, ClassNotFoundException {
        boolean keepPlaying = true;
        while(keepPlaying){
            this.generateNumber();
            this.waitServerIsReady();

            this.correct = false;
            this.attemptsLeft = true;
            while (!correct && this.attemptsLeft){
                this.makeGuess();
                this.processResponse();
            }

            keepPlaying = this.askUserKeepPlaying();
        }
    }

    private void generateNumber() throws IOException {
        GuessMessage generateNumberMessage = new GuessMessage(GuessMessageType.GENERATE_NUMBER);
        this.sendMessage(generateNumberMessage);
    }

    private void waitServerIsReady() throws IOException, ClassNotFoundException {
        boolean isServerReady = false;
        while (!isServerReady){
            GuessMessage readyMessage = receiveMessage();
            isServerReady = readyMessage.getType() == GuessMessageType.READY;
        }
        System.out.println("Server is ready!");
    }

    private void makeGuess() throws IOException {
        System.out.println("Introdueix un nombre del 1 al 1000:");
        int n = kbd.nextInt();
        kbd.nextLine();

        GuessMessage message = new GuessMessage(GuessMessageType.GUESS, n);
        sendMessage(message);
    }

    private void processResponse() throws IOException, ClassNotFoundException {
        GuessMessage message = receiveMessage();
        if (message.getType() == GuessMessageType.CORRECT) {
            System.out.println("Correcte!");
            this.correct = true;
        } else if (message.getType() == GuessMessageType.TOO_LOW)
            System.out.println("Massa baix");
        else if (message.getType() == GuessMessageType.TOO_HIGH)
            System.out.println("Massa alt");
        else if (message.getType() == GuessMessageType.INVALID)
            System.out.println("Nombre invàlid");
        else if (message.getType() == GuessMessageType.TOO_MANY_ATTEMPTS){
            this.attemptsLeft = false;
            int n = message.getN();
            System.out.println("Has superat el nombre màxim d'intents.");
            System.out.printf("La resposta era: %d\n", n);
        }
    }

    private boolean askUserKeepPlaying(){
        System.out.println("Vols jugar de nou? (s/N)");
        String option = kbd.nextLine();

        return option.equalsIgnoreCase("s");
    }

    public static void main(String[] args) {
        try {
            String host = "localhost";
            int port = 1234;
            GuessClient client = new GuessClient(host, port);
            client.play();
        } catch (ConnectException e) {
            System.err.println("Connection refused!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
