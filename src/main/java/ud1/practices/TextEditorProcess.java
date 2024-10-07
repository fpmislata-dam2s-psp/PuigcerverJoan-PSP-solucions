package ud1.practices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextEditorProcess {
    public static void main(String[] args) {
        String filePath = "files/ud1/text.txt";

        ProcessBuilder pbEditor = new ProcessBuilder("gnome-text-editor", filePath);
        ProcessBuilder pbContingut = new ProcessBuilder("cat", filePath);

        try {
            System.out.println("S'està obrint l'editor de text...");
            Process editor = pbEditor.start();

            editor.waitFor();
            System.out.printf("L'edició del fitxer '%s' ha acabat.\n", filePath);

            Process contingut = pbContingut.start();
            BufferedReader stdout = new BufferedReader(new InputStreamReader(contingut.getInputStream()));
            System.out.printf("Contingut del fitxer '%s':\n", filePath);

            String line;
            while ((line = stdout.readLine()) != null) {
                System.out.println("    " + line);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
