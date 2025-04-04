package ud4.practices.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatListener extends Thread {

    private final ChatClient client;
    private final BufferedReader in;

    public ChatListener(Socket socket, ChatClient client) throws IOException {
        this.client = client;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        String line;
        try {
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ignored ){
        } finally {
            this.client.close();
        }
    }
}