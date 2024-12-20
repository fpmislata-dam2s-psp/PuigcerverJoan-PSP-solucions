package ud3.examples.cinema.server;

import ud3.examples.cinema.models.Film;
import ud3.examples.cinema.models.CinemaMessage;
import ud3.examples.cinema.models.CinemaMessageType;

import java.io.*;
import java.net.Socket;

/**
 * Classe que gestiona la comunicació del servidor
 * amb un únic client en un fil d'execució independent.
 */
public class CinemaServerHandler extends Thread {
    /**
     * Socket que permet comunicar-se amb el client.
     */
    private final Socket client;
    /**
     * Servidor CinemaServer
     */
    private final CinemaServer server;
    /**
     * Objecte ObjectInputStream que permet rebre objectes pel Socket.
     */
    private final ObjectInputStream objIn;
    /**
     * Objecte ObjectOutputStream que permet enviar objectes pel Socket.
     */
    private final ObjectOutputStream objOut;

    /**
     * Constructor que inicialitza els canals de comunicació a partir de l'objecte Socket.
     * @param client Socket per comunicar-se amb el client.
     * @param server Servidor
     * @throws IOException Llançada si hi ha algun error creant els canals de comunicació
     */
    public CinemaServerHandler(Socket client, CinemaServer server) throws IOException {
        this.client = client;
        this.server = server;
        objIn = new ObjectInputStream(client.getInputStream());
        objOut = new ObjectOutputStream(client.getOutputStream());
    }

    /**
     * Fil d'execució independent per cada client.
     * <p>
     * El servidor espera peticions (CinemaMessage) i contesta a elles.
     * <p>
     * Si es del tipus POST, afegirà la pel·licula rebuda al servidor.
     * Si es del tipus GET, enviarà la pel·licula sol·licitada al client.
     */
    @Override
    public void run() {
        try {
            // Obtenim objectes del tipus CinemaMessage del client fins que aquest es desconnecte.
            CinemaMessage req;
            while((req = (CinemaMessage) objIn.readObject()) != null){
                if (req.getType() == CinemaMessageType.POST){
                    processPost(req);
                } else if (req.getType() == CinemaMessageType.GET) {
                    processGet(req);
                }
            }
            client.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while handling client.");
            System.err.println(e.getMessage());
        } finally {
            this.server.removeClient(this);
        }
    }

    private void processGet(CinemaMessage req) throws IOException {
        // Recuperem la ID de pel·licula de la petició
        int id = (Integer) req.getObject();

        // Enviem una resposta al client
        CinemaMessage response;
        if(id >= server.getFilms().size() || id < 0)
            // Si no existeix la pel·licula, enviem una resposta del tipus ERROR
            response = new CinemaMessage(
                    CinemaMessageType.ERROR,
                    null,
                    String.format("No s'ha trobat cap pel·licula amb id %d", id)
            );
        else {
            // Si existeix la pel·licula, enviem una resposta del tipus SUCCESS amb la pel·licula solicitada
            Film film = server.getFilm(id);
            response = new CinemaMessage(CinemaMessageType.SUCCESS, film);
        }

        // Enviem la resposta
        objOut.writeObject(response);
    }

    private void processPost(CinemaMessage req) throws IOException {
        // Si la petició és del tipus POST
        // Recuperem la pel·licula del missatge
        Film film = (Film) req.getObject();
        // Afegim la pel·lícula al servidor
        server.addFilms(film);

        // Enviem una resposta del tipus SUCCESS al client
        // indicant que la pel·licula s'ha afegit correctament
        String message = String.format("Film %s added.", film);
        CinemaMessage response = new CinemaMessage(
                CinemaMessageType.SUCCESS,
                null,
                message
        );
        System.out.println(message);
        objOut.writeObject(response);
    }
}
