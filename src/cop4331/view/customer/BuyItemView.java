package cop4331.view.customer;

import cop4331.controller.ShoppingCartController;
import cop4331.model.ProductComponent;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;

/**
 * <p>GUI class for the view presented to customers when they wish to add items to the cart.
 * Allows the customer to specify the quantity of the item they want or cancel the action.</p>
 * @author Benjamin Carver
 */
public class BuyItemView extends JFrame {
    private CustomerDashboardView dashboardView;
    private ProductComponent productComponent;
    private JFormattedTextField quantityField;

    /**
     * <p>Constructs a BuyItemView object with the provided information.</p>
     * @param dashboardView The customer dashboard.
     * @param productComponent The product being added to the cart.
     */
    public BuyItemView(CustomerDashboardView dashboardView, ProductComponent productComponent) {
        this.dashboardView = dashboardView;
        this.productComponent = productComponent;
        setTitle("Quantity to Purchase");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2));

        panel.add(new JLabel("Quantity:", JLabel.CENTER));

        // Create a NumberFormatter for integers
        NumberFormatter intFormatter = new NumberFormatter();
        intFormatter.setValueClass(Integer.class);
        intFormatter.setMinimum(0);
        intFormatter.setAllowsInvalid(false);

        quantityField = new JFormattedTextField(intFormatter);
        panel.add(quantityField);
        quantityField.setValue(1);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> confirmQuantity());
        panel.add(confirmButton);

        add(panel);
        setVisible(true);
    }

    /**
     * <p>Adds the product to the cart.</p>
     */
    private void confirmQuantity() {
        ShoppingCartController shoppingCartController = dashboardView.getShoppingCartController();
        shoppingCartController.addProductToCart(productComponent,
                Integer.parseInt(quantityField.getValue().toString()));
        dashboardView.refreshView();
        dispose();
    }
}


