package ud4.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class CertificateUtils {
    public static KeyStore loadKeyStore(String ksFile, String ksPwd) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore ks = KeyStore.getInstance("JKS");
        File f = new File (ksFile);
        if (!f.isFile())
            throw new FileNotFoundException();

        FileInputStream in = new FileInputStream (f);
        ks.load(in, ksPwd.toCharArray());
        return ks;
    }

    public static String getCertificateInfo(Certificate certificate){
        X509Certificate cert = (X509Certificate) certificate;
        return cert.getSubjectX500Principal().getName();
    }

    /**
     *
     * @param privateKey Clau privada
     * @param text Text a signar
     * @return Signatura en Base64
     * @throws InvalidKeyException if the key is invalid
     */
    public static String signText(PrivateKey privateKey, String text) throws InvalidKeyException {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(text.getBytes());
            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (NoSuchAlgorithmException | SignatureException e) {
            // Aquests errors no haurien d'apareixer mai
            throw new RuntimeException(e);
        }
    }

    public static boolean verifySignature(PublicKey publicKey, String text, String signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signatureObject = Signature.getInstance("SHA256withRSA");
        signatureObject.initVerify(publicKey);
        signatureObject.update(text.getBytes());

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return signatureObject.verify(signatureBytes);
    }

    public static void main(String[] args) {
        try {
            /*
             * Guardar contrasenyes en el codi NO ÉS UNA BONA PRÀCTICA,
             * cal utilitzar variables d'entorn o un fitxer de configuració.
             */
            Properties config = Config.getConfig("application.properties");
            String keyStorePassword = config.getProperty("ud4.examples.keystore.passwd");

            KeyStore keyStore = loadKeyStore("files/ud4/example_keystore.jks", keyStorePassword);

            List<String> aliases = Collections.list(keyStore.aliases());
            System.out.println("Certificats en el magatzem de claus.");
            System.out.printf("Total: %d\n", keyStore.size());
            for(String alias : aliases)
                System.out.println("- " + alias);

            /*
             El certificat ha segut creat prèviament amb la comanda:
             ```
            keytool -genkey -keyalg RSA \
                -alias example \
                -keystore files/ud4/example_keystore.jks \
                -validiry 360 \
                -storepass 123456 -keysize 2048 \
                -dname "CN=Example, O=CIPFP Mislata, OU=PSP-DAM2S, L=Mislata, ST=València, C=ES"
             ```

             Per poder llegir informació sobre el subjecte del certificat,
             necessitem utilitzar l'objecte X509Certificate.
             */
            for (String alias : aliases) {
                Certificate certificate = keyStore.getCertificate(alias);
                System.out.println(getCertificateInfo(certificate));

                // Clau pública del certificat
                PublicKey examplePublic = certificate.getPublicKey();

                // Clau privada
                PrivateKey examplePrivate = (PrivateKey) keyStore.getKey(alias, keyStorePassword.toCharArray());

                String message = "This is a secret information.";
                System.out.printf("Message: %s\n", message);

                String encrypted = RSA.encrypt(examplePublic, message);
                System.out.printf("Encrypted: %s\n", encrypted);

                String decrypted = RSA.decrypt(examplePrivate, encrypted);
                System.out.printf("Decrypted: %s\n", decrypted);

                String signautre = signText(examplePrivate, message);
                System.out.printf("Signed: %s\n", signautre);

                System.out.println("Signature verification: " + verifySignature(examplePublic, message, signautre));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}