package ud3.exercises.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Classe que gestiona la comunicació del servidor
 * amb un únic client en un fil d'execució independent.
 */
public class ChatServerHandler extends Thread {
    private final ChatServer server;
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    private String nom;

    public ChatServerHandler(Socket socket, ChatServer server) throws IOException {
        this.server = server;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        nom = null;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void close() throws IOException {
        socket.close();
    }

    public void sendMessage(String message){
        this.out.println(message);
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
            String nom = in.readLine();
            setNom(nom);
            System.out.printf("%s s'ha identificat.\n", getNom());

            // Quan un client es desconecta, l'operació readLine() retorna null
            String message;
            while((message = in.readLine()) != null){
                String formattedMessage = String.format("%s: %s", getNom(), message);
                System.out.println(formattedMessage);
                server.broadcastMessage(formattedMessage);
            }
            System.out.printf("%s has disconnected.\n", getNom());
            close();
        } catch (IOException e) {
            System.err.println("Error while handling client.");
            System.err.println(e.getMessage());
        }
    }
}
