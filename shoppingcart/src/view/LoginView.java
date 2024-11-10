package view;

import controller.LoginController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>The GUI for interacting with the login system.</p>
 */
public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    private JLabel feedbackLabel;
    private LoginController loginController;

    public LoginView() {
        this.loginController = new LoginController(this);

        // Set up frame
        setTitle("Login");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        // Create UI components
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        createAccountButton = new JButton("Create Account");
        feedbackLabel = new JLabel("", SwingConstants.CENTER);

        // Add components to frame
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(createAccountButton);
        add(feedbackLabel);

        // Add action listeners to buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLoginButtonClick();
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCreateAccountWindow();
            }
        });

        setVisible(true);
    }

    private void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        loginController.handleLogin(username, password);
    }

    private void openCreateAccountWindow() {
        // Set up frame
        JFrame createAccountFrame = new JFrame("Create Account");
        createAccountFrame.setSize(350, 250);
        createAccountFrame.setLayout(new GridLayout(5, 2));

        // Create UI components
        JTextField newUsernameField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();
        JComboBox<String> roleSelector = new JComboBox<>(new String[]{"Customer", "Seller"});
        JButton createAccountButton = new JButton("Create Account");

        // Add components to frame
        createAccountFrame.add(new JLabel("Username:"));
        createAccountFrame.add(newUsernameField);
        createAccountFrame.add(new JLabel("Password:"));
        createAccountFrame.add(newPasswordField);
        createAccountFrame.add(new JLabel("Role:"));
        createAccountFrame.add(roleSelector);
        createAccountFrame.add(createAccountButton);

        // Add action listeners
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = newUsernameField.getText();
                String newPassword = new String(newPasswordField.getPassword());
                String role = (String) roleSelector.getSelectedItem();

                if (loginController.createUser(newUsername, newPassword, role)) {
                    showSuccess("User created successfully");
                    createAccountFrame.dispose();
                } else {
                    showError("User creation failed");
                }
            }
        });

        createAccountFrame.setVisible(true);
    }

    /**
     * <p>Displays the given String as a success message.</p>
     *
     * @param message the message to be displayed.
     */
    public void showSuccess(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.GREEN);
    }

    /**
     * <p>Displays the given String as an error message.</p>
     *
     * @param message the message to be displayed.
     */
    public void showError(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.RED);
    }
}
