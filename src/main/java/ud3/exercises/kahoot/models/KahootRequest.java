package ud3.exercises.kahoot.models;

public class KahootRequest {
    private String message;
    private KahootRequestType type;

    public KahootRequest(KahootRequestType type, String message) {
        this.type = type;
        this.message = message;
    }

    public KahootRequestType getType() {
        return type;
    }
}
