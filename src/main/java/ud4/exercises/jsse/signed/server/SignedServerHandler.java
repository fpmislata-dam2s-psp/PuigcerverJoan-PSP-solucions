package ud4.exercises.jsse.signed.server;

import ud3.examples.cinema.models.CinemaMessage;
import ud3.examples.cinema.models.CinemaMessageType;
import ud3.examples.cinema.models.Film;
import ud4.examples.CertificateUtils;
import ud4.exercises.jsse.signed.models.SignedMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.cert.Certificate;

public class SignedServerHandler extends Thread {
    private final Socket client;
    private final SignedServer server;
    private final ObjectInputStream objIn;
    private final ObjectOutputStream objOut;

    public SignedServerHandler(Socket client, SignedServer server) throws IOException {
        this.client = client;
        this.server = server;
        objIn = new ObjectInputStream(client.getInputStream());
        objOut = new ObjectOutputStream(client.getOutputStream());
    }

    @Override
    public void run() {
        boolean exit = false;
        try {
            while (!exit){
                SignedMessage message = (SignedMessage) objIn.readObject();

                String text = message.message();
                PrivateKey key = server.getKey();
                String signature = CertificateUtils.signText(key, text);

                SignedMessage reposnse = new SignedMessage(text, signature);
                objOut.writeObject(reposnse);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while handling client.");
            System.err.println(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
