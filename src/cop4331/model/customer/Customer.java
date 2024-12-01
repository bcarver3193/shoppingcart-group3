package cop4331.model.customer;

import cop4331.model.User;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>Represents a customer in the system. Extends the {@code User} class.
 * Holds information specific to customers such as their cart and previous orders.</p>
 * @author Jeremy Ladanowski
 * @author Benjamin Carver
 */
public class Customer extends User {
    private ShoppingCart cart;
    private ArrayList<Order> orders;

    /**
     * <p>Constructs a Customer object with the specified username and password.</p>
     * @param username The new Customer's username.
     * @param password The new Customer's password.
     */
    public Customer(String username, String password) {
        super(username, password);
        this.cart = new ShoppingCart();
        this.orders = new ArrayList<>();
    }

    /**
     * <p>Gets the Customer's cart.</p>
     * @return The Customer's cart.
     */
    public ShoppingCart getCart() {
        return cart;
    }

    /**
     * <p>Returns the role of the Customer for identification in the system.</p>
     * @return "Customer"
     */
    @Override
    public String getRole() {
        return "Customer";
    }

    /**
     * <p>Gets the Customer's previous orders.</p>
     * @return An {@code ArrayList} of the Customer's orders.
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * <p>Adds an order to the Customer's order history.</p>
     * @param order The order to add.
     */
    public void addOrder(Order order) {
        this.orders.add(order);
    }
}
