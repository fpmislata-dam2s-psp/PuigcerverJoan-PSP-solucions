package ud4.exercises.certificate;

import ud4.examples.CertificateUtils;

import java.security.*;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class VerifySignatureMessageCertificate {
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

        PublicKey publicKey = null;
        try {
            System.out.println("Introdueix el àlies del certificat:");
            String alias = scanner.nextLine();
            publicKey = keyStore.getCertificate(alias).getPublicKey();
        } catch (KeyStoreException e) {
            System.err.println("Error obtenint el certificat: " + e);
            System.exit(1);
        }

        System.out.println("Introdueix el missatge per verificar signatura:");
        String message = scanner.nextLine();

        System.out.println("Introdueix la signatura:");
        String signatura = scanner.nextLine();

        try {
            boolean isValid = CertificateUtils.verifySignature(publicKey, message, signatura);
            System.out.println("És vàlida la signatura? " + (isValid ? "Sí" : "No"));
        } catch (Exception e) {
            System.err.println("Error signant el missatge: " + e);
        }
    }
}