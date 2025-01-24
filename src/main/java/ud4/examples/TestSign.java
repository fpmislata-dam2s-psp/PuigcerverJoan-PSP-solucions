package ud4.examples;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Properties;

public class TestSign {
    public static void main(String[] args) throws Exception {
        Properties config = Config.getConfig("application.properties");
        String keyStorePassword = config.getProperty("ud4.examples.keystore.passwd");
        KeyStore keyStore = CertificateUtils.loadKeyStore("files/ud4/example_keystore.jks", keyStorePassword);

        Certificate certificate = keyStore.getCertificate("joan");
        PublicKey publicKey = certificate.getPublicKey();
        PrivateKey privateKey = (PrivateKey) keyStore.getKey("joan", keyStorePassword.toCharArray());

        String message = "Hola!";
        String signature = CertificateUtils.signText(privateKey, message);

        System.out.println("Missatge: " + message);
        System.out.println("Signatura: " + signature);

        boolean isCorrect = CertificateUtils.verifySignature(publicKey, message, signature);
        System.out.println("Is correct? " + isCorrect);
    }
}
