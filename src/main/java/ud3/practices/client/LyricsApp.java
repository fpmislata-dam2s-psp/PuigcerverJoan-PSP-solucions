package ud3.practices.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LyricsApp {
    private final Player player;
    private final Loader loader;
    private final List<String> lines;
    private boolean end;

    public LyricsApp(String host, int port) throws IOException {
        player = new Player(this);
        loader = new Loader(this, host, port);
        lines = new ArrayList<>();
        end = false;
    }

    public synchronized void setEnd(boolean end) {
        this.end = end;
    }
    public synchronized boolean ended(int i) {
        return end && i >= lines.size();
    }

    public synchronized boolean isLineAvailable(int i){
        return i < lines.size();
    }

    public synchronized String getLine(int i) throws InterruptedException {
        while (!isLineAvailable(i)) wait();

        return lines.get(i);
    }

    public synchronized void addLine(String line){
        lines.add(line);
        notifyAll();
    }


    public void start(){
        player.start();
        loader.start();
    }
    public void join() throws InterruptedException {
        player.join();
        loader.join();
    }
    public void interrupt() {
        player.interrupt();
        loader.interrupt();
    }

    public static void main(String[] args) {
        try {
            LyricsApp lyricsApp = new LyricsApp("localhost", 1234);
            lyricsApp.start();

            lyricsApp.join();
        } catch (InterruptedException | IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
