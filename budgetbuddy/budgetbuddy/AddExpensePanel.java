package budgetbuddy;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.text.NumberFormat;

public class AddExpensePanel extends JPanel {
    private final MainWindow mainWindow;
    private final JTextField categoryField;
    private final JTextField descriptionField;
    private final JFormattedTextField amountField;

    public AddExpensePanel(MainWindow window) {
        this.mainWindow = window;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        // Title
        JLabel title = new JLabel("➕ Add Expense", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        // Input grid
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        inputPanel.setOpaque(false);

        inputPanel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        inputPanel.add(categoryField);

        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Amount (₹):"));
        amountField = new JFormattedTextField(NumberFormat.getNumberInstance());
        amountField.setValue(null);
        inputPanel.add(amountField);

        add(inputPanel, BorderLayout.CENTER);

        // Buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);

        JButton saveBtn = new JButton("💾 Save");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveBtn.setBackground(new Color(46, 139, 87));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> onSave());

        JButton backBtn = new JButton("← Back");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.setBackground(new Color(70, 130, 180));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> mainWindow.switchPanel("DashboardPanel"));

        bottomPanel.add(saveBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void onSave() {
        String category    = categoryField.getText().trim();
        String description = descriptionField.getText().trim();
        Number value       = (Number) amountField.getValue();

        if (category.isEmpty() || description.isEmpty() || value == null) {
            JOptionPane.showMessageDialog(this, "Please fill all fields with valid data.");
            return;
        }

        double amount = value.doubleValue();
        User loggedInUser = mainWindow.getLoggedInUser();
        double currentSalary = loggedInUser.getSalary();

        if (currentSalary < amount) {
            JOptionPane.showMessageDialog(this, "Insufficient funds to add this expense.");
            return;
        }

        // Deduct from salary and persist
        loggedInUser.setSalary(currentSalary - amount);
        mainWindow.getUserManager().updateSalary(loggedInUser.getUsername(), loggedInUser.getSalary());

        // Record expense
        Expense expense = new Expense(LocalDate.now(), category, description, amount);
        mainWindow.getExpenseManager().addExpense(expense);

        // Update salary display on dashboard
        double remainingBalance = loggedInUser.getSalary();
        mainWindow.getDashboardPanel().updateSalaryLabel(loggedInUser.getSalary(), remainingBalance);

        JOptionPane.showMessageDialog(this, "Expense added successfully!");
        categoryField.setText("");
        descriptionField.setText("");
        amountField.setValue(null);

        mainWindow.switchPanel("DashboardPanel");
    }
}
