package ud3.exercises.kahoot.server;

import java.util.ArrayList;
import java.util.List;

public class KahootPartida {
    private String codi;
    private KahootServerHandler propietari;
    private List<KahootServerHandler> participants;

    public KahootPartida(KahootServerHandler propietari) {
        this.propietari = propietari;
        this.participants = new ArrayList<>();
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }
}
