package ud3.practices.lyrics.client;

public class Player extends Thread {
    LyricsPlayer lyricsPlayer;
    private int lyricsIndex;

    public Player(LyricsPlayer lyricsPlayer) {
        this.lyricsPlayer = lyricsPlayer;
        lyricsIndex = 0;
    }

    public void playLine(String[] lineWords, int lineIndex) throws InterruptedException {
        for (int i = 0; i < lineWords.length; i++) {
            Thread.sleep(500);
            if(i == 0)
                System.out.printf("%d: ", lineIndex + 1);
            else
                System.out.print(" ");
            System.out.print(lineWords[i]);
        }
        System.out.println();
    }

    @Override
    public void run() {
        try {
            while(!lyricsPlayer.ended() || lyricsIndex < lyricsPlayer.linesSize()){
                String line = lyricsPlayer.getLine(lyricsIndex);
                playLine(line.split(" "), lyricsIndex);
                lyricsIndex++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            lyricsPlayer.interrupt();
        }
    }
}
