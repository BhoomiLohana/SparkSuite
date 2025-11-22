package quiz;
public class UserResult {
    public String username;
    public int score;
    public int total;
    public String category;

    public UserResult(String username, int score, int total, String category) {
        this.username = username;
        this.score = score;
        this.total = total;
        this.category = category;
    }
}
