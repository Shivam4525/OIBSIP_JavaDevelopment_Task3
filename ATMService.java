package atmmodel;

import java.util.ArrayList;

public class ATMService {

    private double balance = 5000.0;
    private ArrayList<String> history = new ArrayList<>();

    public boolean validateLogin(ATMUser user, String inputUser, String inputPin) {
        return user.getUserId().equals(inputUser) && user.getPin().equals(inputPin);
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public String deposit(double amount) {
        if (amount <= 0) return "Invalid Amount ❌";

        balance += amount;
        history.add("Deposit: ₹" + amount);
        return "Deposit Successful ✅";
    }

    public String withdraw(double amount) {
        if (amount <= 0) return "Invalid Amount ❌";
        if (amount > balance) return "Insufficient Balance ❌";

        balance -= amount;
        history.add("Withdraw: ₹" + amount);
        return "Withdraw Successful ✅";
    }

    public String transfer(String receiver, double amount) {
        if (receiver == null || receiver.trim().isEmpty()) return "Receiver ID Required ❌";
        if (amount <= 0) return "Invalid Amount ❌";
        if (amount > balance) return "Insufficient Balance ❌";

        balance -= amount;
        history.add("Transfer: ₹" + amount + " to " + receiver);
        return "Transfer Successful ✅";
    }
}
