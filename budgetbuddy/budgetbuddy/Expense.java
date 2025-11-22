package budgetbuddy;

import java.time.LocalDate;

public class Expense {
    private final LocalDate date;
    private final String category;
    private final String description;
    private final double amount;

    public Expense(LocalDate date, String category, String description, double amount) {
        this.date        = date;
        this.category    = category;
        this.description = description;
        this.amount      = amount;
    }

    public LocalDate getDate()       { return date; }
    public String    getCategory()   { return category; }
    public String    getDescription(){ return description; }
    public double    getAmount()     { return amount; }

    /**  
     * Converts this expense to a comma-separated string for file storage.  
     */
    @Override
    public String toString() {
        return date + "," + category + "," + description + "," + amount;
    }
    
    public static Expense fromString(String line) {
        try {
            String[] parts = line.split(",", 4);
            return new Expense(LocalDate.parse(parts[0]), parts[1], parts[2], Double.parseDouble(parts[3]));
        } catch (Exception e) {
            return null;
        }
    }
}    