package quiz;
import java.awt.*;
import javax.swing.*;

public class CategoryScreen extends JFrame {
    private final String username;

    public CategoryScreen(String username) {
        this.username = username;

        // Setting up the window
        setTitle("Select Category");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Main panel with gradient background
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(33, 147, 176),
                        0, getHeight(), new Color(109, 213, 237));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        setContentPane(contentPanel);

        // Title label
        JLabel titleLabel = new JLabel("Choose a Category");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Category buttons
        JButton oopJavaButton = createCategoryButton("OOP Java");
        JButton brainTestingButton = createCategoryButton("Brain Testing");

        // Adding components to the panel
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(oopJavaButton);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(brainTestingButton);
        contentPanel.add(Box.createVerticalGlue());
    }

    // Create a category button with a consistent style
    private JButton createCategoryButton(String category) {
        JButton button = new JButton(category);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        button.setBackground(new Color(255, 255, 255));
        button.setForeground(new Color(33, 147, 176));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(33, 147, 176), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(300, 50));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(33, 147, 176));
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setForeground(new Color(33, 147, 176));
            }
        });

        // Action listener for category selection
        button.addActionListener(e -> {
            new QuizWindow(username, category).setVisible(true);
            dispose();
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CategoryScreen("Test User").setVisible(true);
        });
    }
}
