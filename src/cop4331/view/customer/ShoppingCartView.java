package cop4331.view.customer;

import cop4331.controller.InventoryController;
import cop4331.controller.ShoppingCartController;
import cop4331.model.customer.Customer;
import cop4331.model.ProductComponent;
import cop4331.model.Session;
import cop4331.model.customer.ShoppingCart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * <p>View class for displaying the shopping cart and its contents.</p>
 * @author Benjamin Carver
 */
public class ShoppingCartView extends JFrame {
    private CustomerDashboardView dashboardView;
    private ShoppingCart shoppingCart;
    private JTable cartTable;
    private JLabel totalPriceLabel;

    /**
     * <p>Constructs the ShoppingCartView object.</p>
     * @param dashboardView The customer dashboard.
     */
    public ShoppingCartView(CustomerDashboardView dashboardView) {
        this.dashboardView = dashboardView;
        this.shoppingCart = ((Customer) Session.getInstance().getCurrentUser()).getCart();
        setTitle("Shopping Cart");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table model
        String[] columnNames = {"Name", "Quantity", "Price", "Total"};
        cartTable = new JTable(new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        cartTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = cartTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        cartTable.setRowSelectionInterval(row, row);
                        showActionPopupMenu(e.getComponent(), e.getX(), e.getY(), row);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(cartTable);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        totalPriceLabel = new JLabel("Total Price: $0.00");
        bottomPanel.add(totalPriceLabel);

        JButton clearCartButton = new JButton("Clear Cart");
        clearCartButton.addActionListener(e -> clearCart());
        bottomPanel.add(clearCartButton);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> startCheckout());
        bottomPanel.add(checkoutButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        updateCartDisplay();
    }

    /**
     * <p>Creates the right click menu.</p>
     * @param component
     * @param x
     * @param y
     * @param row
     */
    private void showActionPopupMenu(Component component, int x, int y, int row) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem removeFromCart = new JMenuItem("Remove from Cart");
        removeFromCart.addActionListener(e -> removeItemFromCart(row));
        popupMenu.add(removeFromCart);

        popupMenu.show(component, x, y);
    }

    /**
     * <p>Removes the item in the row from the cart.</p>
     * @param row The row the item is in.
     */
    private void removeItemFromCart(int row) {
        InventoryController inventoryController = new InventoryController();
        ProductComponent product = inventoryController.findProductByName(
                (String) cartTable.getValueAt(row, 0));

        ShoppingCartController shoppingCartController = new ShoppingCartController(dashboardView, shoppingCart);
        shoppingCartController.removeProductFromCart(product);

        updateCartDisplay();
        dashboardView.refreshView();
    }

    /**
     * <p>Clears the cart's contents.</p>
     */
    private void clearCart() {
        ShoppingCartController shoppingCartController = new ShoppingCartController(dashboardView, shoppingCart);
        shoppingCartController.clearCart();
        JOptionPane.showMessageDialog(null, "Cart has been cleared.");
        updateCartDisplay();
    }

    /**
     * <p>Updates the cart display.</p>
     */
    public void updateCartDisplay() {
        DefaultTableModel tableModel = (DefaultTableModel) cartTable.getModel();
        tableModel.setRowCount(0);

        double totalPrice = 0.0;

        for (Map.Entry<ProductComponent, Integer> entry : shoppingCart.getCartItems().entrySet()) {
            ProductComponent product = entry.getKey();
            int quantity = entry.getValue();
            double itemPrice = product.getSalePrice() * quantity;
            totalPrice += itemPrice;

            Object[] rowData = {
                    product.getName(),
                    quantity,
                    String.format("%.2f", product.getSalePrice()),
                    String.format("%.2f", itemPrice)
            };
            tableModel.addRow(rowData);
        }

        totalPriceLabel.setText("Total Price: $" + String.format("%.2f", totalPrice));
    }

    /**
     * <p>Starts the checkout process.</p>
     */
    private void startCheckout() {
        new CheckoutView(dashboardView.getCheckoutController());
        updateCartDisplay();
        dispose();
    }
}
