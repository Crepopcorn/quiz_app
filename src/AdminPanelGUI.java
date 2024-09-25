import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.SQLException;

public class AdminPanelGUI extends JFrame {
    private JTextField questionField;
    private JTextField answerField;
    private JComboBox<String> categoryBox;
    private QuizManager quizManager = new QuizManager();  // Initialize QuizManager

    public AdminPanelGUI() {
        setTitle("Admin Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Category:"));
        categoryBox = new JComboBox<>(new String[]{"Science", "Math", "History"});
        add(categoryBox);

        add(new JLabel("Question:"));
        questionField = new JTextField();
        add(questionField);

        add(new JLabel("Answer:"));
        answerField = new JTextField();
        add(answerField);

        JButton addButton = new JButton("Add Question");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get question/answer details
                    String question = questionField.getText();
                    String answer = answerField.getText();
                    String category = (String) categoryBox.getSelectedItem();
                    
                    // Add question to database
                    quizManager.addQuizQuestion(category, question, answer);
                    
                    // Show success message
                    JOptionPane.showMessageDialog(null, "Question added successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Show error message
                    JOptionPane.showMessageDialog(null, "Error adding question.");
                }
            }
        });
        add(addButton);
    }

    public static void main(String[] args) {
        new AdminPanelGUI().setVisible(true);
    }
}
