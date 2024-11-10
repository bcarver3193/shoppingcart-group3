package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CompositeTester {
    private static void createAndShowGUI() {
        // Create main components
        Seller seller = new Seller("seller1", "password123");
        Customer customer = new Customer("customer1", "password456");

        // Create sample products
        SingleProduct laptop = new SingleProduct("Laptop", 1000.0, 800.0, "A high-performance laptop.", 10);
        SingleProduct mouse = new SingleProduct("Mouse", 25.0, 15.0, "Wireless mouse.", 20);
        SingleProduct keyboard = new SingleProduct("Keyboard", 50.0, 30.0, "Mechanical keyboard.", 15);

        seller.addProduct(laptop);
        seller.addProduct(mouse);
        seller.addProduct(keyboard);

        // Create the main frame
        JFrame frame = new JFrame("Shopping Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create product panels
        List<SingleProduct> products = List.of(laptop, mouse, keyboard);
        List<JSpinner> spinners = new ArrayList<>();

        for (SingleProduct product : products) {
            JPanel productPanel = new JPanel();
            productPanel.setBorder(BorderFactory.createTitledBorder(product.getName()));
            productPanel.setLayout(new GridLayout(3, 2));

            productPanel.add(new JLabel("Price: $" + product.getPrice()));
            productPanel.add(new JLabel("Stock: " + product.getStockQuantity()));

            JLabel descLabel = new JLabel("Description: ");
            JLabel desc = new JLabel(product.getDescription());
            productPanel.add(descLabel);
            productPanel.add(desc);

            productPanel.add(new JLabel("Quantity: "));
            SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, product.getStockQuantity(), 1);
            JSpinner spinner = new JSpinner(spinnerModel);
            spinners.add(spinner);
            productPanel.add(spinner);

            mainPanel.add(productPanel);
        }

        // Create checkout button and total label
        JLabel totalLabel = new JLabel("Total: $0.00");
        JButton checkoutButton = new JButton("Checkout");

        checkoutButton.addActionListener(e -> {
            double total = 0;
            StringBuilder orderSummary = new StringBuilder("Order Summary:\n\n");

            for (int i = 0; i < products.size(); i++) {
                int quantity = (Integer) spinners.get(i).getValue();
                if (quantity > 0) {
                    SingleProduct product = products.get(i);
                    double itemTotal = quantity * product.getPrice();
                    total += itemTotal;

                    orderSummary.append(product.getName())
                            .append(" x").append(quantity)
                            .append(" = $").append(String.format("%.2f", itemTotal))
                            .append("\n");

                    // Add to cart and update inventory
                    customer.getCart().addItem(product, quantity);
                    product.updateStock(-quantity);
                    seller.updateRevenue(
                            product.getPrice() * quantity,
                            product.getInvoicePrice() * quantity
                    );
                }
            }

            orderSummary.append("\nTotal: $").append(String.format("%.2f", total));

            if (total > 0) {
                JOptionPane.showMessageDialog(frame,
                        orderSummary.toString(),
                        "Checkout Complete",
                        JOptionPane.INFORMATION_MESSAGE);

                // Reset spinners after checkout
                spinners.forEach(spinner -> spinner.setValue(0));
                totalLabel.setText("Total: $0.00");

                // Show seller's profit
                JOptionPane.showMessageDialog(frame,
                        "Seller's Profit: $" + String.format("%.2f", seller.getTotalProfit()),
                        "Seller Information",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Please select at least one item",
                        "No Items Selected",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add listener to update total when quantities change
        for (int i = 0; i < spinners.size(); i++) {
            final int index = i;
            spinners.get(i).addChangeListener(e -> {
                double total = 0;
                for (int j = 0; j < products.size(); j++) {
                    total += (Integer) spinners.get(j).getValue() * products.get(j).getPrice();
                }
                totalLabel.setText("Total: $" + String.format("%.2f", total));
            });
        }

        // Add total and checkout button to main panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(totalLabel);
        bottomPanel.add(checkoutButton);
        mainPanel.add(bottomPanel);

        // Add scroll pane in case window is too small
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        frame.add(scrollPane);

        // Display the window
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
