package quiz;

import javax.swing.*;
import java.awt.*;


public class ResultScreen extends JFrame {
    public ResultScreen(String username, String category, int score, int total) {
        setTitle("Quiz Result");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Save result to UserDatabase
        UserDatabase.addResult(new UserResult(username, score, total, category));

        // ==== Result Panel ====
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(131, 96, 195),
                        0, getHeight(), new Color(46, 191, 145));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        setContentPane(panel);

        JLabel resultLabel = new JLabel("🎯 Quiz Completed!");
        resultLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("👤 Name: " + username);
        JLabel scoreLabel = new JLabel("✅ Score: " + score + "/" + total);
        JLabel categoryLabel = new JLabel("📚 Category: " + category);

        for (JLabel label : new JLabel[]{nameLabel, scoreLabel, categoryLabel}) {
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            label.setForeground(Color.WHITE);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        restartButton.setBackground(new Color(33, 150, 243));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);
        restartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        restartButton.addActionListener(e -> {
            dispose();
            new WelcomeScreen().setVisible(true);
        });

        panel.add(resultLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(scoreLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(categoryLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(restartButton);
    }
}
