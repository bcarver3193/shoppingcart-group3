package cop4331.controller;

import cop4331.model.Session;
import cop4331.model.User;
import cop4331.model.UserSystem;
import cop4331.model.customer.Customer;
import cop4331.model.seller.Seller;
import cop4331.view.customer.CustomerDashboardView;
import cop4331.view.seller.SellerDashboardView;

/**
 * <p>Handles the core logic for logging in, logging out, and creating new accounts.</p>
 * @author Benjamin Carver
 */
public class LoginController {
    private UserSystem userSystem;
    private Session session;

    /**
     * <p>Constructs a LoginController object.</p>
     */
    public LoginController() {
        this.userSystem = UserSystem.getInstance();
        this.session = Session.getInstance();
    }

    /**
     * <p>Handles login by passing username and password to UserSystem for verification
     * before redirecting back to the login page on a failed authentication or to the
     * correct dashboard on success. Sets the Session.currentUser to validated users.</p>
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     */
    public void login(String username, String password) {
        User user = userSystem.verifyUser(username, password);

        if (user == null) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        session.setCurrentUser(user);
        if (user instanceof Customer) {
            new CustomerDashboardView((Customer) user);
        } else if (user instanceof Seller) {
            new SellerDashboardView((Seller) user);
        }
    }

    /**
     * <p>Logs the current user out and clears the session.</p>
     */
    public void logout() {
        session.invalidate();
    }

    /**
     * <p>Creates a new Customer with the provided information.</p>
     * @param username The new account's username.
     * @param password The new account's password.
     */
    public void createCustomerAccount(String username, String password) {
        userSystem.registerCustomer(username, password);
    }

    /**
     * <p>Creates a new Seller with the provided information.</p>
     * @param username The new account's username.
     * @param password The new account's password.
     */
    public void createSellerAccount(String username, String password) {
        userSystem.registerSeller(username, password);
    }
}
