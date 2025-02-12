package ud3.practices.server;

import ud3.practices.models.LyricsMessage;
import ud3.practices.models.LyricsMessageType;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que gestiona la comunicació del servidor
 * amb un únic client en un fil d'execució independent.
 */
public class LyricsServerHandler extends Thread {
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final List<String> lyrics;

    public LyricsServerHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        lyrics = new ArrayList<>();
    }

    public void close() throws IOException {
        socket.close();
    }

    public LyricsMessage receiveMessage() throws IOException, ClassNotFoundException {
        LyricsMessage message = (LyricsMessage) in.readObject();
        System.out.println("<- " + message);
        return message;
    }

    public void sendMessage(LyricsMessage message) throws IOException {
        this.out.writeObject(message);
        System.out.println("-> " + message);
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
            this.readLyrics();

            LyricsMessage message;
            while ((message = receiveMessage()) != null) {
                switch (message.type()) {
                    case NUM_LINES -> {
                        processNumLines();
                    }
                    case GET -> {
                        processGet(message);
                    }
                    default -> {
                        System.err.println("Missatge no reconegut.");
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while handling client.");
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void readLyrics() throws IOException {
        File file = new File("files/ud2/lyrics.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            this.lyrics.add(line);
        }
        reader.close();
    }

    private void processNumLines() throws IOException {
        int numLines = this.lyrics.size();
        LyricsMessage response = new LyricsMessage(LyricsMessageType.NUM_LINES, numLines);
        sendMessage(response);
    }

    private void processGet(LyricsMessage message) throws IOException {
        int i = (int) message.object();
        if (i < 0 || i >= this.lyrics.size()) {
            LyricsMessage response = new LyricsMessage(LyricsMessageType.ERROR, "Índex fora de rang.");
            sendMessage(response);
            return;
        } else {
            String line = this.lyrics.get(i);
            LyricsMessage response = new LyricsMessage(LyricsMessageType.SUCCESS, line);
            sendMessage(response);
        }
    }
}
