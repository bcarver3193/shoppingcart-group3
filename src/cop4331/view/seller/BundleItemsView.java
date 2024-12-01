package cop4331.view.seller;

import cop4331.model.Bundle;
import cop4331.model.ProductComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Represents the seller GUI for bundling items.
 * Allows sellers to choose from a list the products to bundle.</p>
 * @author Benjamin Carver
 */
public class BundleItemsView extends JFrame {
    private SellerDashboardView dashboardView;
    private JTextField bundleNameField;
    private JFormattedTextField percentDiscountField;
    private JTable productTable;
    private JLabel errorLabel;

    /**
     * <p>Creates a new BundleItemsView object.</p>
     * @param dashboardView The seller dashboard.
     */
    public BundleItemsView(SellerDashboardView dashboardView) {
        this.dashboardView = dashboardView;
        setTitle("Bundle Items");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel dataEntryPanel = getDataEntryPanel();
        panel.add(dataEntryPanel, BorderLayout.NORTH);

        List<ProductComponent> products = dashboardView.getSellerInventory();
        String[] columnNames = {"Name", "Type", "Sale Price"};
        Object[][] data = new Object[products.size()][columnNames.length];
        for (int i = 0; i < products.size(); i++) {
            ProductComponent product = products.get(i);
            data[i][0] = product.getName();
            data[i][1] = product.getType();
            data[i][2] = product.getSalePrice();
        }

        productTable = new JTable(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        JScrollPane scrollPane = new JScrollPane(productTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = getButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    /**
     * <p>Creates the button panel.</p>
     * @return The buttonPanel {@code JPanel}
     */
    private JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        JButton bundleButton = new JButton("Bundle Selected Items");
        bundleButton.addActionListener(e -> bundleSelectedItems());

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        buttonPanel.add(errorLabel);
        buttonPanel.add(cancelButton);
        buttonPanel.add(bundleButton);
        return buttonPanel;
    }

    /**
     * <p>Creates the dataEntryPanel.</p>
     * @return The dataEntryPanel {@code JPanel}
     */
    private JPanel getDataEntryPanel() {
        JPanel dataEntryPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        dataEntryPanel.add(new JLabel("Bundle Name: "));
        bundleNameField = new JTextField(10);
        dataEntryPanel.add(bundleNameField);
        dataEntryPanel.add(new JLabel("Discount Percentage: %"));

        // Create an integer input field
        NumberFormatter intFormatter = new NumberFormatter();
        intFormatter.setValueClass(Integer.class);
        intFormatter.setMinimum(0);
        intFormatter.setMaximum(100);
        intFormatter.setAllowsInvalid(false);

        percentDiscountField = new JFormattedTextField(intFormatter);
        dataEntryPanel.add(percentDiscountField);
        return dataEntryPanel;
    }

    /**
     * <p>Bundles the items selected by the seller.</p>
     */
    private void bundleSelectedItems() {
        try {
            int[] selectedRows = productTable.getSelectedRows();
            String bundleName = bundleNameField.getText();
            int percentDiscount = Integer.parseInt(percentDiscountField.getText());
            double convertedDiscount = percentDiscount / 100.0;

            if (bundleName.isEmpty()) {
                errorLabel.setText("Bundle name cannot be empty");
                return;
            }

            List<ProductComponent> selectedItems = new ArrayList<>();
            for (int row : selectedRows) {
                String productName = productTable.getValueAt(row, 0).toString();
                ProductComponent product = dashboardView.getInventoryController().findProductByName(productName);
                if (product == null) {
                    errorLabel.setText("Product " + productName + " not found");
                    return;
                }
                selectedItems.add(product);
            }
            if (selectedItems.isEmpty()) {
                errorLabel.setText("No items selected");
                return;
            }

            Bundle bundle = new Bundle(bundleName, convertedDiscount, selectedItems);
            dashboardView.getInventoryController().addProduct(bundle);
            dashboardView.refreshView();
            JOptionPane.showMessageDialog(null, "Bundle created successfully.");
            dispose();
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid discount percentage");
        }
    }
}
