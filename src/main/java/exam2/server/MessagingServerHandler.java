package exam2.server;

import exam2.models.Request;
import exam2.models.RequestType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessagingServerHandler extends Thread {
    private final MessagingServer server;
    private final Socket client;

    // TODO: ObjectStream related object
    private final ObjectOutputStream objOut;
    private final ObjectInputStream objIn;

    private String alias;

    public MessagingServerHandler(Socket client, MessagingServer server) throws IOException {
        this.server = server;
        this.client = client;
        // TODO: ObjectStream related object
        objIn = new ObjectInputStream(client.getInputStream());
        objOut = new ObjectOutputStream(client.getOutputStream());
        alias = "";
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * TODO: Envia una petició
     * @param request Petició a enviar
     * @throws IOException
     */
    public void sendRequest(Request request) throws IOException {
        System.out.printf("--> %s: %s\n", alias, request);
        objOut.writeObject(request);
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
        System.out.printf("<-- %s: %s\n", alias, r);
        return r;
    }

    /**
     * TODO: Comunicació amb el client
     */
    @Override
    public void run() {
        try {
            Request request;
            while((request = readRequest()) != null){
                if (request.getType() == RequestType.SEND){
                    // TODO: Acció del servidor a les respostes del tipus SEND
                    MessagingServerHandler receiver = server.getClientByAlias(request.getAlias());
                    if(receiver == null) {
                        sendRequest(new Request(RequestType.ERROR, "", "No existeix cap client amb aquest alias."));
                    } else if( request.getMessage().isEmpty()){
                        sendRequest(new Request(RequestType.ERROR, "", "El missatge no pot estar buit."));
                    } else {
                        receiver.sendRequest(new Request(RequestType.SEND, this.getAlias(), request.getMessage()));
                        sendRequest(new Request(RequestType.SUCCESS, "", "Missatge enviat correctament."));
                    }
                }
                else if (request.getType() == RequestType.CHANGE_NAME){
                    // TODO: Acció del servidor a les respostes del tipus CHANGE_NAME
                    MessagingServerHandler other = server.getClientByAlias(request.getAlias());
                    if(request.getAlias().isBlank()){
                        sendRequest(new Request(RequestType.ERROR, "", "El alias no pot estar buit."));
                    } else if (other == null){
                        setAlias(request.getAlias());
                        sendRequest(new Request(RequestType.SUCCESS, "", "Alias canviat correctament."));
                    } else {
                        sendRequest(new Request(RequestType.ERROR, "", "Ja existeix un client amb aquest alias."));
                    }
                }
                else if (request.getType() == RequestType.LIST){
                    String clients = server.connectedClients();
                    sendRequest(new Request(RequestType.SUCCESS, "", clients));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        server.removeClient(this);
    }
}
