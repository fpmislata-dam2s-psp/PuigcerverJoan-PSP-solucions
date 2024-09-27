package ud1.exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Scanner;

public class PingProcess {
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

        try {
            Process process = pingProgram.start();

            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            System.out.println("Stdout:");
            while ((line = stdout.readLine()) != null)
                System.out.printf("    %s\n", line);

            System.out.println("Stderr:");
            while ((line = stderr.readLine()) != null)
                System.out.printf("    %s\n", line);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
