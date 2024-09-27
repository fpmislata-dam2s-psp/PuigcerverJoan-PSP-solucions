package ud1.exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class PingProcessManualPipe {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Indica el host:");
        String host = in.nextLine();
        System.out.println("Indica el nombre de pings:");
        int nombrePings = in.nextInt();

        ProcessBuilder pingProgram = new ProcessBuilder(
                "wsl.exe",
                "ping",
                "-c",
                Integer.toString(nombrePings),
                host
        );

        ProcessBuilder trProgram = new ProcessBuilder("wsl.exe", "tr", "[:lower:]", "[:upper:]");

        try {
            Process pingProcess = pingProgram.start();
            Process trProcess = trProgram.start();

            BufferedReader pingStdout = new BufferedReader(new InputStreamReader(pingProcess.getInputStream()));

            PrintWriter trStdin = new PrintWriter(trProcess.getOutputStream(), true);
            BufferedReader trStdout = new BufferedReader(new InputStreamReader(trProcess.getInputStream()));

            String line;
            System.out.println("ping stdout:");
            while ((line = pingStdout.readLine()) != null) {
                trStdin.println(line);
                System.out.printf("    %s\n", line);
            }

            trStdin.close();

            pingProcess.waitFor();
            trProcess.waitFor();

            System.out.println("tr stdout:");
            while ((line = trStdout.readLine()) != null)
                System.out.printf("    %s\n", line);

        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
