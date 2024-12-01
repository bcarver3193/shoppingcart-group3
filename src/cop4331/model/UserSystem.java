package cop4331.model;

import cop4331.model.customer.Customer;
import cop4331.model.seller.Seller;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>The UserSystem stores all users registered. It facilitates the creation,
 * removal, and retrieval of users.</p>
 * @author Benjamin Carver
 */
public class UserSystem {
    private static UserSystem instance;
    private HashMap<String, User> users;
    private ArrayList<Customer> customers;
    private ArrayList<Seller> sellers;

    /**
     * <p>Private constructor for the UserSystem.</p>
     */
    private UserSystem() {
        users = new HashMap<>();
        customers = new ArrayList<>();
        sellers = new ArrayList<>();
    }

    /**
     * <p>Gets the current UserSystem instance or creates it if it doesn't exist.</p>
     * @return The current UserSystem instance.
     */
    public static synchronized UserSystem getInstance() {
        if (instance == null) {
            instance = new UserSystem();
        }
        return instance;
    }

    /**
     * <p>Sets the UserSystem instance for use in serialization.</p>
     * @param userSystem The instance to be loaded.
     */
    public static void setInstance(UserSystem userSystem) {
        instance = userSystem;
    }

    /**
     * <p>Gets the {@code HashMap} containing all registered users.</p>
     * @return A {@code HashMap} with all registered users.
     */
    public HashMap<String, User> getUsers() {
        return users;
    }

    /**
     * <p>Gets the {@code ArrayList} containing all registered customers.</p>
     * @return An {@code ArrayList} containing all registered customers.
     */
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    /**
     * <p>Gets the {@code ArrayList} containing all registered customers.</p>
     * @return An {@code ArrayList} containing all registered customers.
     */
    public ArrayList<Seller> getSellers() {
        return sellers;
    }

    /**
     * <p>Registers a new customer in the system.</p>
     * @param username The new user's username.
     * @param password The new user's password.
     * @throws IllegalArgumentException If the username is already in use.
     */
    public void registerCustomer(String username, String password) {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Username is already in use");
        }
        Customer customer = new Customer(username, password);
        users.put(username, customer);
        customers.add(customer);
    }

    /**
     * <p>Registers a new seller in the system.</p>
     * @param username The new user's username.
     * @param password The new user's password.
     * @throws IllegalArgumentException If the username is already in use.
     */
    public void registerSeller(String username, String password) {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Username is already in use");
        }
        Seller seller = new Seller(username, password);
        users.put(username, seller);
        sellers.add(seller);
    }

    /**
     * <p>Removes the user from the system.</p>
     * @param username The username of the user to be removed.
     */
    public void removeUser(String username) {
        User user = users.remove(username);
        if (user != null) {
            if (user instanceof Customer) {
                customers.remove((Customer) user);
            } else if (user instanceof Seller) {
                sellers.remove((Seller) user);
            }
        }
    }

    /**
     * <p>Handles the user validation process.</p>
     * @param username The username to check.
     * @param password The password for the user.
     * @return The user if verification was successful, null otherwise.
     */
    public User verifyUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.validatePassword(password)) {
            return user;
        }

        return null;
    }
}
