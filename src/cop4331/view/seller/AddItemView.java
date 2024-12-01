package cop4331.view.seller;

import cop4331.controller.InventoryController;
import cop4331.model.Product;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * <p>Represents the seller GUI for adding new products</p>
 * @author Benjamin Carver
 */
public class AddItemView extends JFrame {
    private InventoryController inventoryController;
    private JTextField productNameField;
    private JTextField productTypeField;
    private JTextArea productDescriptionField;
    private JFormattedTextField productSalePriceField;
    private JFormattedTextField productInvoicePriceField;
    private JFormattedTextField productStockQuantityField;
    private JButton cancelButton;
    private JButton createProductButton;
    private JLabel errorLabel;

    /**
     * <p>Creates the AddItemView object.</p>
     * @param dashboardView The seller dashboard.
     */
    public AddItemView(SellerDashboardView dashboardView) {
        this.inventoryController = dashboardView.getInventoryController();

        setTitle("Add New Product");
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10));

        panel.add(new JLabel("Product Name: ", JLabel.RIGHT));
        productNameField = new JTextField(10);
        panel.add(productNameField);

        panel.add(new JLabel("Product Type: ", JLabel.RIGHT));
        productTypeField = new JTextField(10);
        panel.add(productTypeField);

        panel.add(new JLabel("Product Description: ", JLabel.RIGHT));
        productDescriptionField = new JTextArea(5, 10);
        panel.add(productDescriptionField);

        // Specify the price format
        DecimalFormat priceFormat = new DecimalFormat("#.##");
        priceFormat.setMinimumFractionDigits(2);
        priceFormat.setMaximumFractionDigits(2);

        // Create a NumberFormatter for doubles
        NumberFormatter priceFormatter = new NumberFormatter(priceFormat);
        priceFormatter.setValueClass(Double.class);
        priceFormatter.setAllowsInvalid(false);

        panel.add(new JLabel("Product Sale Price: $", JLabel.RIGHT));
        productSalePriceField = new JFormattedTextField(priceFormatter);
        productSalePriceField.setColumns(10);
        panel.add(productSalePriceField);

        panel.add(new JLabel("Product Invoice Price: $", JLabel.RIGHT));
        productInvoicePriceField = new JFormattedTextField(priceFormatter);
        productInvoicePriceField.setColumns(10);
        panel.add(productInvoicePriceField);

        // Create a NumberFormatter for integers
        NumberFormatter intFormatter = new NumberFormatter();
        intFormatter.setValueClass(Integer.class);
        intFormatter.setAllowsInvalid(false);

        panel.add(new JLabel("Product Stock Quantity: ", JLabel.RIGHT));
        productStockQuantityField = new JFormattedTextField(intFormatter);
        productStockQuantityField.setColumns(10);
        panel.add(productStockQuantityField);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);

        createProductButton = new JButton("Create Product");
        createProductButton.addActionListener(e -> createProduct());
        panel.add(createProductButton);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        panel.add(errorLabel);

        add(panel);
        setVisible(true);
    }

    /**
     * <p>Creates the product with the given information from the form.</p>
     */
    private void createProduct() {
        String productName = productNameField.getText();
        String productType = productTypeField.getText();
        String productDescription = productDescriptionField.getText();

        if (productName.isEmpty() || productType.isEmpty() || productDescription.isEmpty() ||
                productSalePriceField.getText().isEmpty() || productInvoicePriceField.getText().isEmpty() ||
                productStockQuantityField.getText().isEmpty()) {
            errorLabel.setText("Please fill all the fields.");
            return;
        }

        double productSalePrice = Double.parseDouble(productSalePriceField.getText());
        double productInvoicePrice = Double.parseDouble(productInvoicePriceField.getText());
        int productStockQuantity = Integer.parseInt(productStockQuantityField.getText());

        if (productSalePrice < 0 || productInvoicePrice < 0 || productStockQuantity < 0) {
            errorLabel.setText("Prices and stock quantity cannot be negative.");
        }

        try {
            Product newProduct = new Product(productName, productType, productDescription,
                    productSalePrice, productInvoicePrice, productStockQuantity);
            if (inventoryController.addProduct(newProduct)) {
                JOptionPane.showMessageDialog(this, "Product added successfully.");
                dispose();
            } else {
                errorLabel.setText("Product already exists.");
            }
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }
}
