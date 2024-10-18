package ud2.exercises.sorteig;

import java.util.List;

public class Usuari extends Thread {
    private final String nom;
    private final List<Sorteig> sorteigs;

    public Usuari(String nom, List<Sorteig> sorteigs){
        this.nom = nom;
        this.sorteigs = sorteigs;
    }

    public String toString(){
        return String.format("Usuari{%s}", this.nom);
    }

    @Override
    public void run() {
        for (Sorteig s : sorteigs){
            s.inscripcio(this);
        }
    }
}
