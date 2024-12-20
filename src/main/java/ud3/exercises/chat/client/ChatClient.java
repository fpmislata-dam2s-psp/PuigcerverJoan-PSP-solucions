package ud3.exercises.chat.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private final Socket socket;
    private final PrintWriter out;
    private final ChatClientListener listener;

    public ChatClient(String host, int port) throws IOException {
        System.out.println("Creant el Socket client.");
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);

        listener = new ChatClientListener(socket.getInputStream());
        listener.start();
    }

    public void chat() throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Identifiquem el client en el servidor.
        // El servidor el primer que fa és esperar el nom
        System.out.print("Introdueix el teu nom: ");
        String nom = scanner.nextLine();
        out.println(nom);

        // El client pot enviar missatges fins que escriga END
        String line;
        while(!(line = scanner.nextLine()).equals("END")){
            out.println(line);
        }
        // Tanquem la connexió al acabar
        socket.close();
    }

    public static void main(String[] args) {
        try {
            String host = "localhost";
            int port = 1234;
            ChatClient client = new ChatClient(host, port);
            client.chat();
        } catch (ConnectException e) {
            System.err.println("Connection refused!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
