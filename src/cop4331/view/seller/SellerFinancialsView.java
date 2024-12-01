package cop4331.view.seller;

import cop4331.model.seller.Seller;
import cop4331.model.Session;

import javax.swing.*;
import java.awt.*;

public class SellerFinancialsView extends JFrame {
    private Seller seller;
    private JLabel revenueLabel;
    private JLabel costLabel;
    private JLabel profitLabel;

    SellerFinancialsView() {
        this.seller = (Seller) Session.getInstance().getCurrentUser();

        setTitle("Financial Overview");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel financialsPanel = new JPanel(new GridLayout(3, 2));

        financialsPanel.add(new JLabel("Total Revenue: ", JLabel.RIGHT));
        revenueLabel = new JLabel("$" + String.format("%.2f", seller.getTotalRevenue()), JLabel.LEFT);
        revenueLabel.setForeground(Color.GREEN);
        financialsPanel.add(revenueLabel);

        financialsPanel.add(new JLabel("Total Costs: ", JLabel.RIGHT));
        costLabel = new JLabel("-$" + String.format("%.2f", seller.getTotalCost()), JLabel.LEFT);
        costLabel.setForeground(Color.RED);
        financialsPanel.add(costLabel);

        financialsPanel.add(new JLabel("Profit: ", JLabel.RIGHT));
        if (seller.getProfit() < 0) {
            // Need to multiply the profit by -1 to remove a redundant '-' from appearing
            // on the wrong side of the '$' when formatted.
            profitLabel = new JLabel("-$" + String.format("%.2f", -1 * seller.getProfit()), JLabel.LEFT);
            profitLabel.setForeground(Color.RED);
        } else {
            profitLabel = new JLabel("$" + String.format("%.2f", seller.getProfit()), JLabel.LEFT);
            profitLabel.setForeground(Color.GREEN);
        }
        financialsPanel.add(profitLabel);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        panel.add(financialsPanel, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}
