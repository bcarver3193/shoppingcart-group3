package cop4331.view.login;

import cop4331.controller.LoginController;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Represents the loginView screen where users log into the system.</p>
 * @author Benjamin Carver
 */
public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    private JLabel errorLabel;

    private LoginController loginController;

    /**
     * <p>Creates a new LoginView object.</p>
     */
    public LoginView() {
        this.loginController = new LoginController();
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Username:", JLabel.CENTER));
        usernameField = new JTextField(16);
        panel.add(usernameField);

        panel.add(new JLabel("Password:", JLabel.CENTER));
        passwordField = new JPasswordField(16);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        panel.add(loginButton);

        createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(e -> showCreateAccountWindow());
        panel.add(createAccountButton);

        errorLabel = new JLabel("", JLabel.CENTER);
        errorLabel.setForeground(Color.RED);
        panel.add(errorLabel);

        add(panel);
        setVisible(true);
    }

    /**
     * <p>Starts the login process using the provided information.</p>
     */
    private void login() {
        try {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            loginController.login(username, password);
            JOptionPane.showMessageDialog(this, "Login Successful",
                    "Login Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
            return;
        }

        dispose();
    }

    /**
     * <p>Shows the createAccountWindow</p>
     */
    private void showCreateAccountWindow() {
        new CreateAccountView(loginController);
    }
}
