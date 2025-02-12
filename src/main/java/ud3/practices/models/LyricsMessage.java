package ud3.practices.models;

import java.io.Serializable;

public record LyricsMessage(
        LyricsMessageType type,
        Object object
) implements Serializable {
    @Override
    public String toString() {
        return "LyricsMessage{" +
                "type=" + type +
                ", object=" + object +
                '}';
    }
}
