package ud3.exercises.chat.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatServer extends Thread {
    ServerSocket server;

    List<ChatHandler> clients;
    boolean running;

    public ChatServer(int port) throws IOException {
        server = new ServerSocket(port);
        clients = new ArrayList<>();
        running = true;
    }

    public void close(){
        running = false;
        this.interrupt();
    }

    public synchronized void removeClient(ChatHandler client){
        clients.remove(client);
    }

    public void sendMessage(String msg){
        for(ChatHandler client : clients) {
            client.sendMessage(msg);
        }
    }

    public void sendMessage(String alias, String msg){
        clients.stream()
                .filter(client -> !client.getNom().equals(alias))
                .forEach(client -> client.sendMessage(msg));
    }

    @Override
    public void run() {
        while (running){
            try {
                Socket client = server.accept();
                ChatHandler handler = new ChatHandler(client, this);
                clients.add(handler);
                handler.start();
                System.out.println("Nova connexió acceptada.");
            } catch (IOException e) {
                System.err.println("Error while accepting new connection");
                System.err.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            ChatServer server = new ChatServer(1234);
            server.start();

            scanner.nextLine();

            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}