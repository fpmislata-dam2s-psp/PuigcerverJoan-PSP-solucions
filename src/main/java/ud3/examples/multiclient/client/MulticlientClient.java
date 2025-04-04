package ud3.examples.multiclient.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class MulticlientClient {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            int port = 1234;
            System.out.println("Creant el Socket client.");
            Socket socket = new Socket(host, port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Es pot utilitzar l'opció autoflush
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            // Identifiquem el client en el servidor.
            // El servidor el primer que fa és esperar el nom
            System.out.print("Introdueix el teu nom: ");
            String nom = scanner.nextLine();
            out.println(nom);

            // El client pot enviar missatges fins que escriga END
            String line;
            System.out.print("Text: ");
            while(!(line = scanner.nextLine()).equals("END")){
                out.println(line);
                System.out.print("Text: ");
            }
            // Tanquem la connexió al acabar
            socket.close();
        } catch (ConnectException e) {
            System.err.println("Connection refused!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
