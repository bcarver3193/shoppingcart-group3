package model;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Stores and manages the users for the application.
 * Handles user validation and creation.</p>
 */
public class UserSystem {
    private static UserSystem instance;
    private Map<String, User> userDatabase;

    /**
     * <p>Private constructor to instantiate a new UserSystem.</p>
     */
    private UserSystem() {
        userDatabase = new HashMap<>();
    }

    /**
     * <p>Gets the current UserSystem instance or creates one if it does not exist.</p>
     *
     * @return the UserSystem
     */
    public static synchronized UserSystem getInstance() {
        if (instance == null) {
            instance = new UserSystem();
        }
        return instance;
    }

    /**
     * <p>Handles the user validation process.</p>
     *
     * @param inputUsername the username to validate.
     * @param inputPassword the password to validate.
     * @return the user or null if failed validation.
     */
    public User validateUser(String inputUsername, String inputPassword) {
        User user = userDatabase.get(inputUsername);
        return (user != null && user.validatePassword(inputPassword)) ? user : null;
    }

    /**
     * <p>Adds users to the UserDatabase.</p>
     *
     * @param username the new user's username.
     * @param password the new user's password.
     * @param role the new user's role.
     * @return true if the user was successfully created, false otherwise.
     */
    public boolean addUser(String username, String password, String role) {
        if (userDatabase.containsKey(username)) {
            return false;
        }

        if ("Customer".equalsIgnoreCase(role)) {
            userDatabase.put(username, new Customer(username, password));
        } else if ("Seller".equalsIgnoreCase(role)) {
            userDatabase.put(username, new Seller(username, password));
        } else {
            return false;
        }

        return true;
    }
}
