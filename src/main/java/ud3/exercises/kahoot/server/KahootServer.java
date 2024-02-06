package ud3.exercises.kahoot.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KahootServer {
    private ServerSocket server;
    private List<KahootServerHandler> handlers;
    private Map<String, KahootPartida> partides;
    public KahootServer(int port) throws IOException {
        server = new ServerSocket(port);
        handlers = new ArrayList<>();
    }

    public void addPartida(KahootPartida p){
        String codi = String.valueOf(partides.size());
        p.setCodi(codi);
        partides.put(codi, p);
    }

    public void run(){
        while (true){
            try {
                Socket s = server.accept();
                KahootServerHandler h = new KahootServerHandler(s, this);
                h.start();
                handlers.add(h);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        KahootServer server = null;
        try {
            server = new KahootServer(1234);
            server.run();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
