package budgetbuddy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel {
    private final MainWindow mainWindow;
    private final JTextField    usernameField;
    private final JPasswordField passwordField;

    public LoginPanel(MainWindow window) {
        this.mainWindow = window;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        JLabel title = new JLabel("🔑 Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(new Color(46, 139, 87));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener((ActionEvent e) -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill both fields.");
                return;
            }
            User u = mainWindow.getUserManager().authenticateUser(user, pass);
            if (u != null) {
                mainWindow.initializeExpenseManager(u);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        });

        inputPanel.add(loginButton);
        add(inputPanel, BorderLayout.CENTER);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signUpButton.setBackground(new Color(70, 130, 180));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.addActionListener(e -> mainWindow.switchPanel("SignUpPanel"));
        add(signUpButton, BorderLayout.SOUTH);
    }
}
