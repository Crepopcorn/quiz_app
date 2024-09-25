import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Start with the login page
        SwingUtilities.invokeLater(() -> {
            new LoginGUI().setVisible(true);
        });
    }
}
