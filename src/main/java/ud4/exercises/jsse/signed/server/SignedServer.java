package ud4.exercises.jsse.signed.server;

import ud4.examples.CertificateUtils;
import ud4.examples.Config;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SignedServer {
    private final ServerSocket server;
    private final List<SignedServerHandler> clients;
    private boolean running;
    private final PrivateKey key;

    public SignedServer() throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        Properties config = Config.getConfig("application.properties");
        int port = Integer.parseInt(config.getProperty("ud4.exercises.jsse.signed.server.port"));
        String keyStorePath = config.getProperty("ud4.exercises.jsse.signed.server.keystore.path");
        String keyStorePassword = config.getProperty("ud4.exercises.jsse.signed.server.keystore.password");
        String alias = config.getProperty("ud4.exercises.jsse.signed.server.alias");

        System.setProperty("javax.net.ssl.keyStore", keyStorePath);
        System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
        SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        this.server = sslserversocketfactory.createServerSocket(port);
        clients = new ArrayList<>();
        running = true;

        key = loadKey(keyStorePath, keyStorePassword, alias);
    }

    private PrivateKey loadKey(String keyStorePath, String keyStorePassword, String alias) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException {
        KeyStore keyStore = CertificateUtils.loadKeyStore(keyStorePath, keyStorePassword);
        return (PrivateKey) keyStore.getKey(alias, keyStorePassword.toCharArray());
    }

    public PrivateKey getKey() {
        return key;
    }

    /**
     * Fil d'execució del servidor.
     * <p>
     * El servidor escolta el port i espera noves connexions.
     * Quan una nou client es connecta, es crea un objecte CinemaServerHandler,
     * que gestionarà la comunicació amb aquest client en un fil distint.
     * <p>
     * D'aquesta manera, el servidor pot continuar escoltant i esperant
     * noves connexions mentres cada fil gestiona la comunicació
     * amb cada client.
     */
    public void run() {
        while (running){
            try {
                // Escolta i esperà una nova connexió.
                Socket client = server.accept();
                // Quan un client es connecta, es crea un objecte CinemaServerHandler que
                // gestionarà la comunicació amb el client connectat.
                System.out.println("Nou client acceptat.");
                SignedServerHandler handler = new SignedServerHandler(client, this);
                clients.add(handler);
                // S'inicia CinemaServerHandler en un fil independent
                handler.start();
            } catch (IOException e) {
                System.err.println("Error while accepting new connection");
                System.err.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            SignedServer server = new SignedServer();
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
