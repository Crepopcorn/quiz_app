import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class ForgotPasswordGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    public ForgotPasswordGUI() {
        setTitle("Forgot Password");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel resetPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordField = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField();

        resetPanel.add(usernameLabel);
        resetPanel.add(usernameField);
        resetPanel.add(newPasswordLabel);
        resetPanel.add(newPasswordField);
        resetPanel.add(confirmPasswordLabel);
        resetPanel.add(confirmPasswordField);

        JButton resetButton = new JButton("Reset Password");
        resetButton.addActionListener(e -> resetPassword());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resetButton);

        add(resetPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void resetPassword() {
        String username = usernameField.getText();
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_app", "root", "Mypwd_3016");
             PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET password = ? WHERE username = ?")) {

            updateStmt.setString(1, newPassword);
            updateStmt.setString(2, username);

            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Password reset successfully!");
                new LoginGUI().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username not found.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error resetting password.");
            e.printStackTrace();
        }
    }
}
