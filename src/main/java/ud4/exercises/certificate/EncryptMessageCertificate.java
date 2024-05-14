package ud4.exercises.certificate;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PublicKey;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import ud4.examples.CertificateUtils;
import ud4.examples.RSA;

public class EncryptMessageCertificate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

        String keyStoreFile = "files/ud4/exercises/keystore.jks";
        String keyStorePassword = "123456";

        KeyStore keyStore = null;
        PublicKey publicKey = null;

        try {
            keyStore = CertificateUtils.loadKeyStore(keyStoreFile, keyStorePassword);
            List<String> aliases = Collections.list(keyStore.aliases());
            System.out.println("Àlies disponibles:");
            for (String alias : aliases) {
                System.out.println("- " + alias);
            }
        } catch (Exception e) {
            System.err.println("Error carregant el keystore: " + e);
            System.exit(1);
        }

        try {
            System.out.println("Introdueix el àlies del certificat:");
            String alias = scanner.nextLine();
            publicKey = keyStore.getCertificate(alias).getPublicKey();
        } catch (KeyStoreException e) {
            System.err.println("Error obtenint el certificat: " + e);
            System.exit(1);
        }

        System.out.println("Introdueix el missatge a xifrar:");
        String message = scanner.nextLine();

        try {
            String encrypted = RSA.encrypt(publicKey, message);
            System.out.printf("Missatge xifrat:\n%s\n", encrypted);
        } catch (Exception e) {
            System.err.println("Error xifrant el missatge: " + e);
        }
    }
}
