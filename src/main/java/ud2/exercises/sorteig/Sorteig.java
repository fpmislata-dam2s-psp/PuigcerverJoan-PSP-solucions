package ud2.exercises.sorteig;

import java.util.ArrayList;
import java.util.List;

public class Sorteig {
    private final String nom;
    private final int placesInicial;
    private int places;
    private final List<Usuari> seleccionats;

    public Sorteig(String nom, int places){
        this.nom = nom;
        this.placesInicial = places;
        this.places = places;
        this.seleccionats = new ArrayList<>();
    }

    public synchronized boolean inscripcio(Usuari u){
        if (places > 0){
            places--;
            seleccionats.add(u);
            return true;
        }
        return false;
    }

    public String toString(){
        return String.format(
                "Sorteig: %s (%d places), seleccionats (%d): %s",
                nom,
                placesInicial,
                seleccionats.size(),
                seleccionats
        );
    }

    public static void main(String[] args) throws InterruptedException {
        List<Sorteig> sorteigs = new ArrayList<>();
        sorteigs.add(new Sorteig("Viatge a Mallorca", 500));
        sorteigs.add(new Sorteig("Telèfon mòbil", 200));
        sorteigs.add(new Sorteig("Cotxe", 100));
        sorteigs.add(new Sorteig("Ordinador", 300));

        List<Usuari> usuaris = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            String nom = String.format("Usuari %d", i);
            Usuari u = new Usuari(nom, sorteigs);
            usuaris.add(u);
        }

        for(Usuari u : usuaris){
            u.start();
        }

        for(Usuari u : usuaris){
            u.join();
        }

        for(Sorteig s : sorteigs){
            System.out.println(s);
        }
    }
}
