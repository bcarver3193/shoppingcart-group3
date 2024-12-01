package cop4331.model.seller;

import cop4331.model.User;

import java.io.Serializable;

/**
 * <p>Represents a Seller in the system. Extends the {@code User} class.
 * Holds information relating to sellers such as their inventory and financial data.</p>
 * @author Jeremy Ladanowski
 * @author Benjamin Carver
 */
public class Seller extends User implements Serializable {
    private double totalRevenue;
    private double totalCost;

    /**
     * <p>Constructs a Seller object.</p>
     * @param username The username of the new Seller.
     * @param password The password of the new Seller.
     */
    public Seller(String username, String password) {
        super(username, password);
    }

    /**
     * <p>Gets the total revenue from the seller.</p>
     * @return The seller's total revenue.
     */
    public double getTotalRevenue() {
        return totalRevenue;
    }

    /**
     * <p>Gets the total cost incurred by the seller.</p>
     * @return The seller's total cost.
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * <p>Calculates the profit of the seller.</p>
     * @return The profit of the seller (revenue - cost).
     */
    public double getProfit() {
        return totalRevenue - totalCost;
    }

    /**
     * <p>Returns the role of the Seller for identification within the system.</p>
     * @return "Seller"
     */
    @Override
    public String getRole() {
        return "Seller";
    }

    /**
     * <p>Updates the financial data stored within the seller.</p>
     * @param revenue The revenue to add.
     * @param cost The cost to add.
     */
    public void updateFinancialData(double revenue, double cost) {
        totalRevenue += revenue;
        totalCost += cost;
    }
}
