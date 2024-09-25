import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class QuizGUI extends JFrame {
    private QuizManager quizManager = new QuizManager();
    private JComboBox<String> categoryBox;
    private JTextArea questionArea;
    private JTextField answerField;
    private JLabel scoreLabel;
    private JButton submitButton;
    private JProgressBar progressBar;
    private JButton logoutButton;  // Logout btn
    private int score = 0;
    private List<Quiz> quizzes;
    private int currentQuestionIndex = 0;
    private int totalQuestions = 0;

    public QuizGUI() {
        setTitle("Quiz Application");
        setSize(500, 400);  // Adjust window size if needed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main layout control
        setLayout(new BorderLayout(5, 5));

        // Top panel for category selection and logout btn
        JPanel topPanel = new JPanel(new BorderLayout());

        // Category selection combo box
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        categoryBox = new JComboBox<>(new String[]{"Science", "Math", "History"});
        categoryBox.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNewCategory();
            }
        });
        categoryPanel.add(new JLabel("Select Category:"));
        categoryPanel.add(categoryBox);
        topPanel.add(categoryPanel, BorderLayout.WEST);

        // Logout btn on the top right
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 12));
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setPreferredSize(new Dimension(80, 30));  // Make logout btn smaller
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();  // Handle logout
            }
        });
        topPanel.add(logoutButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Progress Bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Arial", Font.PLAIN, 12));
        add(progressBar, BorderLayout.CENTER);

        // Main panel for question text and answer field
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Question display area
        questionArea = new JTextArea(4, 30);
        questionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(questionArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Answer input field
        JPanel answerPanel = new JPanel(new BorderLayout());
        JLabel answerLabel = new JLabel("Answer:");
        answerField = new JTextField();
        answerPanel.add(answerLabel, BorderLayout.WEST);
        answerPanel.add(answerField, BorderLayout.CENTER);
        mainPanel.add(answerPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Bottom panel for submit btn and score
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        // Score label
        scoreLabel = new JLabel("Score: 0", JLabel.LEFT);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bottomPanel.add(scoreLabel);

        // Submit btn
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setPreferredSize(new Dimension(150, 40));
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                displayNextQuestion();
            }
        });
        bottomPanel.add(submitButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Load initial category
        loadNewCategory();
    }

    private void logout() {
        // Handle logout logic
        this.dispose();  // Close current window
        new LoginGUI().setVisible(true);  // Open login window
    }

    // Load new questions based on selected category
    private void loadNewCategory() {
        try {
            String selectedCategory = (String) categoryBox.getSelectedItem();
            quizzes = quizManager.getQuizzesByCategory(selectedCategory);
            currentQuestionIndex = 0;
            totalQuestions = quizzes.size();
            score = 0;
            scoreLabel.setText("Score: " + score);
            submitButton.setEnabled(true);
            progressBar.setValue(0);
            displayNextQuestion();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading questions for category: " + categoryBox.getSelectedItem());
        }
    }

    // Display next question
    private void displayNextQuestion() {
        if (currentQuestionIndex < quizzes.size()) {
            Quiz currentQuiz = quizzes.get(currentQuestionIndex);
            questionArea.setText(currentQuiz.getQuestion());
            answerField.setText("");

            int progress = (int) (((double) currentQuestionIndex / totalQuestions) * 100);
            progressBar.setValue(progress);
        } else {
            questionArea.setText("Quiz completed! Your final score: " + score);
            submitButton.setEnabled(false);
            progressBar.setValue(100);
        }
    }

    // Check user's answer
    private void checkAnswer() {
        String userAnswer = answerField.getText().trim();
        String correctAnswer = quizzes.get(currentQuestionIndex).getAnswer().trim();

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            score++;
            scoreLabel.setText("Score: " + score);
        }

        currentQuestionIndex++;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new QuizGUI().setVisible(true);
        });
    }
}
