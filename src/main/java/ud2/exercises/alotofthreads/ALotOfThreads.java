package ud2.exercises.alotofthreads;

import java.util.*;

public class ALotOfThreads {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in).useLocale(Locale.US);

        System.out.println("Introdueix el nombre de fils a crear:");
        int n =  in.nextInt();

        List<Thread> fils = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Thread t = new StartEndThread(String.format("FIL%d", i));
            t.start();
            fils.add(t);
        }

        try {
            for (Thread t : fils){
                    t.join();
            }
            System.out.println("Tots els fils han acabat!");
        } catch (InterruptedException e) {
            System.out.println("El fil principal ha segut interromput.");
        }
    }
}
