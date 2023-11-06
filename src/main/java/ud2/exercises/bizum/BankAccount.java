package ud2.exercises.bizum;

public class BankAccount {
    private double balance;

    public BankAccount(double amount) {
        this.balance = amount;
    }

    public double getBalance() {
        return balance;
    }

    public synchronized void deposit(double amount) throws InterruptedException {
        double newBalance = this.balance;
        Thread.sleep(300);
        newBalance = newBalance + amount;
        Thread.sleep(200);
        this.balance = newBalance;
    }
    public synchronized void withdraw(double amount) throws NotEnoughMoneyException, InterruptedException {
        if (amount > this.balance)
            throw new NotEnoughMoneyException();
        double newBalance = this.balance;
        Thread.sleep(350);
        newBalance = newBalance - amount;
        Thread.sleep(200);
        this.balance = newBalance;
    }

    public void bizum(double amount, BankAccount destination) throws NotEnoughMoneyException, InterruptedException {
        this.withdraw(amount);
        destination.deposit(amount);
    }
}
