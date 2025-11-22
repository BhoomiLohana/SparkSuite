package currency;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;

public class CurrencyConverterApp extends JFrame {
    private final JComboBox<String> fromCurrencyBox;
    private final JComboBox<String> toCurrencyBox;
    private final JTextField amountField;
    private final JLabel resultLabel;
    private final JTextArea historyArea;

    private final String[] currencies = {
        "USD", "EUR", "PKR", "GBP", "JPY", "INR", "AUD", "CAD", "CNY", "SAR"
    };

    private final ArrayList<String> historyList = new ArrayList<>();

    public CurrencyConverterApp() {
        setTitle(" 💱 Currency Converter");
        setSize(850, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(232, 245, 253);
                Color color2 = new Color(208, 240, 235);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("🌐 Currency Converter", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(33, 64, 95));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);
        gbc.gridwidth = 1;

        fromCurrencyBox = createComboBox();
        toCurrencyBox = createComboBox();

        amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(300, 35));
        amountField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        amountField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(119, 221, 183), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        resultLabel = new JLabel("💰 Converted amount will appear here");
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        resultLabel.setForeground(new Color(26, 82, 118));

        historyArea = new JTextArea(10, 40);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        historyArea.setBackground(new Color(250, 250, 250));
        historyArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(160, 200, 180), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyScroll.setPreferredSize(new Dimension(720, 220));

        JLabel historyLabel = new JLabel("📜 Conversion History");
        historyLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        historyLabel.setForeground(new Color(52, 73, 94));

        JButton convertButton = createStyledButton("Convert 🔁");
        JButton resetButton = createStyledButton("Reset 🔄");

        // Layout Setup
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("From Currency:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        panel.add(fromCurrencyBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("To Currency:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        panel.add(toCurrencyBox, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Amount:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        panel.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(convertButton, gbc);

        gbc.gridy = 5;
        panel.add(resultLabel, gbc);

        gbc.gridy = 6;
        panel.add(historyLabel, gbc);

        gbc.gridy = 7;
        panel.add(historyScroll, gbc);

        gbc.gridy = 8;
        panel.add(resetButton, gbc);

        add(panel);

        convertButton.addActionListener(new ConvertButtonListener());
        resetButton.addActionListener(new ResetButtonListener());
    }

    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(currencies);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        comboBox.setPreferredSize(new Dimension(300, 35));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(new Color(44, 62, 80));
        return comboBox;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(280, 50));
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(new Color(27, 79, 114), 2));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(31, 97, 141));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185));
            }
        });

        return button;
    }

    private class ConvertButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String fromCode = (String) fromCurrencyBox.getSelectedItem();
                String toCode = (String) toCurrencyBox.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());

                if (amount < 0) {
                    JOptionPane.showMessageDialog(null, "Amount must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Currency from = CurrencyFactory.getCurrency(fromCode);
                Currency to = CurrencyFactory.getCurrency(toCode);

                if (from == null || to == null) {
                    JOptionPane.showMessageDialog(null, "Invalid currency selected.", "Selection Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ConversionStrategy strategy = new StandardConversion();
                double result = strategy.convert(from, to, amount);

                resultLabel.setText(String.format("💸 %.2f %s = %.2f %s", amount, fromCode, result, toCode));

                String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String historyEntry = String.format("[%s] %.2f %s -> %.2f %s", timeStamp, amount, fromCode, result, toCode);
                historyList.add(historyEntry);
                updateHistoryArea();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            amountField.setText("");
            fromCurrencyBox.setSelectedIndex(0);
            toCurrencyBox.setSelectedIndex(0);
            resultLabel.setText("💰 Converted amount will appear here");
        }
    }

    private void updateHistoryArea() {
        StringBuilder historyText = new StringBuilder();
        for (String entry : historyList) {
            historyText.append(entry).append("\n");
        }
        historyArea.setText(historyText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CurrencyConverterApp().setVisible(true));
    }
}