package budgetbuddy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoryPanel extends JPanel {
    private final MainWindow mainWindow;
    private final DefaultTableModel model;
    private final JTable expenseTable;
    private static final String[] COLUMNS = {"Date", "Category", "Description", "Amount (₹)"};

    public HistoryPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("🗂 Expense History", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        // Table model and table
        model = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // read-only
            }
        };
        expenseTable = new JTable(model);
        expenseTable.setFillsViewportHeight(true);
        expenseTable.setRowHeight(24);
        expenseTable.getTableHeader().setReorderingAllowed(false);
        expenseTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        expenseTable.setShowGrid(true);
        expenseTable.setGridColor(Color.LIGHT_GRAY);

        // Scroll pane with preferred size
        JScrollPane scroll = new JScrollPane(expenseTable);
        scroll.setPreferredSize(new Dimension(700, 300));
        add(scroll, BorderLayout.CENTER);

        // Back button
        JButton back = new JButton("← Back to Dashboard");
        back.setFont(new Font("Segoe UI", Font.BOLD, 16));
        back.setBackground(new Color(100, 149, 237));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        back.addActionListener(e -> mainWindow.switchPanel("DashboardPanel"));
        add(back, BorderLayout.SOUTH);
    }

    /** 
     * Reloads table data from ExpenseManager.
     * Call this each time *before* you show HistoryPanel.
     */
    public void reload() {
        model.setRowCount(0);  // clear existing
        List<Expense> list = mainWindow.getExpenseManager().getExpenses();
        for (Expense exp : list) {
            model.addRow(new Object[]{
                exp.getDate().toString(),
                exp.getCategory(),
                exp.getDescription(),
                String.format("%.2f", exp.getAmount())
            });
        }
    }
}
