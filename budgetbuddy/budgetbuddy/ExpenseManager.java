package budgetbuddy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private final List<Expense> expenses = new ArrayList<>();
    private final String filePath;

    public ExpenseManager(String username) {
        this.filePath = "expenses_" + username + ".txt";
        loadExpensesFromFile(); // ✅ Load existing expenses from file
    }

    private void loadExpensesFromFile() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Expense exp = Expense.fromString(line);
                if (exp != null) expenses.add(exp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense e) {
        expenses.add(e);
        saveExpenseToFile(e);
    }

    private void saveExpenseToFile(Expense e) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(e.toString());
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
