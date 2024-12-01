package cop4331.view.customer;

import cop4331.model.customer.Customer;
import cop4331.model.customer.Order;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * <p>Represents the GUI popup for the order history. Displays all previous orders the customer has made.</p>
 * @author Benjamin Carver
 */
public class OrderHistoryView extends JFrame {
    /**
     * <p>Creates a new OrderHistoryView object.</p>
     * @param customer The customer to view.
     */
    public OrderHistoryView(Customer customer) {
        setTitle(customer.getUsername() + "'s Order History");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        ArrayList<Order> orders = customer.getOrders();

        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No orders found");
            return;
        }

        JPanel mainPanel = new JPanel(new BorderLayout());

        JTextArea orderTextArea = new JTextArea(10, 30);
        orderTextArea.setEditable(false);

        for (Order order : orders) {
            orderTextArea.append(order.getInvoice() + "\n\n");
        }

        JScrollPane orderScrollPane = new JScrollPane(orderTextArea);
        mainPanel.add(orderScrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }
}
