package quiz;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;

public class WelcomeScreen extends JFrame {

    public WelcomeScreen() {
        setTitle("Brain Spark Quiz");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ==== MENU BAR ====
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(100, 149, 237)); 
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JMenu adminMenu = new JMenu("Admin");
        adminMenu.setForeground(Color.WHITE);
        adminMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JMenuItem viewUsers = new JMenuItem("View Quiz Users");
        viewUsers.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        viewUsers.setBackground(Color.WHITE);
        viewUsers.setForeground(new Color(33, 147, 176));

        viewUsers.addActionListener(e -> {
    String adminName = JOptionPane.showInputDialog(this, "Enter admin name:");
    String adminPass = JOptionPane.showInputDialog(this, "Enter admin password:");

    if ("Bhoomi".equals(adminName) && "bhooms123".equals(adminPass)) {
        java.util.List<UserResult> results =UserDatabase.getResults();

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No quiz results recorded yet.", "No Data", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Define table data
        String[] columnNames = { "Username", "Score", "Total", "Category" };
        String[][] tableData = new String[results.size()][4];

        for (int i = 0; i < results.size(); i++) {
            UserResult r = results.get(i);
            tableData[i][0] = r.username;
            tableData[i][1] = String.valueOf(r.score);
            tableData[i][2] = String.valueOf(r.total);
            tableData[i][3] = r.category;
        }

        JTable table = new JTable(tableData, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.DARK_GRAY);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(58, 123, 213));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel label = new JLabel("📊 Quiz Score Records");
        label.setFont(new Font("Poppins", Font.BOLD, 18));
        label.setForeground(new Color(33, 147, 176));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "Admin View", JOptionPane.PLAIN_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid credentials!", "Access Denied", JOptionPane.ERROR_MESSAGE);
    }
});


        adminMenu.add(viewUsers);
        menuBar.add(adminMenu);
        setJMenuBar(menuBar);

        // ==== Gradient background ====
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(58, 123, 213),
                        0, getHeight(), new Color(58, 213, 174));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        setContentPane(panel);

        JLabel welcomeLabel = new JLabel("🎉 Welcome to Brain Spark Quiz 🎉");
        welcomeLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameField.setMaximumSize(new Dimension(300, 30));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        startButton.setBackground(new Color(33, 150, 243));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener(e -> {
            String username = nameField.getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name.");
            } else {
                dispose();
                new CategoryScreen(username).setVisible(true);
            }
        });

        panel.add(welcomeLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(new JLabel("Enter your name:") {{
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setForeground(Color.WHITE);
            setAlignmentX(Component.CENTER_ALIGNMENT);
        }});
        panel.add(nameField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(startButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomeScreen().setVisible(true));
    }
}
