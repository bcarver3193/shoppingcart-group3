package cop4331.model.customer;

import cop4331.model.ProductComponent;

import java.io.Serializable;
import java.util.HashMap;

/**
 * <p>Represents a {@code Customer}'s order. Contains information like the products purchased,
 * the total price, and an invoice string for printing.</p>
 * @author Benjamin Carver
 */
public class Order {
    private Customer customer;
    private HashMap<ProductComponent, Integer> items;
    private double total;
    private String invoice;

    /**
     * <p>Constructs a new Order object.</p>
     * @param customer The {@code Customer} the order belongs to.
     * @param items A {@code HashMap} containing the products bought and their quantities.
     * @param total The total price of the order.
     * @param invoice A string representing the invoice.
     */
    public Order(Customer customer, HashMap<ProductComponent, Integer> items, double total, String invoice) {
        this.customer = customer;
        this.items = items;
        this.total = total;
        this.invoice = invoice;
    }

    /**
     * <p>Gets the {@code Customer} the order belongs to.</p>
     * @return The {@code Customer} the order belongs to.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * <p>Gets the {@code HashMap} of all items in the order.</p>
     * @return The {@code HashMap} of all items in the order.
     */
    public HashMap<ProductComponent, Integer> getItems() {
        return items;
    }

    /**
     * <p>Gets the total price of the order.</p>
     * @return The total price of the order.
     */
    public double getTotal() {
        return total;
    }

    /**
     * <p>Gets the invoice string from the order.</p>
     * @return The invoice string of the order.
     */
    public String getInvoice() {
        return invoice;
    }

    /**
     * <p>Provides the invoice string as the representation of the order.</p>
     * @return The invoice string.
     */
    @Override
    public String toString() {
        return invoice;
    }
}
