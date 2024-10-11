package ud2.exercises.bizum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.ThreadLocalRandom;

class Person extends Thread {
    private String nom;
    private BankAccount bankAccount;
    List<Person> friends;

    public Person(String nom, BankAccount bankAccount) {
        this.nom = nom;
        this.bankAccount = bankAccount;
        this.friends = new ArrayList<>();
    }

    public BankAccount getBankAccount(){
        return bankAccount;
    }

    public void addFriend(Person p){
        this.friends.add(p);
    }

    @Override
    public void run(){
        for (int i = 0; i < 20; i++) {
            if(bankAccount.getBalance() < 10)
                break;

            int randomIndex = ThreadLocalRandom.current().nextInt(0, friends.size());
            Person friend = friends.get(randomIndex);
            this.bankAccount.bizum(friend.bankAccount, 10);
            System.out.printf("%s ha fet un bizum de 10€ a %s (saldo actual: %.2f)\n",
                    this.nom,
                    friend.nom,
                    this.bankAccount.getBalance()
            );
        }
    }
}
