package ud4.exercises.jsse.signed.models;

import java.io.Serializable;

public record SignedMessage(
        String message,
        String signature
) implements Serializable {
}
