package ud3.exercises.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatListener extends Thread {

    private final BufferedReader in;

    public ChatListener(InputStream inputStream) throws IOException {
        in = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public void run() {
        String line;
        try {
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ignored ){
        }
    }
}