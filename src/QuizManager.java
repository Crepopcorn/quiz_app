import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizManager {

    // Get quizzes by category
    public List<Quiz> getQuizzesByCategory(String category) throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT question, answer FROM quizzes WHERE category = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, category);
        ResultSet rs = statement.executeQuery();

        // Loop through result set
        while (rs.next()) {
            String question = rs.getString("question");
            String answer = rs.getString("answer");
            quizzes.add(new Quiz(question, answer));
        }

        // Close connection
        conn.close();
        return quizzes;
    }

    // Add quiz question to DB
    public void addQuizQuestion(String category, String question, String answer) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO quizzes (category, question, answer) VALUES (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(query);
        
        // Set category, question, answer
        statement.setString(1, category);
        statement.setString(2, question);
        statement.setString(3, answer);
        
        // Execute query
        statement.executeUpdate();
        
        // Close connection
        conn.close();
    }
}
