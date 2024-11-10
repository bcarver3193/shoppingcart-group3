package model;

import java.util.ArrayList;
import java.util.List;

public class Seller extends User {
    private List<SingleProduct> inventory = new ArrayList<>();
    private double totalRevenue = 0;
    private double totalCost = 0;

    public Seller(String username, String password) {
        super(username, password);
    }

    public void addProduct(SingleProduct product) {
        inventory.add(product);
    }

    public void updateRevenue(double revenue, double cost) {
        totalRevenue += revenue;
        totalCost += cost;
    }

    public double getTotalProfit() {
        return totalRevenue - totalCost;
    }

    public List<SingleProduct> getInventory() {
        return new ArrayList<>(inventory);
    }
}
