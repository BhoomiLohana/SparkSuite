package quiz;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String FILE_PATH = "quiz_results.csv";  // File path for saving results

    // Save results to the CSV file
    public static void saveResults(List<UserResult> results) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (UserResult result : results) {
                writer.write(result.username + "," + result.score + "," + result.total + "," + result.category);
                writer.newLine();
            }
        } catch (IOException e) {
            // e.printStackTrace();  Add this to debug file save issues
        }
    }

    // Load results from the CSV file
    public static List<UserResult> loadResults() {
        List<UserResult> results = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    results.add(new UserResult(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), data[3]));
                }
            }
        } catch (IOException e) {
            // e.printStackTrace();   Add this to debug file load issues
        }
        return results;
    }
}
