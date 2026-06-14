package SOLID_PRINCIPLES.LiskovSubstitutionPrinciple;

interface DepositeMoney {
    void deposite(double amount);
}

interface withdrawMoney extends DepositeMoney {
    void withdraw(double amount);
}

class savingsAccount implements withdrawMoney {
    private double balance;

    public savingsAccount(double balance) {
        this.balance = balance;
    }

    @Override
    public void deposite(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public double getBalance() {
        return balance;
    }
}

class currentaccount implements withdrawMoney {
    private double balance;

    public currentaccount(double balance) {
        this.balance = balance;
    }

    @Override
    public void deposite(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public double getBalance() {
        return balance;
    }
}

class fixedDepositAccount implements DepositeMoney {
    private double balance;

    public fixedDepositAccount(double balance) {
        this.balance = balance;
    }

    @Override
    public void deposite(double amount) {
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }
}



public class LiskovSubstitutionPrinciple {

    public static void main(String[] args) {
        savingsAccount savingsAccount = new savingsAccount(1000);
        currentaccount currentaccount = new currentaccount(2000);
        fixedDepositAccount fixedDepositAccount = new fixedDepositAccount(5000);

        // Using the accounts
        savingsAccount.deposite(500);
        currentaccount.deposite(1000);
        fixedDepositAccount.deposite(2000);

        System.out.println("Savings Account Balance: " + savingsAccount.getBalance());
        System.out.println("Current Account Balance: " + currentaccount.getBalance());
        System.out.println("Fixed Deposit Account Balance: " + fixedDepositAccount.getBalance());

        // Withdraw from savings and current accounts
        savingsAccount.withdraw(300);
        currentaccount.withdraw(1500);

        System.out.println("Savings Account Balance after withdrawal: " + savingsAccount.getBalance());
        System.out.println("Current Account Balance after withdrawal: " + currentaccount.getBalance());
    }
    
}
