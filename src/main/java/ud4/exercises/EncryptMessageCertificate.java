package ud4.exercises;

import ud4.examples.CertificateUtils;
import ud4.examples.Config;
import ud4.examples.RSA;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.*;

public class EncryptMessageCertificate {
    private final KeyStore keyStore;

    public EncryptMessageCertificate(String keyStorePath, String keyStorePass) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        this.keyStore = CertificateUtils.loadKeyStore(keyStorePath, keyStorePass);
    }

    public boolean containsAlias(String alias) throws KeyStoreException {
        return this.keyStore.containsAlias(alias);
    }

    public String encrypt(String alias, String text) throws KeyStoreException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Certificate certificate = keyStore.getCertificate(alias);
        PublicKey publicKey = certificate.getPublicKey();

        return RSA.encrypt(publicKey, text);
    }

    public static void main(String[] args) {
        try{
            Properties config = Config.getConfig("application.properties");
            String keyStorePath = config.getProperty("ud4.exercises.keystore.path");
            String keyStorePass = config.getProperty("ud4.exercises.keystore.passwd");

            Scanner in = new Scanner(System.in).useLocale(Locale.US);

            EncryptMessageCertificate sc = new EncryptMessageCertificate(keyStorePath, keyStorePass);

            System.out.println("Introdueix l'alias del certificat:");
            String alias = in.nextLine();

            if (!sc.containsAlias(alias)) {
                System.err.println("No s'ha trobat el certificat amb l'àlies introduit.");
                System.exit(1);
            }

            System.out.println("Text:");
            String text = in.nextLine();

            String encrypted = sc.encrypt(alias, text);
            System.out.println("Exrypted:");
            System.out.println(encrypted);

        } catch (FileNotFoundException e) {
            System.err.println("No s'ha trobat la KeyStore especificada");
        } catch (IOException e) {
            System.err.println("La contrasenya de la KeyStore no és correcta");
        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException e) {
            System.err.println("Error obrint la KeyStore.");
        } catch (NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            System.err.println("Error encriptant el text.");
        }
    }
}
