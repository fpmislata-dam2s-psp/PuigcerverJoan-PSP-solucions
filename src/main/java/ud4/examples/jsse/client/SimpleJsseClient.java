package ud4.examples.jsse.client;

import ud4.examples.Config;

import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Properties;

public class SimpleJsseClient {
    public static void main(String[] args) {
        try {
            Properties config = Config.getConfig("application.properties");

            String host = config.getProperty("ud4.examples.jsse.client.host");
            int port = Integer.parseInt(config.getProperty("ud4.examples.jsse.client.port"));
            String trustStorePath = config.getProperty("ud4.examples.jsse.client.truststore.path");
            String trustStorePassword = config.getProperty("ud4.examples.jsse.client.truststore.password");

            System.setProperty("javax.net.ssl.trustStore", trustStorePath);
            System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

            System.out.println("Creant el Socket client.");
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            Socket socket = sslsocketfactory.createSocket(host, port);
            // Socket socket = new Socket(host, port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Es pot utilitzar l'opció autoflush per forçar l'enviament de dades
            // després de cada print()
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String missatge = "Aquest missatge ha segut enviat des del client.";
            out.println(missatge);
            out.flush(); // Aquesta línia no és necessària amb l'opció autoFlush
            System.out.println("S'ha enviat el missatge.");

            System.out.println("Esperant resposta");
            String resposta = in.readLine();

            System.out.println("Resposta del servidor:");
            System.out.println(resposta);

            System.out.println("Tancant el socket...");
            socket.close();
            System.out.println("Tancat");
        } catch (ConnectException e) {
            System.err.println("Connection refused!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
