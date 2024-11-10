package controller;

import model.User;
import model.UserSystem;
import view.LoginView;

/**
 * <p>Handles passing of commands between the login system's model and view elements.</p>
 */
public class LoginController {
    private LoginView loginView;
    private UserSystem userSystem;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.userSystem = UserSystem.getInstance();
    }

    /**
     * <p>Passes user credentials to the UserSystem for validation.
     * Displays a message depending on success or failure.</p>
     *
     * @param username the input username.
     * @param password the input password.
     */
    public void handleLogin(String username, String password) {
        User user = userSystem.validateUser(username, password);

        if (user != null) {
            loginView.showSuccess("Login successful. Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
        } else {
            loginView.showError("Invalid username or password");
        }
    }

    /**
     * <p>Passes the create user command to the UserSystem.</p>
     * @param username the new user's username.
     * @param password the new user's password.
     * @param role the new user's role.
     * @return true if the user was created, false otherwise.
     */
    public boolean createUser(String username, String password, String role) {
        return userSystem.addUser(username, password, role);
    }
}
