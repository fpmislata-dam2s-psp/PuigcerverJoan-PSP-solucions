package exam2.server;

import ud4.examples.Config;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;

import static ud4.examples.CertificateUtils.loadKeyStore;
import static ud4.examples.CertificateUtils.printCertificateInfo;

public class MessagingServer extends Thread {
    ServerSocket server;

    List<MessagingServerHandler> clients;
    boolean running;

    public MessagingServer(int port) throws IOException {
        // TODO: Crea un SocketServer mitjançant JSSE
        Properties config = Config.getConfig("application.properties");
        String keystorePasswd = config.getProperty("exam2.keystore.passwd");
        /*
        keytool -genkey -keyalg RSA \
            -alias messaging-server \
            -keystore files/exam2/messaging-keystore.jks \
            -storepass 123456 -keysize 2048 \
            -dname "CN=MessagingServer, O=CIPFP Mislata, OU=PSP-DAM2S, L=Mislata, ST=València, C=ES"
         */
        System.setProperty("javax.net.ssl.keyStore", "files/exam2/messaging-keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", keystorePasswd);
        printCertificate("files/exam2/messaging-keystore.jks", keystorePasswd, "messaging-server");

        SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        this.server = sslserversocketfactory.createServerSocket(port);
        clients = new ArrayList<>();
        running = true;
    }

    private void printCertificate(String keyStorePath, String keyStorePasswd, String alias){
        try {
            KeyStore ks = loadKeyStore(keyStorePath, keyStorePasswd);
            Certificate cer = ks.getCertificate(alias);
            printCertificateInfo(cer);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void close(){
        running = false;
        this.interrupt();
    }

    public synchronized void removeClient(MessagingServerHandler hc){
        clients.remove(hc);
    }

    /**
     * Retorna el client amb el alies especificat o null si no existeix
     * @param alias Alias de la persona a la que li envies el missatge
     * @return Retorna el client amb el alies especificat o null si no existeix
     */
    public MessagingServerHandler getClientByAlias(String alias){
        return clients.stream().filter(c -> c.getAlias().equals(alias)).findAny().orElse(null);
    }
    /**
     * Retorna els alies dels clients connectats separats per comes
     * @return Alies dels clients connectats separats per comes
     */
    public String connectedClients(){
        return clients.stream().map(MessagingServerHandler::getAlias).collect(Collectors.joining(","));
    }


    @Override
    public void run() {
        while (running){
            try {
                Socket client = server.accept();
                MessagingServerHandler handler = new MessagingServerHandler(client, this);
                clients.add(handler);
                handler.start();
                System.out.println("Nova connexió acceptada.");
            } catch (IOException e) {
                System.err.println("Error while accepting new connection");
                System.err.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            MessagingServer server = new MessagingServer(1234);
            server.start();

            scanner.nextLine();

            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
