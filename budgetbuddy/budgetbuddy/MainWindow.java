package budgetbuddy;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel container;
    private final UserManager userManager;

    private ExpenseManager expenseManager;
    private User loggedInUser;

    private DashboardPanel dashboardPanel;
    private HistoryPanel historyPanel;
    private AddExpensePanel addExpensePanel;

    public MainWindow() {
        super("💰 BudgetBuddy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        userManager = new UserManager();
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        // Only these at startup:
        container.add(new WelcomePanel(this), "WelcomePanel");
        container.add(new SignUpPanel(this),   "SignUpPanel");
        container.add(new LoginPanel(this),     "LoginPanel");

        setContentPane(container);

        // Show welcome first
        switchPanel("WelcomePanel");

        // Finally, make it visible
        setVisible(true);
    }

    /** Switches the visible card in the container. */
    public void switchPanel(String name) {
        cardLayout.show(container, name);
    }

    /**
     * Called by LoginPanel after a successful login.
     * Builds the panels that depend on a non-null ExpenseManager,
     * then navigates to the dashboard.
     */
    public void initializeExpenseManager(User user) {
        this.loggedInUser   = user;
        this.expenseManager = new ExpenseManager(user.getUsername());

        // Now safe to create expense-dependent panels:
        dashboardPanel   = new DashboardPanel(this);
        historyPanel     = new HistoryPanel(this);
        addExpensePanel  = new AddExpensePanel(this);

        container.add(dashboardPanel,  "DashboardPanel");
        container.add(historyPanel,    "HistoryPanel");
        container.add(addExpensePanel, "AddExpensePanel");

        // Initialize dashboard values and show it
        double salary = user.getSalary();
        dashboardPanel.updateSalaryLabel(salary, salary);
        switchPanel("DashboardPanel");
    }

    // Getters used by other panels:
    public UserManager    getUserManager()    { return userManager; }
    public ExpenseManager getExpenseManager() { return expenseManager; }
    public User           getLoggedInUser()   { return loggedInUser; }
    public DashboardPanel getDashboardPanel() { return dashboardPanel; }
    public HistoryPanel   getHistoryPanel()   { return historyPanel; }
    public AddExpensePanel getAddExpensePanel(){ return addExpensePanel; }

    /** Entry point: runs the UI creation on the EDT. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}
