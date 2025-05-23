package ud4.exercises.certificates;

import ud4.examples.CertificateUtils;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class SignMessageCertificate {
    private final static String KEYSOTRE_PATH = "files/ud4/exercises_certificate.jks";
    private final static String KEYSOTRE_PASSWORD = "654321";
    private KeyStore keyStore;
    private PrivateKey privateKey;

    public SignMessageCertificate() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        loadKeyStore();
    }

    private void loadKeyStore() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        keyStore = CertificateUtils.loadKeyStore(KEYSOTRE_PATH, KEYSOTRE_PASSWORD);
        System.out.println("Loaded keystore: " + KEYSOTRE_PATH);
    }

    private boolean showAliases() throws KeyStoreException {
        if (keyStore.size() > 0){
            System.out.println("Els àlies disponibles són els següents.");
            List<String> aliases = Collections.list(keyStore.aliases());
            for(String alias : aliases)
                System.out.println("- " + alias);
            System.out.printf("Total: %d\n", keyStore.size());
            return true;
        } else {
            System.out.println("No s'ha trobat cap àlies.");
            return false;
        }
    }

    public void loadPrivateKey(String alias) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        this.privateKey = (PrivateKey) keyStore.getKey(alias, KEYSOTRE_PASSWORD.toCharArray());
        if (privateKey == null)
            throw new RuntimeException(String.format("No s'ha trobat la clau privada per a l'àlies '%s'", alias));
        System.out.printf("S'ha carregat la clau de '%s'\n", alias);
    }

    public String sign(String message) throws InvalidKeyException {
        if (privateKey == null)
            throw new RuntimeException("No s'ha carregat cap clau privada.");
        return CertificateUtils.signText(this.privateKey, message);
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

            SignMessageCertificate signMessageCertificate = new SignMessageCertificate();
            boolean hasAliases = signMessageCertificate.showAliases();
            if(!hasAliases) return;

            System.out.println();
            System.out.print("Introdueix l'alies: ");
            String selectedAlias = scanner.nextLine();
            signMessageCertificate.loadPrivateKey(selectedAlias);

            System.out.println("Introdueix el missatge a signar:");
            String message = scanner.nextLine();
            String signatura = signMessageCertificate.sign(message);

            System.out.println();
            System.out.println("Signatura:");
            System.out.println(signatura);

        } catch (RuntimeException e){
            System.err.println(e.getMessage());
        } catch (UnrecoverableKeyException e ){
            System.out.println("La clau privada no es pot recuperar.");
        } catch (IOException e) {
            System.out.println("No s'ha trobat la KeyStore.");
        } catch (InvalidKeyException e) {
            System.out.println("La clau privada no és vàlida.");
        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException e) {
            throw new RuntimeException(e);
        }
    }
}
