package cop4331.model;


import java.io.Serializable;

/**
 * <p>Abstract class for storing User information in the system.</p>
 * @author Jeremy Ladanowski
 * @author Benjamin Carver
 */
public abstract class User implements Serializable {
    private String username;
    private String password;

    /**
     * <p>Constructs a new User object.</p>
     * @param username The username of the new User.
     * @param password The password of the new User.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * <p>Gets the username of the user.</p>
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    public abstract String getRole();

    /**
     * <p>Validates the password for the current user.</p>
     * @param password The password to check.
     * @return true if password is valid, false otherwise.
     */
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }
}
