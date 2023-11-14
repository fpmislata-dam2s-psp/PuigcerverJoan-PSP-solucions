package ud2.practices.swimmingpool;

import ud2.exercises.bizum.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SwimmingPool {
    private int poolCapacity;
    private int showersCapacity;

    private Semaphore poolSemaphore;
    private Semaphore showersSemaphore;

    public SwimmingPool(int poolCapacity, int showersCapacity) {
        this.poolCapacity = poolCapacity;
        this.showersCapacity = showersCapacity;

        this.poolSemaphore = new Semaphore(poolCapacity);
        this.showersSemaphore = new Semaphore(showersCapacity);
    }

    // TODO: create get() method for semaphores
    public Semaphore getPoolSemaphore() {
        return poolSemaphore;
    }

    public Semaphore getShowersSemaphore() {
        return showersSemaphore;
    }

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
            PersonThread p = new PersonThread(name, pool);
            persons.add(p);
            p.start();
        }

        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // TODO: Wait for all persons to leave
        for(PersonThread p : persons) {
            p.interrupt();
            try {
                p.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Tothom ha marxat de la piscina.");
    }
}
