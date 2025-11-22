package quiz;
import java.awt.*;
import java.awt.event.*;
// import java.util.Locale;
import java.util.Random;
import javax.swing.*;


public class QuizWindow extends JFrame {
    private final String username;
    private final String category;
    private int score = 0;
    private int questionIndex = 0;
    private int timeRemaining = 100; // Timer duration in seconds
    private Timer timer;

    private final JLabel questionLabel = new JLabel();
    private final JButton option1Button = new JButton();
    private final JButton option2Button = new JButton();
    private final JButton option3Button = new JButton();
    private final JButton option4Button = new JButton();
    private final JLabel scoreLabel = new JLabel("Score: 0");
    private final JLabel timerLabel = new JLabel("Time Remaining: " + timeRemaining);

    private final String[][] currentQuestions;
    private String selectedAnswer = null;

    public QuizWindow(String username, String category) {
        this.username = username;
        this.category = category;

        // Get random questions
        currentQuestions = RandomQuiz.getRandomQuestions(category);

        setTitle("Quiz - " + category);
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with gradient background
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(33, 147, 176),
                        0, getHeight(), new Color(109, 213, 237));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        setContentPane(contentPanel);

        // Setup score and timer labels
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setMaximumSize(new Dimension(600, 80));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Button styling setup
        Font optionFont = new Font("Segoe UI", Font.PLAIN, 16);
        JButton[] options = {option1Button, option2Button, option3Button, option4Button};
        for (JButton btn : options) {
            btn.setFont(optionFont);
            btn.setBackground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setMaximumSize(new Dimension(500, 40));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        }

        // Highlight selected option
        option1Button.addActionListener(e -> {
            selectedAnswer = option1Button.getText();
            highlightSelected(option1Button);
        });
        option2Button.addActionListener(e -> {
            selectedAnswer = option2Button.getText();
            highlightSelected(option2Button);
        });
        option3Button.addActionListener(e -> {
            selectedAnswer = option3Button.getText();
            highlightSelected(option3Button);
        });
        option4Button.addActionListener(e -> {
            selectedAnswer = option4Button.getText();
            highlightSelected(option4Button);
        });

        // Submit button
        JButton submitButton = new JButton("Submit Answer");
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        submitButton.setBackground(new Color(76, 175, 80));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.setMaximumSize(new Dimension(200, 40));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener((ActionEvent e) -> {
            if (selectedAnswer == null) {
                JOptionPane.showMessageDialog(this, "Please select an answer before submitting.");
                return;
            }
            checkAnswer(selectedAnswer);
            selectedAnswer = null;
            showNextQuestion();
        });

        contentPanel.add(scoreLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(timerLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(questionLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(option1Button);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(option2Button);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(option3Button);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(option4Button);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(submitButton);

        startTimer();
        showNextQuestion();
    }

    private void startTimer() {
        timer = new Timer(1000, (ActionEvent e) -> {
            if (timeRemaining > 0) {
                timeRemaining--;
                timerLabel.setText("Time Remaining: " + timeRemaining);
            } else {
                timer.stop();
                JOptionPane.showMessageDialog(QuizWindow.this, "Time's up! Submitting your answers.");
                endQuiz();
            }
        });
        timer.start();
    }

    private void resetOptionButtons() {
        JButton[] allOptions = {option1Button, option2Button, option3Button, option4Button};
        for (JButton btn : allOptions) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        }
    }

    private void highlightSelected(JButton selectedBtn) {
        resetOptionButtons();
        selectedBtn.setBackground(new Color(76, 175, 80));
        selectedBtn.setForeground(Color.WHITE);
        selectedBtn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
    }

    private void showNextQuestion() {
        resetOptionButtons();
        if (questionIndex < currentQuestions.length) {
            String[] question = currentQuestions[questionIndex];
            questionLabel.setText("<html><div style='text-align: center;'>" + question[0] + "</div></html>");
            option1Button.setText(question[1]);
            option2Button.setText(question[2]);
            option3Button.setText(question[3]);
            option4Button.setText(question[4]);
        } else {
            endQuiz();
        }
    }

    private void checkAnswer(String selectedAnswer) {
        String correctAnswer = currentQuestions[questionIndex][5];
        if (selectedAnswer.equals(correctAnswer)) {
            score++;
        }
        questionIndex++;
        scoreLabel.setText("Score: " + score);
    }

    private void endQuiz() {
    JOptionPane.showMessageDialog(this, "Quiz Over! Your score is: " + score);
    // Save the result permanently
    UserDatabase.addResult(new UserResult(username, score, currentQuestions.length, category)); 
    dispose();
    new ResultScreen(username, category, score, currentQuestions.length).setVisible(true);
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new QuizWindow("Test User", "OOP Java").setVisible(true);
        });
    }
}

class RandomQuiz {
    private static final Random random = new Random();

    public static String[][] getRandomQuestions(String category) {
        String[][] questionPool;

        if (category.equalsIgnoreCase("OOP Java")) {
            questionPool = Questions.OOP_JAVA_QUESTIONS;
        } else if (category.equalsIgnoreCase("Brain Testing")) {
            questionPool = Questions.BRAIN_TESTING_QUESTIONS;
        } else {
            return new String[0][0]; // Unknown category
        }

        // Shuffle and pick 5 random unique questions
        int total = Math.min(5, questionPool.length);
        String[][] selectedQuestions = new String[total][6];
        boolean[] used = new boolean[questionPool.length];

        int count = 0;
        while (count < total) {
            int index = random.nextInt(questionPool.length);
            if (!used[index]) {
                used[index] = true;
                selectedQuestions[count] = questionPool[index];
                count++;
            }
        }

        return selectedQuestions;
    }
}
