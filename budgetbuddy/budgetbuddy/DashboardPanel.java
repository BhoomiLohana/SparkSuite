package budgetbuddy;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private final MainWindow mainWindow;
    private JLabel salaryLabel;
    private JLabel remainingBalanceLabel;

    public DashboardPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        // Title
        JLabel title = new JLabel("📊 Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        // Info panel
        JPanel infoPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        salaryLabel = new JLabel("Current Salary: ₹0.00", SwingConstants.CENTER);
        salaryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        infoPanel.add(salaryLabel);

        remainingBalanceLabel = new JLabel("Remaining Balance: ₹0.00", SwingConstants.CENTER);
        remainingBalanceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        infoPanel.add(remainingBalanceLabel);

        JButton addSalaryBtn = new JButton("➕ Add Salary");
        addSalaryBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        addSalaryBtn.setBackground(new Color(255, 140, 0));
        addSalaryBtn.setForeground(Color.WHITE);
        addSalaryBtn.setFocusPainted(false);
        addSalaryBtn.addActionListener(e -> onAddSalary());
        infoPanel.add(addSalaryBtn);

        JButton addExpenseBtn = new JButton("➕ Add Expense");
        addExpenseBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        addExpenseBtn.setBackground(new Color(60, 179, 113));
        addExpenseBtn.setForeground(Color.WHITE);
        addExpenseBtn.setFocusPainted(false);
        addExpenseBtn.addActionListener(e -> mainWindow.switchPanel("AddExpensePanel"));
        infoPanel.add(addExpenseBtn);

        JButton viewHistoryBtn = new JButton("📄 View History");
        viewHistoryBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        viewHistoryBtn.setBackground(new Color(100, 149, 237));
        viewHistoryBtn.setForeground(Color.WHITE);
        viewHistoryBtn.setFocusPainted(false);
        viewHistoryBtn.addActionListener(e -> {
            mainWindow.getHistoryPanel().reload(); // 👈 Make sure to call this
            mainWindow.switchPanel("HistoryPanel");
        });
        infoPanel.add(viewHistoryBtn);

        JButton logoutBtn = new JButton("🔒 Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logoutBtn.setBackground(new Color(220, 20, 60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> mainWindow.switchPanel("WelcomePanel"));
        infoPanel.add(logoutBtn);

        add(infoPanel, BorderLayout.CENTER);
    }

    public void updateSalaryLabel(double salary, double remainingBalance) {
        salaryLabel.setText("Current Salary: ₹" + String.format("%.2f", salary));
        remainingBalanceLabel.setText("Remaining Balance: ₹" + String.format("%.2f", remainingBalance));
    }

    private void onAddSalary() {
        String input = JOptionPane.showInputDialog(this, "Enter salary amount to add (₹):");
        if (input != null && !input.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(input);
                if (amount <= 0) throw new NumberFormatException();

                User user = mainWindow.getLoggedInUser();
                user.setSalary(user.getSalary() + amount);
                mainWindow.getUserManager().updateSalary(user.getUsername(), user.getSalary());
                updateSalaryLabel(user.getSalary(), user.getSalary());

                JOptionPane.showMessageDialog(this, "Salary added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            }
        }
    }
}
