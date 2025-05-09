package ud4.exercises.jsse.signed.client;

import ud4.examples.CertificateUtils;
import ud4.examples.Config;
import ud4.exercises.jsse.signed.models.SignedMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class SignedClient {
    private final Socket socket;
    private final Scanner scanner;
    private final ObjectInputStream objIn;
    private final ObjectOutputStream objOut;
    private final PublicKey serverPublicKey;

    public SignedClient() throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
        this.scanner = new Scanner(System.in);

        Properties config = Config.getConfig("application.properties");
        String host = config.getProperty("ud4.exercises.jsse.signed.client.host");
        int port = Integer.parseInt(config.getProperty("ud4.exercises.jsse.signed.client.port"));
        String trustStorePath = config.getProperty("ud4.exercises.jsse.signed.client.truststore.path");
        String trustStorePassword = config.getProperty("ud4.exercises.jsse.signed.client.truststore.password");
        String alias = config.getProperty("ud4.exercises.jsse.signed.client.truststore.alias");

        System.setProperty("javax.net.ssl.trustStore", trustStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
        this.socket = new Socket(host, port);
        this.objOut = new ObjectOutputStream(socket.getOutputStream());
        this.objIn = new ObjectInputStream(socket.getInputStream());

        serverPublicKey = loadServerPublicKey(trustStorePath, trustStorePassword, alias);
    }

    public PublicKey loadServerPublicKey(String trustStorePath, String trustStorePassword, String serverAlias) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        KeyStore keyStore = CertificateUtils.loadKeyStore(trustStorePath, trustStorePassword);
        List<String> aliases = Collections.list(keyStore.aliases());
        System.out.println(aliases);
        Certificate cert = keyStore.getCertificate(serverAlias);
        return cert.getPublicKey();
    }

    private void sendText(String text) throws IOException {
        SignedMessage message = new SignedMessage(text, "");
        this.objOut.writeObject(message);
    }

    public void run() throws IOException, ClassNotFoundException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        boolean exit = false;
        while (!exit){
            System.out.println("Introdueix un text:");
            String line = scanner.nextLine();

            this.sendText(line);

            SignedMessage response = (SignedMessage) objIn.readObject();
            String text = response.message();
            String signature = response.signature();
            boolean valid = CertificateUtils.verifySignature(this.serverPublicKey, text, signature);
            System.out.printf("Received: %s\n", text);
            System.out.printf("Signature: %s\n", signature);
            System.out.printf("Is valid?: %s\n", valid ? "yes" : "no");
        }
    }

    public static void main(String[] args) {
        System.out.println("Connectant-se amb el servidor...");
        try {
            SignedClient signed = new SignedClient();
            signed.run();
        } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException e){
            System.err.println("Error connectant-se amb el servidor.");
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
