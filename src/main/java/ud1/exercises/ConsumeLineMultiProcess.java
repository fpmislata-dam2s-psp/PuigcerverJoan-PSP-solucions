package ud1.exercises;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConsumeLineMultiProcess {
    public static void main(String[] args) {
        List<ProcessBuilder> programs = new ArrayList<>();
        programs.add(new ProcessBuilder(
                "wsl.exe",
                "files/ud1/consume_line.sh",
                "files/ud1/colors.txt",
                "3"
        ).inheritIO());
        programs.add(new ProcessBuilder(
                "wsl.exe",
                "files/ud1/consume_line.sh",
                "files/ud1/concessionari.csv",
                "1"
        ).inheritIO());
        programs.add(new ProcessBuilder(
                "wsl.exe",
                "files/ud1/consume_line.sh",
                "files/ud1/lorem.txt",
                "2"
        ).inheritIO());

       List<Process> processes = new ArrayList<>();

       for (ProcessBuilder pb : programs){
           try {
               Process p = pb.start();
               processes.add(p);
           } catch (IOException e) {
               System.err.println(e.getMessage());
           }
       }

       for (Process p : processes){
           try {
               p.waitFor();
           } catch (InterruptedException e) {
               System.err.println(e.getMessage());
           }
       }

        System.out.println("Tots els processos han acabat.");
    }
}
