package budgetbuddy;

import java.awt.*;
import javax.swing.*;

public class WelcomePanel extends JPanel {
    private MainWindow mainWindow;

    public WelcomePanel(MainWindow window) {
        this.mainWindow = window;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 250, 255)); // Soft blue background

        JLabel title = new JLabel("👋 Welcome to BudgetBuddy", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JButton signUpBtn = createStyledButton("📝 Sign Up");
        JButton loginBtn = createStyledButton("🔐 Login");

        signUpBtn.addActionListener(e -> mainWindow.switchPanel("SignUpPanel"));
        loginBtn.addActionListener(e -> mainWindow.switchPanel("LoginPanel"));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonsPanel.add(signUpBtn);
        buttonsPanel.add(loginBtn);

        add(buttonsPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        button.setBackground(new Color(100, 149, 237)); // Cornflower Blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
