package cop4331.view.seller;

import cop4331.model.FlatDiscount;
import cop4331.model.PercentageDiscount;
import cop4331.model.ProductComponent;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

/**
 * <p>Represents the Discount product GUI for sellers.</p>
 * @author Benjamin Carver
 */
public class DiscountView extends JFrame {
    private SellerDashboardView dashboardView;
    private ProductComponent product;
    private JComboBox<String> discountTypeComboBox;
    private JFormattedTextField discountAmountField;
    private JLabel errorLabel;

    /**
     * <p>Creates the DiscountView object.</p>
     * @param dashboardView the seller dashboard.
     * @param product the product being discounted.
     */
    public DiscountView(SellerDashboardView dashboardView, ProductComponent product) {
        this.dashboardView = dashboardView;
        this.product = product;
        setTitle("Apply Discount");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel dataInputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        dataInputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        dataInputPanel.add(new JLabel("Product:"));
        dataInputPanel.add(new JLabel(product.getName()));

        dataInputPanel.add(new JLabel("Discount Type:"));
        discountTypeComboBox = new JComboBox<>(new String[] {"Percentage", "Flat"});
        dataInputPanel.add(discountTypeComboBox);

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setParseIntegerOnly(false);
        numberFormat.setMaximumFractionDigits(2);

        dataInputPanel.add(new JLabel("Discount Amount:"));
        discountAmountField = new JFormattedTextField(numberFormat);
        discountAmountField.setValue(0);
        dataInputPanel.add(discountAmountField);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        dataInputPanel.add(cancelButton);

        JButton applyButton = new JButton("Apply Discount");
        applyButton.addActionListener(e -> applyDiscount());
        dataInputPanel.add(applyButton);

        panel.add(dataInputPanel, BorderLayout.CENTER);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        panel.add(errorLabel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    /**
     * <p>Applies the discount to the specifications provided in the fields.</p>
     */
    private void applyDiscount() {
        try {
            String discountType = discountTypeComboBox.getSelectedItem().toString();
            double discountAmount = Double.parseDouble(discountAmountField.getText());

            if (discountType.equals("Percentage")) {
                if (discountAmount <= 0 || discountAmount >= 100) {
                    errorLabel.setText("Percentage must be between 0 and 100.");
                    return;
                }
                product = new PercentageDiscount(product, discountAmount / 100);
            } else {
                if (discountAmount <= 0 || discountAmount >= product.getSalePrice()) {
                    errorLabel.setText("Discount amount must be greater than 0 and " +
                            "less than the product's sale price.");
                    return;
                }
                product = new FlatDiscount(product, discountAmount);
            }

            dashboardView.getInventoryController().updateProduct(product);
            dashboardView.refreshView();
            JOptionPane.showMessageDialog(null, "Discount applied successfully.");
            dispose();
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid discount amount.");
        }
    }
}
