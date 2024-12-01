package cop4331.view.customer;

import cop4331.controller.CheckoutController;
import cop4331.controller.InventoryController;
import cop4331.controller.LoginController;
import cop4331.controller.ShoppingCartController;
import cop4331.model.Inventory;
import cop4331.model.Observer;
import cop4331.model.ProductComponent;
import cop4331.model.customer.*;
import cop4331.view.login.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * <p>Represents the main GUI on the customer-side of the application. Holds a table
 * containing all products the customer may buy as well as access to order history, the shopping cart,
 * and the checkout windows.</p>
 * @author Benjamin Carver
 */
public class CustomerDashboardView extends JFrame implements Observer {
    private Customer customer;
    private JTable inventoryTable;
    private Inventory inventory;
    private ShoppingCart shoppingCart;
    private ShoppingCartController shoppingCartController;
    private JLabel cartStatus;
    private CheckoutController checkoutController = new CheckoutController(this);
    private InventoryController inventoryController = new InventoryController(this);
    private ArrayList<ProductComponent> currentInventory = new ArrayList<>();

    /**
     * <p>Constructs a new CustomerDashboardView object.</p>
     * @param customer The current customer.
     */
    public CustomerDashboardView(Customer customer) {
        this.customer = customer;
        this.inventory = Inventory.getInstance();
        inventory.registerObserver(this);
        this.shoppingCart = customer.getCart();
        this.shoppingCartController = new ShoppingCartController(this, shoppingCart);

        setTitle(customer.getUsername() + "'s Dashboard (Customer)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create top bar
        JPanel topBar = getTopBar(customer);

        // Create table model
        updateCurrentInventory();

        String[] columnNames = {"Name", "Type", "Stock Quantity", "Price"};
        Object[][] data = new Object[currentInventory.size()][columnNames.length];
        for (ProductComponent product : currentInventory) {
            data[currentInventory.indexOf(product)][0] = product.getName();
            data[currentInventory.indexOf(product)][1] = product.getType();
            if (product.getStockQuantity() > 0) {
                data[currentInventory.indexOf(product)][2] = "In Stock";
            } else {
                data[currentInventory.indexOf(product)][2] = "Out of Stock";
            }
            data[currentInventory.indexOf(product)][3] = "$" + String.format("%.2f", product.getSalePrice());
        }

        inventoryTable = new JTable(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        JScrollPane scrollPane = new JScrollPane(inventoryTable);

        inventoryTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = inventoryTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        inventoryTable.setRowSelectionInterval(row, row);
                        showActionPopupMenu(e.getComponent(), e.getX(), e.getY(), row);
                    }
                }
            }
        });

        cartStatus = new JLabel("Cart: 0 items", JLabel.CENTER);
        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.addActionListener(e -> new ShoppingCartView(this));

        JPanel cartPanel = new JPanel(new GridLayout(1, 2));
        cartPanel.add(cartStatus);
        cartPanel.add(viewCartButton);


        add(topBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(cartPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * <p>Listener for inventory updates. Triggers a refresh of the view.</p>
     */
    @Override
    public void update() {
        refreshView();
    }

    /**
     * <p>Updates the local inventory held by the dashboard.</p>
     */
    private void updateCurrentInventory() {
        currentInventory.clear();

        Iterator<ProductComponent> it = inventory.iterator();
        while (it.hasNext()) {
            currentInventory.add(it.next());
        }
    }

    /**
     * <p>Constructs the "topBar" section of the GUI.</p>
     * @param customer The current customer.
     * @return the topBar {@code JPanel}.
     */
    private JPanel getTopBar(Customer customer) {
        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + customer.getUsername());
        topBar.add(welcomeLabel);

        JButton orderHistoryButton = new JButton("View Order History");
        orderHistoryButton.addActionListener(e -> showOrderHistory());
        topBar.add(orderHistoryButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        topBar.add(logoutButton);
        return topBar;
    }

    /**
     * <p>Gets the dashboard's checkoutController.</p>
     * @return the dashboard's checkoutController.
     */
    public CheckoutController getCheckoutController() {
        return checkoutController;
    }

    /**
     * <p>Gets the dashboard's inventoryController.</p>
     * @return the dashboard's inventoryController.
     */
    public InventoryController getInventoryController() {
        return inventoryController;
    }

    /**
     * <p>Gets the dashboard's shoppingCartController.</p>
     * @return the dashboard's shoppingCartController.
     */
    public ShoppingCartController getShoppingCartController() {
        return shoppingCartController;
    }

    /**
     * <p>Shows the order history window.</p>
     */
    private void showOrderHistory() {
        new OrderHistoryView(customer);
    }

    /**
     * <p>Logs the customer out and returns to the login page.</p>
     */
    public void logout() {
        LoginController loginController = new LoginController();
        loginController.logout();
        new LoginView();
        JOptionPane.showMessageDialog(this, "Logout successful");
        dispose();
    }

    /**
     * <p>Creates the right click menu and handles its functionality.</p>
     * @param component
     * @param x
     * @param y
     * @param row
     */
    private void showActionPopupMenu(Component component, int x, int y, int row) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem viewDetails = new JMenuItem("View Details");
        viewDetails.addActionListener(e -> showViewDetails(row));
        popupMenu.add(viewDetails);

        JMenuItem addToCart = new JMenuItem("Add to Cart");
        addToCart.addActionListener(e -> addItemToCart(row));
        popupMenu.add(addToCart);

        popupMenu.show(component, x, y);
    }

    /**
     * <p>Displays product details for the product.</p>
     * @param row The row the product exists on.
     */
    private void showViewDetails(int row) {
        ProductComponent product = inventoryController.findProductByName(
                (String) inventoryTable.getValueAt(row, 0));
        StringBuilder output = new StringBuilder();
        output.append("Product Details:\n");
        output.append("Name: ").append(product.getName()).append("\nType: ").append(product.getType())
                .append("\nDescription: ").append(product.getDescription());
        if (product.getStockQuantity() > 0) {
            output.append("\nStock Quantity: ").append(product.getStockQuantity());
        } else {
            output.append("\nStock Quantity: Out of Stock");
        }
        output.append("\nSale Price: $").append(String.format("%.2f", product.getSalePrice()));

        JOptionPane.showMessageDialog(this, output.toString());
    }

    /**
     * <p>Adds the product in the row to the cart.</p>
     * @param row The row the product is in.
     */
    private void addItemToCart(int row) {
        ProductComponent product = inventoryController.findProductByName(
                (String) inventoryTable.getValueAt(row, 0));

        new BuyItemView(this, product);
        refreshView();
    }

    /**
     * <p>Refreshes the table to update any changes that occurred.</p>
     */
    public void refreshView() {
        updateCurrentInventory();

        String[] columnNames = {"Name", "Type", "Stock Quantity", "Price"};
        Object[][] data = new Object[currentInventory.size()][columnNames.length];
        for (ProductComponent product : currentInventory) {
            data[currentInventory.indexOf(product)][0] = product.getName();
            data[currentInventory.indexOf(product)][1] = product.getType();
            if (product.getStockQuantity() > 0) {
                data[currentInventory.indexOf(product)][2] = product.getStockQuantity();
            } else {
                data[currentInventory.indexOf(product)][2] = "Out of Stock";
            }
            data[currentInventory.indexOf(product)][3] = "$" + String.format("%.2f", product.getSalePrice());
        }

        inventoryTable = new JTable(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        cartStatus.setText("Cart: " + shoppingCart.getTotalQuantity() + " items");

        revalidate();
        repaint();
    }
}
