package cop4331.view.seller;

import cop4331.controller.InventoryController;
import cop4331.controller.LoginController;
import cop4331.model.DiscountDecorator;
import cop4331.model.Inventory;
import cop4331.model.Observer;
import cop4331.model.ProductComponent;
import cop4331.model.seller.Seller;
import cop4331.model.seller.SellerAddProductCommand;
import cop4331.model.seller.SellerBundleItemsCommand;
import cop4331.model.seller.SellerLogoutCommand;
import cop4331.view.login.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * <p>Represents the main GUI for the Seller side of the application. Contains a Table with the
 * seller's inventory and functionality to access the financials and add/edit products.</p>
 * @author Benjamin Carver
 */
public class SellerDashboardView extends JFrame implements Observer {
    private Seller seller;
    ArrayList<ProductComponent> sellerInventory = new ArrayList<>();
    private JTable inventoryTable;
    private InventoryController inventoryController = new InventoryController(this);

    /**
     * <p>Creates a new SellerDashboardView object.</p>
     * @param seller The current seller.
     */
    public SellerDashboardView(Seller seller) {
        this.seller = seller;
        Inventory inventory = Inventory.getInstance();
        inventory.registerObserver(this);

        setTitle(seller.getUsername() + "'s Dashboard (Seller)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JToolBar toolbar = new JToolBar();

        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(e -> new SellerAddProductCommand(this).execute());
        toolbar.add(addButton);

        JButton bundleButton = new JButton("Bundle");
        bundleButton.addActionListener(e -> new SellerBundleItemsCommand(this).execute());
        toolbar.add(bundleButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> new SellerLogoutCommand(this).execute());
        toolbar.add(logoutButton);
        
        JPanel topBar = getTopBar(seller);

        // Create table model
        updateSellerInventory();

        String[] columnNames = {"Name", "Type", "Quantity", "Invoice Price", "Sale Price"};
        Object[][] data = new Object[sellerInventory.size()][columnNames.length];
        for (ProductComponent product : sellerInventory) {
            data[sellerInventory.indexOf(product)][0] = product.getName();
            data[sellerInventory.indexOf(product)][1] = product.getType();
            if (product.getStockQuantity() > 0) {
                data[sellerInventory.indexOf(product)][2] = product.getStockQuantity();
            } else {
                data[sellerInventory.indexOf(product)][2] = "Out of Stock";
            }
            data[sellerInventory.indexOf(product)][3] = "$" + String.format("%.2f", product.getInvoicePrice());
            data[sellerInventory.indexOf(product)][4] = "$" + String.format("%.2f", product.getSalePrice());
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

        JPanel bottomBar = getBottomBar();

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(toolbar, BorderLayout.NORTH);
        northPanel.add(topBar, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomBar, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * <p>Listener for updates to the {@code Inventory} and triggers the refreshView() method.</p>
     */
    @Override
    public void update() {
        refreshView();
    }

    /**
     * <p>Creates the topBar component of the dashboard.</p>
     * @param seller The current seller.
     * @return the topBar {@code JPanel}
     */
    private JPanel getTopBar(Seller seller) {
        JPanel topBar = new JPanel();
        topBar.setLayout(new GridLayout(1, 3));

        JLabel welcomeLabel = new JLabel("Welcome, " + seller.getUsername() + "!", JLabel.CENTER);
        topBar.add(welcomeLabel);

        JButton financialsButton = new JButton("Financials");
        financialsButton.addActionListener(e -> showFinancialDetails());
        topBar.add(financialsButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        topBar.add(logoutButton);
        return topBar;
    }

    /**
     * <p>Creates the bottomBar component of the dashboard.</p>
     * @return the bottomBar {@code JPanel}
     */
    private JPanel getBottomBar() {
        JPanel bottomBar = new JPanel();
        bottomBar.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(e -> addProduct());
        bottomBar.add(addButton);

        JButton bundleButton = new JButton("Bundle Items");
        bundleButton.addActionListener(e -> bundleItems());
        bottomBar.add(bundleButton);

        JButton removeButton = new JButton("Remove Selected Items");
        removeButton.addActionListener(e -> removeItems());
        bottomBar.add(removeButton);
        return bottomBar;
    }

    /**
     * <p>Displays the seller financials window.</p>
     */
    public void showFinancialDetails() {
        new SellerFinancialsView();
    }

    /**
     * <p>Logs the seller out and returns to the login screen.</p>
     */
    public void logout() {
        LoginController loginController = new LoginController();
        loginController.logout();
        new LoginView();
        JOptionPane.showMessageDialog(this, "Logout successful");
        dispose();
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

        JMenuItem viewItem = new JMenuItem("View Details");
        viewItem.addActionListener(e -> showViewDetails(row));
        popupMenu.add(viewItem);

        JMenuItem editItem = new JMenuItem("Edit Details");
        editItem.addActionListener(e -> showEditDetails(row));
        popupMenu.add(editItem);

        JMenuItem discountItem = new JMenuItem("Discount Item");
        discountItem.addActionListener(e -> showDiscountOptions(row));
        popupMenu.add(discountItem);

        JMenuItem removeDiscount = new JMenuItem("Remove Discount");
        removeDiscount.addActionListener(e -> removeDiscount(row));
        popupMenu.add(removeDiscount);

        JMenuItem removeItem = new JMenuItem("Remove Item");
        removeItem.addActionListener(e -> removeItem(row));
        popupMenu.add(removeItem);

        popupMenu.show(component, x, y);
    }

    /**
     * <p>Updates the local seller inventory by scanning for products assigned to the seller.</p>
     */
    public void updateSellerInventory() {
        sellerInventory.clear();

        Iterator<ProductComponent> it = Inventory.getInstance().iterator();
        while (it.hasNext()) {
            ProductComponent product = it.next();
            if (product.getSeller() != null &&
                    product.getSeller().equals(seller.getUsername())) {
                sellerInventory.add(product);
            }
        }
    }

    /**
     * <p>Refreshes the seller dashboard to change with any updates.</p>
     */
    public void refreshView() {
        updateSellerInventory();
        
        String[] columnNames = {"Name", "Type", "Quantity", "Invoice Price", "Sale Price"};
        Object[][] data = new Object[sellerInventory.size()][columnNames.length];
        for (ProductComponent product : sellerInventory) {
            data[sellerInventory.indexOf(product)][0] = product.getName();
            data[sellerInventory.indexOf(product)][1] = product.getType();
            if (product.getStockQuantity() > 0) {
                data[sellerInventory.indexOf(product)][2] = product.getStockQuantity();
            } else {
                data[sellerInventory.indexOf(product)][2] = "Out of Stock";
            }
            data[sellerInventory.indexOf(product)][3] = "$" + String.format("%.2f", product.getInvoicePrice());
            data[sellerInventory.indexOf(product)][4] = "$" + String.format("%.2f", product.getSalePrice());
        }
        
        inventoryTable.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        revalidate();
        repaint();
    }

    /**
     * <p>Displays the view details window for a product.</p>
     * @param row The row the product is in.
     */
    private void showViewDetails(int row) {
        ProductComponent product = sellerInventory.get(row);
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
        output.append("\nInvoice Price: $").append(String.format("%.2f", product.getInvoicePrice()));

        JOptionPane.showMessageDialog(this, output.toString());
    }

    /**
     * <p>Gets the {@code InventoryController} for the dashboard.</p>
     * @return the {@code InventoryController} for the dashboard.
     */
    public InventoryController getInventoryController() {
        return inventoryController;
    }

    /**
     * <p>Gets the sellerInventory {@code ArrayList}.</p>
     * @return the sellerInventory {@code ArrayList}.
     */
    public ArrayList<ProductComponent> getSellerInventory() {
        return sellerInventory;
    }

    /**
     * <p>Creates the editDetails window for the selected product.</p>
     * @param row The row the product is in.
     */
    private void showEditDetails(int row) {
        ProductComponent product = sellerInventory.get(row);
        new EditItemView(this, product);
    }

    /**
     * <p>Creates the discountOptions window for the selected product.</p>
     * @param row The row the product is in.
     */
    private void showDiscountOptions(int row) {
        ProductComponent product = sellerInventory.get(row);
        new DiscountView(this, product);
    }

    /**
     * <p>Removes the discount from the item.</p>
     * @param row the row the item is in.
     */
    private void removeDiscount(int row) {
        ProductComponent product = sellerInventory.get(row);
        if (product instanceof DiscountDecorator) {
            ProductComponent newProduct = ((DiscountDecorator) product).getProduct();
            inventoryController.updateProduct(newProduct);
            refreshView();
            JOptionPane.showMessageDialog(this, "Discount removed.");
        } else {
            JOptionPane.showMessageDialog(this, "Product does not have a discount");
        }
    }

    /**
     * <p>Shows the addProduct window.</p>
     */
    public void addProduct() {
        new AddItemView(this);
    }

    /**
     * <p>Removes the item from the selected row.</p>
     * @param row The row to remove the item from.
     */
    private void removeItem(int row) {
        ProductComponent product = sellerInventory.get(row);
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this item?", "Delete Item",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            try {
                inventoryController.removeProduct(product);
                refreshView();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * <p>Shows the bundleItems window.</p>
     */
    public void bundleItems() {
        new BundleItemsView(this);
    }

    /**
     * <p>Shows the removeItems window.</p>
     */
    private void removeItems() {
        JOptionPane.showMessageDialog(this, "Remove item placeholder.");
    }
}
