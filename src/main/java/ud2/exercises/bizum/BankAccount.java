package ud2.exercises.bizum;

public class BankAccount {
    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public synchronized double getBalance(){
        return this.balance;
    }

    public synchronized void deposit(double amount){
        this.balance += amount;
    }

    public synchronized boolean withdraw(double amount){
        if (amount > balance)
            return false;
        this.balance -= amount;
        return true;
    }

    public void bizum(BankAccount other, double amount){
        if(withdraw(amount)) {
            other.deposit(amount);
        }
    }
}
