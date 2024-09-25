import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);

        JPanel linksPanel = new JPanel(new GridLayout(1, 2));
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(e -> createAccount());

        JButton forgotPasswordButton = new JButton("Forgot Password");
        forgotPasswordButton.addActionListener(e -> forgotPassword());

        linksPanel.add(createAccountButton);
        linksPanel.add(forgotPasswordButton);

        add(loginPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(linksPanel, BorderLayout.NORTH);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_app", "root", "Mypwd_3016");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new QuizGUI().setVisible(true);  // Navigate to Quiz GUI
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error while logging in.");
            e.printStackTrace();
        }
    }

    private void createAccount() {
        new RegisterGUI().setVisible(true);
        this.dispose();
    }

    private void forgotPassword() {
        new ForgotPasswordGUI().setVisible(true);
        this.dispose();
    }
}
