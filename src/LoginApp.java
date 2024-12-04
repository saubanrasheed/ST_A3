import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class LoginApp extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/softwaretesting";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1432187s";
    public LoginApp() {
        setTitle("Login Screen");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginAction());
        panel.add(loginButton);
        add(panel);
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
// Fixed: Now passing both email and password to authenticateUser method
            String userName = authenticateUser(email, password); // Pass both email and password
            if (userName != null) {
                JOptionPane.showMessageDialog(null, "Welcome, " + userName + "!", "Login Successful",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid email or password.", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public String authenticateUser(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            System.err.println("Error: Email cannot be null or empty.");
            return null; // Fixed: Early return for invalid email input
        }
        if (password == null || password.trim().isEmpty()) {
            System.err.println("Error: Password cannot be null or empty.");
            return null; // Fixed: Early return for invalid password input
        }
        String userName = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
// Fixed: Added a parameterized query to include password validation
            String query = "SELECT name FROM User WHERE Email = ? AND Password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                stmt.setString(2, password); // Use parameterized query to prevent SQL injection

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        userName = rs.getString("Name"); }
                }
            }
        } catch (Exception e) {
// Fixed: Added specific error handling
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
        return userName; // Fixed: Returning userName even if null
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginApp loginApp = new LoginApp();
            loginApp.setVisible(true);
        });
    }
}