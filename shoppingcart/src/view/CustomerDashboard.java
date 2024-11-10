package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDashboard extends JFrame {
    private JTable productTable;
    private JLabel welcomeLabel;
    private JLabel cartTotalLabel;
    private JButton viewCartButton;
    private JButton logoutButton;
    
    public CustomerDashboard() {
        // Setup frame
        setTitle("Customer Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // North panel
        JPanel northPanel = new JPanel(new BorderLayout());
        JLabel logoLabel = new JLabel("LOGO", JLabel.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel = new JLabel("Welcome, {username}", JLabel.LEFT);
        logoutButton = new JButton("Logout");
        
        northPanel.add(logoLabel, BorderLayout.CENTER);
        northPanel.add(welcomeLabel, BorderLayout.WEST);
        northPanel.add(logoutButton, BorderLayout.EAST);
        
        add(northPanel, BorderLayout.NORTH);
        
        // Center panel
        DefaultTableModel tableModel = getDefaultTableModel();

        productTable = new JTable(tableModel);
        productTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        productTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        add(tableScrollPane, BorderLayout.CENTER);
        
        // South panel
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cartTotalLabel = new JLabel("Cart Total");
        viewCartButton = new JButton("View Cart");

        southPanel.add(cartTotalLabel);
        southPanel.add(viewCartButton);

        add(southPanel, BorderLayout.SOUTH);

        // Add action listeners
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Logging out...");
                dispose();
            }
        });

        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Viewing cart...");
            }
        });

        setVisible(true);
    }

    private static DefaultTableModel getDefaultTableModel() {
        String[] columns = {"Product Name", "Price", "Quantity", "Action"};
        Object[][] data = {
                {"Product 1", "$5.00", "5 Available", "View Details"},
                {"Product 2", "$5.00", "5 Available", "View Details"},
                {"Product 3", "$5.00", "Out of Stock", "View Details"},
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        return tableModel;
    }
}
