package ud3.exercises.kahoot.server;

import ud3.exercises.kahoot.models.KahootRequest;
import ud3.exercises.kahoot.models.KahootRequestType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class KahootServerHandler extends Thread {
    private KahootServer server;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public KahootServerHandler(Socket socket, KahootServer server) throws IOException {
        this.server = server;
        this.socket = socket;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
    }

    private KahootRequest readRequest() throws IOException, ClassNotFoundException {
        return (KahootRequest) in.readObject();
    }
    private void sendRequest(KahootRequest request) throws IOException {
        out.writeObject(request);
    }

    private void crearPartida() throws IOException {
        KahootPartida p = new KahootPartida(this);
        server.addPartida(p);
        sendRequest(new KahootRequest(KahootRequestType.SUCCESS, p.getCodi()));
    }

    private void unirsePartida(){

    }

    @Override
    public void run(){
        try {
            KahootRequest request;
            while ((request = readRequest()) != null){
                if (request.getType() == KahootRequestType.CREAR_PARTIDA){
                    crearPartida();
                } else if (request.getType() == KahootRequestType.UNIRSE_PARTIDA) {
                    unirsePartida();
                }
            }
        } catch (IOException | ClassNotFoundException e){
            System.err.println(e);
        }
    }
}
