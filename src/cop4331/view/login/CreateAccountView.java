package cop4331.view.login;

import cop4331.controller.LoginController;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Represents the window taking in information for account creation.</p>
 * @author Benjamin Carver
 */
public class CreateAccountView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> userTypeComboBox;
    private JButton createAccountButton;
    private JButton cancelButton;
    private JLabel errorLabel;

    private LoginController loginController;

    /**
     * <p>Creates the CreateAccountView object.</p>
     * @param loginController The {@code LoginController}.
     */
    public CreateAccountView(LoginController loginController) {
        this.loginController = loginController;
        setTitle("Create Account");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        panel.add(new JLabel("Username:", JLabel.CENTER));
        usernameField = new JTextField(16);
        panel.add(usernameField);

        panel.add(new JLabel("Password:", JLabel.CENTER));
        passwordField = new JPasswordField(16);
        panel.add(passwordField);
        panel.add(new JLabel("Confirm Password:", JLabel.CENTER));
        confirmPasswordField = new JPasswordField(16);
        panel.add(confirmPasswordField);

        panel.add(new JLabel("User Type:", JLabel.CENTER));
        userTypeComboBox = new JComboBox<>(new String[]{"Customer", "Seller"});
        panel.add(userTypeComboBox);

        createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(e -> createAccount());
        panel.add(createAccountButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);

        errorLabel = new JLabel("", JLabel.CENTER);
        errorLabel.setForeground(Color.RED);
        panel.add(errorLabel);

        add(panel);
        setVisible(true);
    }

    /**
     * <p>Takes data from the fields and creates a new account.</p>
     */
    private void createAccount() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
        String userType = userTypeComboBox.getSelectedItem().toString();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || userType.isEmpty()) {
            errorLabel.setText("Please fill all the fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match");
            return;
        }

        try {
            if (userType.equals("Customer")) {
                loginController.createCustomerAccount(username, password);
            } else {
                loginController.createSellerAccount(username, password);
            }

            JOptionPane.showMessageDialog(this, "Account created successfully");
            dispose();
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }
}
