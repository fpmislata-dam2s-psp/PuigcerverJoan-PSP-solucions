package ud2.practices.swimmingpool;

import java.util.ArrayList;
import java.util.List;

public class SwimmingPool {
    private int poolCapacity;
    private int showersCapacity;

    // TODO: Add semaphores

    public SwimmingPool(int poolCapacity, int showersCapacity) {
        this.poolCapacity = poolCapacity;
        this.showersCapacity = showersCapacity;

        // TODO: Create semaphores
    }

    // TODO: create get() method for semaphores

    public static void main(String[] args) {
        SwimmingPool pool = new SwimmingPool(10, 3);
        String[] names = {
            "Andrès", "Àngel", "Anna", "Carles", "Enric",
            "Helena", "Isabel", "Joan", "Lorena", "Mar",
            "Maria", "Marta", "Míriam", "Nicolàs", "Òscar",
            "Paula", "Pere", "Teresa", "Toni", "Vicent"
        };
        List<PersonThread> persons = new ArrayList<>();
        for(String name : names) {
            // TODO: Create the threads and start them
        }

        // TODO: Wait 60 seconds and kick all persons

        // TODO: Wait for all persons to leave

        System.out.println("Tothom ha marxat de la piscina.");
    }
}
