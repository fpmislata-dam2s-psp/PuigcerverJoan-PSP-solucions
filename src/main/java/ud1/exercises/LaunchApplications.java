package ud1.exercises;

import java.io.IOException;
import java.util.ArrayList;

public class LaunchApplications {
    public static void main(String[] args) {
        ArrayList<String[]> applications = new ArrayList<>();
        // Totes aquelles aplicacions que no estiguen en la variable d'entorn $PATH
        // han de ser especificades en la ruta al programa
        applications.add(new String[]{"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe"});
        applications.add(new String[]{"C:\\Users\\jpuigcerver\\AppData\\Local\\Programs\\Git\\git-bash.exe"});
        applications.add(new String[]{"explorer"});

        ArrayList<Process> processes = new ArrayList<>();

        for(String[] app : applications){
            ProcessBuilder pb = new ProcessBuilder(app);
            try {
                Process p = pb.start();
                processes.add(p);

                p.waitFor();
            } catch (IOException | InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
