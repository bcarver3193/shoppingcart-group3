package cop4331.view.customer;

import cop4331.controller.CheckoutController;
import cop4331.model.customer.Customer;
import cop4331.model.Session;
import cop4331.model.customer.ShoppingCart;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Represents the checkout gui that shows when the customer is ready to check out.
 * Allows the user the ability to review their order before proceeding with payment.</p>
 * @author Benjamin Carver
 */
public class CheckoutView extends JFrame {
    private Customer customer;
    private ShoppingCart shoppingCart;
    private CheckoutController checkoutController;
    private JTextArea invoiceTextArea;

    /**
     * <p>Constructs the CheckoutView object with the given parameters.</p>
     * @param checkoutController The {@code CheckoutController} used in the process.
     */
    public CheckoutView(CheckoutController checkoutController) {
        this.customer = (Customer) Session.getInstance().getCurrentUser();
        this.shoppingCart = customer.getCart();
        this.checkoutController = checkoutController;

        setTitle("Checkout");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create invoice display
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Invoice", SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        invoiceTextArea = new JTextArea(10, 30);
        invoiceTextArea.setEditable(false);
        JScrollPane invoiceScrollPane = new JScrollPane(invoiceTextArea);
        panel.add(invoiceScrollPane, BorderLayout.CENTER);

        // Generate and display the invoice
        String invoice = checkoutController.generateInvoice(shoppingCart);
        invoiceTextArea.setText(invoice);

        // Create buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> cancelCheckout());
        buttonPanel.add(cancelButton);

        JButton proceedButton = new JButton("Proceed to Payment");
        proceedButton.addActionListener(e -> showPaymentWindow());
        buttonPanel.add(proceedButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    /**
     * <p>Cancels the checkout process.</p>
     */
    private void cancelCheckout() {
        dispose();
    }

    /**
     * <p>Shows the payment window.</p>
     */
    private void showPaymentWindow() {
        new PaymentWindow(this, customer, shoppingCart, checkoutController);
    }
}

/**
 * <p>Represents the GUI shown to the customer when payment details must be provided.
 * Facilitates the collection of payment details and verifies them through the {@code CheckoutController}.</p>
 * @author Benjamin Carver
 */
class PaymentWindow extends JFrame {
    private CheckoutView checkoutView;
    private Customer customer;
    private ShoppingCart shoppingCart;
    private CheckoutController checkoutController;
    private JTextField cardNumberField;
    private JTextField expiryDateField;
    private JTextField cvvField;
    private JLabel errorLabel;

    /**
     * <p>Creates the PaymentWindow object with the specified arguments.</p>
     * @param checkoutView The checkout view window.
     * @param customer The customer making the purchase.
     * @param shoppingCart The customer's shopping cart.
     * @param checkoutController The {@code CheckoutController}
     */
    public PaymentWindow(CheckoutView checkoutView, Customer customer, ShoppingCart shoppingCart,
                         CheckoutController checkoutController) {
        this.checkoutView = checkoutView;
        this.customer = customer;
        this.shoppingCart = shoppingCart;
        this.checkoutController = checkoutController;

        setTitle("Enter Payment Details");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel checkoutPanel = new JPanel();
        checkoutPanel.setLayout(new GridLayout(4, 2, 10, 10));

        checkoutPanel.add(new JLabel("Card Number:"));
        cardNumberField = new JTextField();
        checkoutPanel.add(cardNumberField);

        checkoutPanel.add(new JLabel("Expiry Date (MM/YY):"));
        expiryDateField = new JTextField();
        checkoutPanel.add(expiryDateField);

        checkoutPanel.add(new JLabel("CVV:"));
        cvvField = new JTextField();
        checkoutPanel.add(cvvField);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        checkoutPanel.add(cancelButton);

        JButton submitButton = new JButton("Submit Payment");
        submitButton.addActionListener(e -> submitPayment());
        checkoutPanel.add(submitButton);

        JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorPanel.add(errorLabel);

        panel.add(checkoutPanel, BorderLayout.CENTER);
        panel.add(errorPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    /**
     * <p>Submits the payment information to the {@code CheckoutController} for finalization of the order.</p>
     */
    private void submitPayment() {
        String cardNumber = cardNumberField.getText();
        String expiryDate = expiryDateField.getText();
        String cvv = cvvField.getText();

        if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
            errorLabel.setText("Please fill out all fields.");
            return;
        }

        checkoutController.checkout(customer, shoppingCart, cardNumber, expiryDate, cvv);
        JOptionPane.showMessageDialog(null, "Payment Successful");
        dispose();
        checkoutView.dispose();
    }
}
