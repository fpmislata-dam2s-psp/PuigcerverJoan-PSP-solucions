package ud3.examples.cinema.models;

import java.io.Serializable;

/**
 * Classe que representa un missatge entre el servidor i el client.
 * <p>
 * Aquesta classe implementa Serialitzable per poder ser convertida a
 * bytes i poder ser enviada mitjançant sockets.
 */
public class CinemaMessage implements Serializable {
    /**
     * Tipus de missatge (GET/POST/SUCCESS/ERROR)
     * @see CinemaMessageType
     */
    private CinemaMessageType type;
    /**
     * Objecte que es pot adjuntar a la comunicació
     */
    private Object object;
    /**
     * Missatge opcional que es pot adjuntar a la comunicació
     */
    private String message;

    /**
     * Constructor del missatge
     * @param type Tipus del missatge
     * @param object Objecte adjuntat
     */
    public CinemaMessage(CinemaMessageType type, Object object) {
        this.type = type;
        this.object = object;
    }

    /**
     * Constructor del missatge
     * @param type Tipus del missatge
     * @param object Objecte adjuntat
     * @param message Missatge adjuntat
     */
    public CinemaMessage(CinemaMessageType type, Object object, String message) {
        this.type = type;
        this.object = object;
        this.message = message;
    }

    public CinemaMessageType getType() {
        return type;
    }

    public void setType(CinemaMessageType type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
