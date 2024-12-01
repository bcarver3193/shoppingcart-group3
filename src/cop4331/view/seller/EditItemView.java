package cop4331.view.seller;

import cop4331.model.Bundle;
import cop4331.model.Product;
import cop4331.model.ProductComponent;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * <p>Represents the seller GUI for editing items in their inventory.</p>
 * @author Benjamin Carver
 */
public class EditItemView extends JFrame {
    private SellerDashboardView dashboardView;
    private ProductComponent productComponent;
    private JTextField nameField;
    private JTextArea descriptionField;
    private JTextField typeField;
    private JFormattedTextField invoicePriceField;
    private JFormattedTextField salePriceField;
    private JFormattedTextField stockQuantityField;
    private JLabel errorLabel;

    /**
     * <p>Creates a new EditItemView object.</p>
     * @param dashboardView the seller dashboard.
     * @param productComponent the product being edited.
     */
    public EditItemView(SellerDashboardView dashboardView, ProductComponent productComponent) {
        this.dashboardView = dashboardView;
        this.productComponent = productComponent;
        setTitle("Edit Product");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        if (productComponent instanceof Bundle) {
            JOptionPane.showMessageDialog(null,"Editing bundles is not yet supported.");
            return;
        }

        JPanel panel = new JPanel(new BorderLayout());
        
        // Construct the Data Entry Panel
        JPanel dataEntryPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        dataEntryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        dataEntryPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        nameField.setText(productComponent.getName());
        dataEntryPanel.add(nameField);
        
        dataEntryPanel.add(new JLabel("Description:"));
        descriptionField = new JTextArea(5, 10);
        descriptionField.setText(productComponent.getDescription());
        dataEntryPanel.add(descriptionField);
        
        dataEntryPanel.add(new JLabel("Type:"));
        typeField = new JTextField();
        typeField.setText(productComponent.getType());
        dataEntryPanel.add(typeField);

        // Specify the price format
        DecimalFormat priceFormat = new DecimalFormat("#.##");
        priceFormat.setMinimumFractionDigits(2);
        priceFormat.setMaximumFractionDigits(2);

        // Create a NumberFormatter for doubles
        NumberFormatter priceFormatter = new NumberFormatter(priceFormat);
        priceFormatter.setValueClass(Double.class);
        priceFormatter.setMinimum(0);
        priceFormatter.setAllowsInvalid(false);
        
        dataEntryPanel.add(new JLabel("Invoice Price: $"));
        invoicePriceField = new JFormattedTextField(priceFormatter);
        invoicePriceField.setValue(productComponent.getInvoicePrice());
        dataEntryPanel.add(invoicePriceField);
        
        dataEntryPanel.add(new JLabel("Sale Price: $"));
        salePriceField = new JFormattedTextField(priceFormatter);
        salePriceField.setValue(productComponent.getSalePrice());
        dataEntryPanel.add(salePriceField);

        // Create a NumberFormatter for integers
        NumberFormatter intFormatter = new NumberFormatter();
        intFormatter.setValueClass(Integer.class);
        intFormatter.setMinimum(0);
        intFormatter.setAllowsInvalid(false);
        
        dataEntryPanel.add(new JLabel("Stock Quantity:"));
        stockQuantityField = new JFormattedTextField(intFormatter);
        stockQuantityField.setValue(productComponent.getStockQuantity());
        dataEntryPanel.add(stockQuantityField);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        dataEntryPanel.add(cancelButton);
        
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> saveChanges());
        dataEntryPanel.add(saveButton);
        
        // Construct the error container
        JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorPanel.add(errorLabel);
        
        panel.add(dataEntryPanel, BorderLayout.CENTER);
        panel.add(errorPanel, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * <p>Applies the changes made on the form.</p>
     */
    private void saveChanges() {
        try {
            Product newProduct = getNewProduct();

            dashboardView.getInventoryController().removeProduct(productComponent);
            dashboardView.getInventoryController().addProduct(newProduct);
            dashboardView.refreshView();
            JOptionPane.showMessageDialog(this, "Product has been saved");
            dispose();
        } catch (NumberFormatException e) {
            errorLabel.setText("Please enter a valid number");
        } catch (IllegalArgumentException e) {
            errorLabel.setText("Invalid argument, please verify product name is unique.");
        } catch (Exception e) {
            errorLabel.setText("Something went wrong");
        }
    }

    /**
     * <p>Handles the generation of the updated product.</p>
     * @return the updated product.
     */
    private Product getNewProduct() {
        String productName = nameField.getText();
        String productDescription = descriptionField.getText();
        String productType = typeField.getText();
        double productInvoicePrice = Double.parseDouble(invoicePriceField.getText());
        double productSalePrice = Double.parseDouble(salePriceField.getText());
        int productStockQuantity = Integer.parseInt(stockQuantityField.getText());

        return new Product(productName, productType, productDescription,
                productSalePrice, productInvoicePrice, productStockQuantity);
    }
}
