package ud1.examples;

import java.io.IOException;
import java.util.Arrays;

public class RunProcess {
    public static void main (String[] args) {
        // Indica la comanda que utilitza aquest programa per iniciar un nou procés
        String[] program = {"wsl", "rm", "inexistent"};

        ProcessBuilder pb = new ProcessBuilder(program);
        try {
            // Inicia el procés
            Process process = pb.start();
            // Espera a que el procés finalitze
            int codiRetorn = process.waitFor();
            System.out.println("L'execució de "+ Arrays.toString(program) +" retorna "+ codiRetorn);
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
