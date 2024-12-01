package cop4331.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Represents a collection of products bundled together and sold as a single unit.
 * Implements the {@code ProductComponent} interface. Provides functionality to manage
 * the bundled products.</p>
 * @author Denry Ormejuste
 * @author Benjamin Carver
 */
public class Bundle implements ProductComponent {
    private String name;
    private String type;
    private double discountPercentage;
    private List<ProductComponent> bundledProducts;
    private final String seller;

    /**
     * <p>Constructs a Bundle object with an empty list.</p>
     * @param name The name of the Bundle.
     * @param discountPercentage The discount applied to the Bundle.
     */
    public Bundle(String name, double discountPercentage) {
        this.name = name;
        this.type = "Bundle";
        this.discountPercentage = discountPercentage;
        bundledProducts = new ArrayList<>();
        // ProductComponents will only be created by the current session user.
        // We can use the getCurrentUser method to implicitly assign the product,
        // bundle, or discount to the Seller who created it.
        this.seller = Session.getInstance().getCurrentUser().getUsername();
    }

    /**
     * <p>Constructs a Bundle object with the provided list.</p>
     * @param name The name of the Bundle.
     * @param discountPercentage The discount applied to the Bundle.
     * @param productList A list of products to be bundled.
     */
    public Bundle(String name, double discountPercentage, List<ProductComponent> productList) {
        this.name = name;
        this.type = "Bundle";
        this.discountPercentage = discountPercentage;
        bundledProducts = productList;
        this.seller = Session.getInstance().getCurrentUser().getUsername();
    }

    /**
     * <p>Gets the name of the bundle.</p>
     * @return The name of the bundle.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * <p>Constructs the description of the bundle.</p>
     * @return The description of the bundle.
     */
    @Override
    public String getDescription() {
        StringBuilder description = new StringBuilder(name + " includes:\n");
        for (ProductComponent product : bundledProducts) {
            description.append("- ").append(product.getName()).append(": ")
                    .append(product.getDescription()).append("\n");
        }
        return description.toString();
    }

    /**
     * <p>Gets the type of the bundle. Always "Bundle".</p>
     * @return "Bundle"
     */
    public String getType() {
        return type;
    }

    /**
     * <p>Gets the minimum stock for the bundle by checking the stock values of the items inside.</p>
     * @return The minimum stock value in the bundle.
     */
    @Override
    public int getStockQuantity() {
        int minStock = Integer.MAX_VALUE;
        for (ProductComponent product : bundledProducts) {
            if (product.getStockQuantity() < minStock) {
                minStock = product.getStockQuantity();
            }
        }

        return minStock;
    }

    /**
     * <p>Gets the sale price of the bundle by totaling the prices of its constituent products
     * and applying the Bundle's discount.</p>
     * @return The sale price of the Bundle.
     */
    @Override
    public double getSalePrice() {
        double salePrice = 0;
        for (ProductComponent product : bundledProducts) {
            salePrice += product.getSalePrice();
        }

        // Apply the Bundle's discount by multiplying it by the reciprocal of the percentage.
        salePrice *= (1 - discountPercentage);
        return salePrice;
    }

    /**
     * <p>Gets the invoice price of the bundle by totaling the invoice prices of its
     * constituent products.</p>
     * @return The total invoice price of the Bundle.
     */
    @Override
    public double getInvoicePrice() {
        double invoicePrice = 0;
        for (ProductComponent product : bundledProducts) {
            invoicePrice += product.getInvoicePrice();
        }

        return invoicePrice;
    }

    /**
     * <p>Gets the seller of the bundle's username.</p>
     * @return The bundle seller's username.
     */
    @Override
    public String getSeller() {
        return seller;
    }

    /**
     * <p>Adds a product to the Bundle.</p>
     * @param product The product to be added.
     * @throws IllegalArgumentException If product is null or if product is already in the Bundle.
     */
    public void addProductToBundle(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (bundledProducts.contains(product)) {
            throw new IllegalArgumentException("Product is already in the bundle");
        }

        bundledProducts.add(product);
    }

    /**
     * <p>Removes a product from the Bundle.</p>
     * @param product The product to be removed.
     * @throws IllegalArgumentException If product is null or if product is not found in the Bundle.
     */
    public void removeProductFromBundle(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot remove null product.");
        }
        if (!bundledProducts.remove(product)) {
            throw new IllegalArgumentException("Product is not in the bundle");
        }
    }

    /**
     * <p>Sets the stock quantity of the bundle to a specific value.</p>
     * @param stockQuantity The new stock quantity.
     */
    @Override
    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        int difference = this.getStockQuantity() - stockQuantity;

        for (ProductComponent product : bundledProducts) {
            product.reduceStockQuantity(difference);
        }
    }

    /**
     * <p>Removes stock quantity from the bundle.</p>
     * @param stockQuantity The new stock quantity.
     */
    @Override
    public void reduceStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        for (ProductComponent product : bundledProducts) {
            product.reduceStockQuantity(stockQuantity);
        }
    }
}
