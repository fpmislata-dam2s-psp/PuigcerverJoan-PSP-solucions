package ud4.exercises;

import ud4.examples.CertificateUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.List;

public class ShowCertificate {
    private final KeyStore keyStore;

    public ShowCertificate(String keyStorePath, String keyStorePass) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        this.keyStore = CertificateUtils.loadKeyStore(keyStorePath, keyStorePass);
    }

    public void showCertificates() throws KeyStoreException {
        List<String> aliases = Collections.list(keyStore.aliases());
        for (String alias : aliases){
            Certificate cert = this.keyStore.getCertificate(alias);
            String info = CertificateUtils.getCertificateInfo(cert);
            System.out.printf("- %s: %s\n", alias, info);
        }
    }

    public static void main(String[] args) {
        try{
            String keyStorePath = "files/ud4/exercises_certificate.jks";
            String keyStorePass = "654321";
            ShowCertificate sc = new ShowCertificate(keyStorePath, keyStorePass);
            sc.showCertificates();
        } catch (FileNotFoundException e) {
            System.err.println("No s'ha trobat la KeyStore especificada");
        } catch (IOException e) {
            System.err.println("La contrasenya de la KeyStore no és correcta");
        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException e) {
            System.err.println("Error obrint la KeyStore.");
        }
    }
}
