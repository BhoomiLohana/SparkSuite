package budgetbuddy;

public class User {
    private final String username;
    private final String password;
    private double salary;

    public User(String username, String password, double salary) {
        this.username = username;
        this.password = password;
        this.salary = salary;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
