package cop4331.controller;

import cop4331.model.ProductComponent;
import cop4331.model.customer.Customer;
import cop4331.model.customer.Order;
import cop4331.model.customer.ShoppingCart;
import cop4331.view.customer.CustomerDashboardView;

import javax.swing.*;
import java.util.HashMap;

/**
 * <p>Handles the checkout process for a customer including generating an invoice,
 * processing payment, and saving an order.</p>
 * @author Benjamin Carver
 */
public class CheckoutController {
    private CustomerDashboardView dashboardView;

    /**
     * <p>Constructs a CheckoutController object.</p>
     *
     * @param dashboardView The customer dashboard view.
     */
    public CheckoutController(CustomerDashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }

    /**
     * <p>Takes a User's cart and produces an invoice with product details, quantities,
     * and the total cost.</p>
     * @param cart The User's cart.
     * @return The invoice in String form.
     */
    public String generateInvoice(ShoppingCart cart) {
        StringBuilder invoice = new StringBuilder();
        invoice.append("Order:\n");
        HashMap<ProductComponent, Integer> cartItems = cart.getCartItems();

        for (ProductComponent product : cartItems.keySet()) {
            double price = product.getSalePrice();
            int quantity = cartItems.get(product);
            invoice.append(product.getName()).append(" x").append(quantity)
                    .append(" = $").append(String.format("%.2f", price * quantity)).append("\n");

        }
        invoice.append("Total: $").append(String.format("%.2f", cart.calculateTotal()));
        return invoice.toString();
    }

    /**
     * <p>Simulates payment processing using provided information.</p>
     * @param cardNumber The card's number.
     * @param expiryDate The card's expiry date.
     * @param cvv The card's security code.
     * @return true, always simulates successful payment.
     */
    public boolean processPayment(String cardNumber, String expiryDate, String cvv) {
        System.out.println("Processing payment...");
        return true;
    }

    /**
     * <p>Saves the order to the User's account.</p>
     * @param customer The User who made the order.
     * @param cart The User's cart.
     * @param invoice The invoice generated for the order.
     */
    public void saveOrder(Customer customer, ShoppingCart cart, String invoice) {
        Order order = new Order(customer, cart.getCartItems(), cart.calculateTotal(), invoice);
        customer.addOrder(order);
    }

    /**
     * <p>Performs the checkout process for the customer.</p>
     *
     * @param customer   The customer performing the checkout.
     * @param cart       The customer's shopping cart.
     * @param cardNumber The card number for payment.
     * @param expiryDate The expiry date of the card.
     * @param cvv        The CVV security code of the card.
     */
    public void checkout(Customer customer, ShoppingCart cart,
                         String cardNumber, String expiryDate, String cvv) {
        String invoice = generateInvoice(cart);
        boolean paymentSuccessful = processPayment(cardNumber, expiryDate, cvv);
        System.out.println(invoice);

        if (paymentSuccessful) {
            saveOrder(customer, cart, invoice);

            cart.getCartItems().clear();
        } else {
            JOptionPane.showMessageDialog(null, "Payment failed");
        }
    }
}
