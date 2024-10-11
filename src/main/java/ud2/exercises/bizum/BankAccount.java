package ud2.exercises.bizum;

public class BankAccount {
    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance(){
        return this.balance;
    }

    public void deposit(double amount){
        this.balance += amount;
    }

    public boolean withdraw(double amount){
        if (amount > balance)
            return false;
        this.balance -= amount;
        return true;
    }

    public boolean bizum(BankAccount other, double amount){
        if(withdraw(amount)) {
            other.deposit(amount);
            return true;
        }
        return false;
    }
}
