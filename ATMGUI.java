package atmmodel;

import javax.swing.*;
import java.awt.*;

public class ATMGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private ATMUser user;
    private ATMService service;

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    private JTextField userField;
    private JPasswordField pinField;

    private JLabel balanceLabel;

    public ATMGUI(ATMUser user, ATMService service) {
        this.user = user;
        this.service = service;

        setTitle("ATM Interface - Task 3 (Swing)");
        setSize(620, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel.add(loginPanel(), "LOGIN");
        mainPanel.add(dashboardPanel(), "DASHBOARD");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
        setVisible(true);
    }

    private JPanel loginPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JLabel title = new JLabel("ATM Login", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        userField = new JTextField();
        pinField = new JPasswordField();

        userField.setFont(new Font("Arial", Font.PLAIN, 16));
        pinField.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setPreferredSize(new Dimension(200, 45));

        loginBtn.addActionListener(e -> doLogin());

        panel.add(title);
        panel.add(new JLabel("User ID:"));
        panel.add(userField);
        panel.add(new JLabel("PIN:"));
        panel.add(pinField);
        panel.add(loginBtn);

        return panel;
    }

    private JPanel dashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("ATM Dashboard", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));

        balanceLabel = new JLabel("", JLabel.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        updateBalance();

        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton transferBtn = new JButton("Transfer");
        JButton historyBtn = new JButton("Transaction History");
        JButton logoutBtn = new JButton("Logout");

        Font btnFont = new Font("Arial", Font.BOLD, 15);

        depositBtn.setFont(btnFont);
        withdrawBtn.setFont(btnFont);
        transferBtn.setFont(btnFont);
        historyBtn.setFont(btnFont);
        logoutBtn.setFont(btnFont);

        Dimension btnSize = new Dimension(200, 55);
        depositBtn.setPreferredSize(btnSize);
        withdrawBtn.setPreferredSize(btnSize);
        transferBtn.setPreferredSize(btnSize);
        historyBtn.setPreferredSize(btnSize);
        logoutBtn.setPreferredSize(btnSize);

        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        transferBtn.addActionListener(e -> transfer());
        historyBtn.addActionListener(e -> showHistory());
        logoutBtn.addActionListener(e -> logout());

        JPanel btnPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(25, 50, 10, 50));

        btnPanel.add(depositBtn);
        btnPanel.add(withdrawBtn);
        btnPanel.add(transferBtn);
        btnPanel.add(historyBtn);
        btnPanel.add(logoutBtn);
        btnPanel.add(new JLabel("")); // Empty cell for symmetry

        panel.add(title, BorderLayout.NORTH);
        panel.add(balanceLabel, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void doLogin() {
        String inputUser = userField.getText().trim();
        String inputPin = new String(pinField.getPassword()).trim();

        if (service.validateLogin(user, inputUser, inputPin)) {
            JOptionPane.showMessageDialog(this, "Login Successful !!");
            updateBalance();
            cardLayout.show(mainPanel, "DASHBOARD");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials ❌");
        }
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: ₹" + String.format("%.2f", service.getBalance()));
    }

    private void deposit() {
        String input = JOptionPane.showInputDialog(this, "Enter Deposit Amount:");
        if (input == null) return;

        try {
            double amt = Double.parseDouble(input);
            JOptionPane.showMessageDialog(this, service.deposit(amt));
            updateBalance();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter valid amount ❌");
        }
    }

    private void withdraw() {
        String input = JOptionPane.showInputDialog(this, "Enter Withdraw Amount:");
        if (input == null) return;

        try {
            double amt = Double.parseDouble(input);
            JOptionPane.showMessageDialog(this, service.withdraw(amt));
            updateBalance();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter valid amount ❌");
        }
    }

    private void transfer() {
        JTextField receiver = new JTextField();
        JTextField amount = new JTextField();

        Object[] msg = {
                "Receiver User ID:", receiver,
                "Amount:", amount
        };

        int option = JOptionPane.showConfirmDialog(this, msg, "Transfer", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String r = receiver.getText().trim();
                double amt = Double.parseDouble(amount.getText().trim());

                JOptionPane.showMessageDialog(this, service.transfer(r, amt));
                updateBalance();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input ❌");
            }
        }
    }

    private void showHistory() {
        StringBuilder sb = new StringBuilder();

        if (service.getHistory().isEmpty()) {
            sb.append("No Transactions Yet.");
        } else {
            for (String h : service.getHistory()) sb.append(h).append("\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setFont(new Font("Arial", Font.PLAIN, 14));

        JOptionPane.showMessageDialog(this, new JScrollPane(area),
                "Transaction History", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        userField.setText("");
        pinField.setText("");
        JOptionPane.showMessageDialog(this, "Logged Out Successfully !!");
        cardLayout.show(mainPanel, "LOGIN");
    }
}
