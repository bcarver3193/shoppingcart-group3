package cop4331.model;

import java.io.Serializable;

/**
 * <p>Stores the current user.</p>
 * @author Benjamin Carver
 */
public class Session implements Serializable {
    private static Session instance;
    private User currentUser;

    private Session() {
        currentUser = null;
    }

    /**
     * <p>Gets the current Session instance or creates one if it does not exist.</p>
     * @return The Session object.
     */
    public static synchronized Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    /**
     * <p>Gets the current user.</p>
     * @return The current user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * <p>Sets the current user.</p>
     * @param user The new current user.
     */
    public void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * <p>Invalidates the current session.</p>
     */
    public void invalidate() {
        currentUser = null;
    }
}
