import currency.CurrencyConverterApp;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import quiz.WelcomeScreen;

public class MainApp extends JFrame {

    public MainApp() {
        setTitle("SparkSuite - Mini App Market");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Title Section
        JLabel title = new JLabel("SparkSuite - Mini App Market", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        JLabel subtitle = new JLabel("Welcome, Spark User!", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(Color.WHITE);
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Cards Section
        JPanel cardsPanel = new JPanel(new GridLayout(2, 3, 25, 25));
        cardsPanel.setOpaque(false);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        cardsPanel.add(createAppCard("🧠", "Brain Spark Quiz", new Color(255, 138, 128)));
        cardsPanel.add(createAppCard("💱", "Currency Converter", new Color(129, 212, 250)));
        cardsPanel.add(createAppCard("💰", "Expense Tracker", new Color(174, 213, 129)));
        cardsPanel.add(createAppCard("🌦", "CARD CREATOR", new Color(179, 157, 219)));
        
        // Footer
        JLabel footer = new JLabel("© 2025 Developed by Your Bhoomi Heer", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footer.setForeground(Color.WHITE);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Layout container
        JPanel glassPanel = new JPanel(new BorderLayout());
        glassPanel.setOpaque(false);
        glassPanel.add(title, BorderLayout.NORTH);
        glassPanel.add(subtitle, BorderLayout.CENTER);
        glassPanel.add(cardsPanel, BorderLayout.SOUTH);

        JPanel outerWrapper = new JPanel(new BorderLayout());
        outerWrapper.setOpaque(false);
        outerWrapper.add(glassPanel, BorderLayout.NORTH);
        outerWrapper.add(cardsPanel, BorderLayout.CENTER);
        outerWrapper.add(footer, BorderLayout.SOUTH);

        GradientPanel gradientBackground = new GradientPanel();
        gradientBackground.setLayout(new BorderLayout());
        gradientBackground.add(outerWrapper, BorderLayout.CENTER);

        setContentPane(gradientBackground);
    }

    private JPanel createAppCard(String icon, String title, Color bgColor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 180));
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JButton launch = new JButton("Launch");
        launch.setFocusPainted(false);
        launch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        launch.setBackground(bgColor.darker());
        launch.setForeground(Color.WHITE);
        launch.setCursor(new Cursor(Cursor.HAND_CURSOR));

        launch.addActionListener(e -> {
            switch (title) {
                case "Currency Converter" -> {
                    // Launch Currency Converter
                    new CurrencyConverterApp().setVisible(true);
                    dispose(); // Close MainApp window
                }
                case "Brain Spark Quiz" -> {
                    // Launch Welcome Screen for Brain Spark Quiz
                    new WelcomeScreen().setVisible(true);
                    dispose(); // Close MainApp window
                }
                case "Expense Tracker" -> {
                    // Launch BudgetBuddy Main class
                    budgetbuddy.MainWindow.main(null); // Call main method of BudgetBuddy
                    dispose(); // Close MainApp window
                }
                
                default -> JOptionPane.showMessageDialog(this, title + " is launching...");
            }
        });

        card.add(iconLabel, BorderLayout.NORTH);
        card.add(titleLabel, BorderLayout.CENTER);
        card.add(launch, BorderLayout.SOUTH);

        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 220));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 180));
            }
        });

        return card;
    }

    // Gradient background
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            GradientPaint gp = new GradientPaint(0, 0, new Color(63, 81, 181), width, height, new Color(144, 202, 249));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}
