package exam2.client;

import exam2.models.Request;
import exam2.models.RequestType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MessagingClientListener extends Thread {

    private final MessagingClient client;
    private final Socket socket;

    // TODO: ObjectStream related object
    private final ObjectInputStream objIn;


    public MessagingClientListener(Socket socket, MessagingClient client) throws IOException {
        this.client = client;
        this.socket = socket;
        // TODO: ObjectStream related object
        objIn = new ObjectInputStream(socket.getInputStream());
    }
    /**
     * TODO: Llegeix una petició
     * @return Petició llegida
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Request readRequest() throws IOException, ClassNotFoundException {
        // TODO
        Request r = (Request) objIn.readObject();
        System.out.println("<-- " + r);
        return r;
    }

    // TODO: Reb missatges
    @Override
    public void run() {
        try {
            Request request;
            while((request = readRequest()) != null){
                if (request.getType() == RequestType.SEND){
                    // TODO: Acció del client a les respostes del tipus SEND
                    System.out.printf("%s: %s\n", request.getAlias(), request.getMessage());
                }
                else if (request.getType() == RequestType.SUCCESS){
                    // TODO: Acció del client a les respostes del tipus SUCCESS
                    System.out.printf("%s\n", request.getMessage());
                }
                else if (request.getType() == RequestType.ERROR){
                    // TODO: Acció del client a les respostes del tipus ERROR
                    System.out.printf("ERROR: %s\n", request.getMessage());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
