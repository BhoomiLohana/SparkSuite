package quiz;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private static List<UserResult> resultList = new ArrayList<>();

    static {
        // Load previous results from CSV when the app starts
        resultList = FileHandler.loadResults();
    }

    // Add a new result to the list and save it to the CSV file
    public static void addResult(UserResult result) {
        resultList.add(result);
        FileHandler.saveResults(resultList);  // Save updated results to file
    }

    // Get the list of results
    public static List<UserResult> getResults() {
        return resultList;
    }
}
