import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class RegisterGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public RegisterGUI() {
        setTitle("Create New Account");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel registerPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField();

        registerPanel.add(usernameLabel);
        registerPanel.add(usernameField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(confirmPasswordLabel);
        registerPanel.add(confirmPasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> register());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);

        add(registerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_app", "root", "Mypwd_3016");
             PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {

            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose another.");
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
                    insertStmt.setString(1, username);
                    insertStmt.setString(2, password);
                    insertStmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Account created successfully!");
                    new LoginGUI().setVisible(true);
                    this.dispose();
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error creating account.");
            e.printStackTrace();
        }
    }
}
