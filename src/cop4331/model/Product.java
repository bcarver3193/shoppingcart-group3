package cop4331.model;

import java.io.Serializable;

/**
 * <p>Represents a product with attributes like name, type, description, prices and quantity.</p>
 * <p>Implements the {@code ProductComponent} interface.</p>
 *
 * @author Denry Ormejuste
 * @author Benjamin Carver
 */
public class Product implements ProductComponent {
    private String name;
    private String type;
    private String description;
    private double salePrice;
    private double invoicePrice;
    private int stockQuantity;
    private String seller;

    /**
     * <p>Constructs a Product object.</p>
     * @param name The name of the Product.
     * @param type The type/category the Product belongs in.
     * @param description A description of the Product.
     * @param salePrice How much the Product is being sold for.
     * @param invoicePrice How much the Product cost the seller.
     * @param stockQuantity How much Product is in Inventory.
     * @throws IllegalArgumentException If salePrice, invoicePrice, or stockQuantity is negative.
     */
    public Product(String name, String type, String description,
                   double salePrice, double invoicePrice, int stockQuantity) {
        this.name = name;
        this.type = type;
        this.description = description;
        if (salePrice >= 0) {
            this.salePrice = salePrice;
        } else {
            throw new IllegalArgumentException("Sale price cannot be negative.");
        }
        if (invoicePrice >= 0) {
            this.invoicePrice = invoicePrice;
        } else {
            throw new IllegalArgumentException("Invoice price cannot be negative.");
        }
        if (stockQuantity >= 0) {
            this.stockQuantity = stockQuantity;
        } else {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
        // ProductComponents will only be created by the current session user.
        // We can use the getCurrentUser method to implicitly assign the product,
        // bundle, or discount to the Seller who created it.
        this.seller = Session.getInstance().getCurrentUser().getUsername();
    }

    // Getters

    /**
     * <p>Gets the name of the product.</p>
     * @return The name of the product.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * <p>Gets the type of the product.</p>
     * @return The type of the product.
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * <p>Gets the description of the product.</p>
     * @return The description of the product.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * <p>Gets the sale price of the product.</p>
     * @return The sale price of the product.
     */
    @Override
    public double getSalePrice() {
        return salePrice;
    }

    /**
     * <p>Gets the invoice price of the product.</p>
     * @return The invoice price of the product.
     */
    public double getInvoicePrice() {
        return invoicePrice;
    }

    /**
     * <p>Gets the stock quantity of the product.</p>
     * @return The stock quantity of the product.
     */
    @Override
    public int getStockQuantity() {
        return stockQuantity;
    }

    /**
     * <p>Gets the seller of the product's username.</p>
     * @return The product seller's username.
     */
    @Override
    public String getSeller() {
        return seller;
    }

    // Setters

    /**
     * <p>Sets the product's name to a new value.</p>
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Sets the product's type to a new value.</p>
     * @param type The new type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * <p>Sets the product's description to a new value.</p>
     * @param description The new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>Sets the sale price of the Product.</p>
     * @param salePrice The new sale price.
     * @throws IllegalArgumentException If salePrice is negative.
     */
    public void setSalePrice(double salePrice) {
        if (salePrice >= 0) {
            this.salePrice = salePrice;
        } else {
            throw new IllegalArgumentException("Sale price cannot be negative.");
        }
    }

    /**
     * <p>Sets the invoice price of the Product.</p>
     * @param invoicePrice The new invoice price.
     * @throws IllegalArgumentException If invoicePrice is negative.
     */
    public void setInvoicePrice(double invoicePrice) {
        if (invoicePrice >= 0) {
            this.invoicePrice = invoicePrice;
        } else {
            throw new IllegalArgumentException("Invoice price cannot be negative.");
        }
    }

    /**
     * <p>Sets the stockQuantity of the Product.</p>
     * @param stockQuantity The new value for stockQuantity.
     * @throws IllegalArgumentException If stockQuantity is negative.
     */
    @Override
    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity >= 0) {
            this.stockQuantity = stockQuantity;
        } else {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
    }

    /**
     * <p>Removes the specified amount from the product stock quantity.</p>
     * @param stockQuantity The amount to remove from the stock quantity.
     * @throws IllegalArgumentException If quantity is invalid or greater than stock quantity.
     */
    @Override
    public void reduceStockQuantity(int stockQuantity) {
        if (stockQuantity >= 0 && stockQuantity <= this.stockQuantity) {
            this.stockQuantity -= stockQuantity;
        } else if (stockQuantity > this.stockQuantity) {
            throw new IllegalArgumentException("Quantity cannot be greater than the stock quantity.");
        } else {
            throw new IllegalArgumentException("Invalid stock quantity.");
        }
    }

    /**
     * <p>Checks if the product is in stock.</p>
     * @return true if one or more items are in stock, false otherwise.
     */
    public boolean isInStock() {
        return stockQuantity > 0;
    }

    /**
     * <p>Adds the specified quantity to the stockQuantity attribute of the Product.</p>
     * @param quantity The amount to be added.
     * @throws IllegalArgumentException If quantity is negative or would cause an integer overflow.
     */
    public void addStockQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity to add cannot be negative.");
        }

        if (Integer.MAX_VALUE - quantity < this.stockQuantity) {
            throw new IllegalArgumentException("Adding this quantity would cause an integer overflow.");
        }

        this.stockQuantity += quantity;
    }
}
