package ud4.examples.jsse.server;

import ud4.examples.Config;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.util.Properties;

public class SimpleJsseServer {

    public static void main(String[] args) {
        try {
            Properties config = Config.getConfig("application.properties");
            int port = Integer.parseInt(config.getProperty("ud4.examples.jsse.server.port"));
            String keyStorePath = config.getProperty("ud4.examples.jsse.server.keystore.path");
            String keyStorePassword = config.getProperty("ud4.examples.jsse.server.keystore.password");

            System.setProperty("javax.net.ssl.keyStore", keyStorePath);
            System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);

            SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            ServerSocket server = sslserversocketfactory.createServerSocket(port);
            System.out.println("Creant el Socket servidor en el port: " + port);

            // ServerSocket server = new ServerSocket(port);

            System.out.println("Esperant connexions...");
            // Aquest Socket es de tipus SSLSocket
            SSLSocket connexio = (SSLSocket) server.accept();
            System.out.println("Connectat amb el client!");

            BufferedReader in = new BufferedReader(new InputStreamReader(connexio.getInputStream()));
            // Activem l'opció autoFlush
            PrintWriter out = new PrintWriter(connexio.getOutputStream(), true);

            System.out.println("Esperant missatge des del client...");
            String missatge = in.readLine();
            System.out.println("Sha rebut el missatge:");
            System.out.println(missatge);

            String resposta = "Rebut!";
            System.out.println("S'ha enviat el missatge: " + resposta);
            out.println(resposta);

            System.out.println("Tancant el servidor...");
            connexio.close();
            server.close();
            System.out.println("Tancat.");
        } catch (ConnectException e) {
            System.err.println("Connection refused!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
