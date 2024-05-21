package ud4.exercises.certificate;

import ud4.examples.CertificateUtils;

import java.security.*;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class SignMessageCertificate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

        String keyStoreFile = "files/ud4/exercises/keystore.jks";
        String keyStorePassword = "123456";

        KeyStore keyStore = null;
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

        PrivateKey privateKey = null;
        try {
            System.out.println("Introdueix el àlies del certificat:");
            String alias = scanner.nextLine();
            privateKey = (PrivateKey) keyStore.getKey(alias, keyStorePassword.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            System.err.println("Error obtenint el certificat: " + e);
            System.exit(1);
        }

        System.out.println("Introdueix el missatge a signar:");
        String message = scanner.nextLine();

        try {
            String signatura = CertificateUtils.signText(privateKey, message);
            System.out.printf("Signatura:\n%s\n", signatura);
        } catch (Exception e) {
            System.err.println("Error signant el missatge: " + e);
        }
    }
}