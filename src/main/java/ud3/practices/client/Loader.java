package ud3.practices.client;

import ud3.practices.models.LyricsMessage;
import ud3.practices.models.LyricsMessageType;

import java.io.*;
import java.net.Socket;

public class Loader extends Thread {
    private final LyricsApp lyricsApp;
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    private int numLines;

    public Loader(LyricsApp lyricsApp, String host, int port) throws IOException {
        this.lyricsApp = lyricsApp;

        this.socket = new Socket(host, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public LyricsMessage receiveMessage() throws IOException, ClassNotFoundException {
        LyricsMessage message = (LyricsMessage) in.readObject();
        // System.out.println("<- " + message);
        return message;
    }

    public void sendMessage(LyricsMessage message) throws IOException {
        this.out.writeObject(message);
        // System.out.println("-> " + message);
    }

    @Override
    public void run(){
        try {
            this.askNumLines();

            boolean correct = true;
            for (int i = 0; i < this.numLines && correct; i++) {
                correct = this.askLine(i);
            }
            lyricsApp.setEnd(true);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private void askNumLines() throws IOException, ClassNotFoundException {
        LyricsMessage message = new LyricsMessage(
                LyricsMessageType.NUM_LINES, null
        );
        this.sendMessage(message);

        LyricsMessage response = receiveMessage();
        this.numLines = (Integer) response.object();
    }

    private boolean askLine(int i) throws IOException, ClassNotFoundException {
        LyricsMessage message = new LyricsMessage(
                LyricsMessageType.GET, i
        );
        this.sendMessage(message);

        LyricsMessage response = receiveMessage();
        if (response.type() == LyricsMessageType.SUCCESS) {
            String line = (String) response.object();
            this.lyricsApp.addLine(line);
        } else
            return false;

        return true;
    }
}
