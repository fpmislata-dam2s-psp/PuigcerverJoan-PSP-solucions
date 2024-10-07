package ud1.practices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class IPAddressProcess {
    public static void main (String[] args) {
        // String[] program = {"wsl", "ip", "-br", "a"};
        String[] program = {"ip", "-4", "-br", "a"};
        ProcessBuilder pb = new ProcessBuilder(program);
        try {
            Process process = pb.start();
            // Objectes per poder llegir l'eixida estàndard i l'error
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));

            int codiRetorn = process.waitFor();

            String line;
            System.out.println("La IP de les interfícies de xarxa és:");
            while ((line = stdout.readLine()) != null) {
                String[] parts = line.split("\\s+");
                System.out.println(parts[0] + ": " + parts[2]);
            }

        } catch (IOException ex) {
            System.err.println("Excepció d'E/S:");
            System.err.println(ex.getMessage());
            System.exit(-1);
        } catch (InterruptedException ex) {
            System.err.println("El procés fill ha finalitzat de manera incorrecta.");
            System.exit(-1);
        }
    }
}