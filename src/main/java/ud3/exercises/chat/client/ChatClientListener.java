package ud3.exercises.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ChatClientListener extends Thread {
    private final BufferedReader in;

    public ChatClientListener(InputStream inputStream){
        in = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public void run(){
        try {
            String message = "";
            while ((message = in.readLine()) != null){
                System.out.println(message);
            }
        } catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
}
