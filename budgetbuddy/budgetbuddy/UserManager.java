package budgetbuddy;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class UserManager {
    private static final String USER_FILE = "user.txt";
    private Map<String, User> users = new HashMap<>();

    public UserManager() {
        ensureUserFileExists();
        loadAllUsers();
    }

    // Ensure the user.txt file exists (else create an empty one)
    private void ensureUserFileExists() {
        Path path = Paths.get(USER_FILE);
        if (Files.notExists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Load all users into memory at startup
    private void loadAllUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    String user = parts[0], pass = parts[1];
                    double salary = Double.parseDouble(parts[2]);
                    users.put(user, new User(user, pass, salary));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Register a new user (appends to user.txt)
    public boolean createUser(String username, String password, double salary) {
        if (users.containsKey(username)) return false;
        users.put(username, new User(username, password, salary));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(username + "," + password + "," + salary);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Authenticate by reading in-memory map
    public User authenticateUser(String username, String password) {
        User u = users.get(username);
        if (u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }

    // Update salary both in-memory and in file
    public void updateSalary(String username, double newSalary) {
        User u = users.get(username);
        if (u != null) {
            u.setSalary(newSalary);
            // Rewrite entire file with updated salaries
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
                for (User user : users.values()) {
                    writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getSalary());
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
