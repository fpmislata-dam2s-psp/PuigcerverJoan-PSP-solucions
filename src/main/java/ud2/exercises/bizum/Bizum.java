package ud2.exercises.bizum;

import java.util.ArrayList;
import java.util.List;

public class Bizum {
    public static void main(String[] args) throws InterruptedException {
        List<Person> persones = new ArrayList<>();

        BankAccount bankAccountPau = new BankAccount(100);
        Person pau = new Person("Pau", bankAccountPau);
        persones.add(pau);

        BankAccount bankAccountMar = new BankAccount(150);
        Person mar = new Person("Mar", bankAccountMar);
        persones.add(mar);

        BankAccount bankAccountPere = new BankAccount(150);
        Person pere = new Person("Pere", bankAccountPere);
        persones.add(pere);

        BankAccount bankAccountAina = new BankAccount(150);
        Person aina = new Person("Aina", bankAccountAina);
        persones.add(aina);

        pau.addFriend(mar);
        pau.addFriend(aina);

        mar.addFriend(pau);
        mar.addFriend(pere);

        pere.addFriend(pau);
        pere.addFriend(mar);
        pere.addFriend(aina);

        aina.addFriend(mar);

        double saldoInicial = 0;
        for(Person p : persones){
            saldoInicial += p.getBankAccount().getBalance();
            p.start();
        }

        System.out.printf("Saldo inicial: %.2f\n", saldoInicial);

        double saldoFinal = 0;
        for(Person p : persones){
            p.join();
        }
        for(Person p : persones){
            saldoFinal += p.getBankAccount().getBalance();
        }
        System.out.printf("Saldo final: %.2f\n", saldoFinal);
    }
}
