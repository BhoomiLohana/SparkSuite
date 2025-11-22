package budgetbuddy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPanel extends JPanel {
    private MainWindow mainWindow;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField salaryField;

    public SignUpPanel(MainWindow window) {
        this.mainWindow = window;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        JLabel title = new JLabel("📝 Sign Up", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);

        inputPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        inputPanel.add(passwordField);

        inputPanel.add(new JLabel("Salary:"));
        salaryField = new JTextField();
        inputPanel.add(salaryField);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signUpButton.setBackground(new Color(46, 139, 87));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String salaryText = salaryField.getText().trim();
            
            if (username.isEmpty() || password.isEmpty() || salaryText.isEmpty()) {
                JOptionPane.showMessageDialog(SignUpPanel.this, "Please fill all fields.");
                return;
            }
            
            try {
                double salary = Double.parseDouble(salaryText);
                if (salary <= 0) {
                    JOptionPane.showMessageDialog(SignUpPanel.this, "Salary must be greater than 0.");
                    return;
                }
                
                // Assuming UserManager class has a method to create a new user
                boolean isCreated = mainWindow.getUserManager().createUser(username, password, salary);
                if (isCreated) {
                    JOptionPane.showMessageDialog(SignUpPanel.this, "User created successfully!");
                    mainWindow.switchPanel("LoginPanel");
                } else {
                    JOptionPane.showMessageDialog(SignUpPanel.this, "Username already exists.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(SignUpPanel.this, "Invalid salary entered.");
            }
        });

        inputPanel.add(signUpButton);

        add(inputPanel, BorderLayout.CENTER);

        // Button to navigate back to Login screen
        JButton loginButton = new JButton("Already have an account? Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(new Color(100, 149, 237));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> mainWindow.switchPanel("LoginPanel"));

        add(loginButton, BorderLayout.SOUTH);
    }
}
