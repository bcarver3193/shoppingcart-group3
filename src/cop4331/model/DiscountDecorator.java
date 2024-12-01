package cop4331.model;

import java.io.Serializable;

/**
 * <p>An abstract class that serves as a decorator for applying discounts onto
 * {@code ProductComponent} objects. Implements the {@code ProductComponent} interface.</p>
 * @author Denis Ziegler
 * @author Benjamin Carver
 */
public abstract class DiscountDecorator implements ProductComponent {
    protected ProductComponent productComponent;
    protected String seller;

    /**
     * <p>Constructs a DiscountDecorator object.</p>
     * @param productComponent The product to be discounted.
     * @throws IllegalArgumentException If product is null.
     */
    public DiscountDecorator(ProductComponent productComponent) {
        if (productComponent == null) {
            throw new IllegalArgumentException("Product must not be null.");
        }
        this.productComponent = productComponent;
        // ProductComponents will only be created by the current session user.
        // We can use the getCurrentUser method to implicitly assign the product,
        // bundle, or discount to the Seller who created it.
        this.seller = Session.getInstance().getCurrentUser().getUsername();
    }

    /**
     * <p>Gets the base product from the discount.</p>
     * @return The base product.
     */
    public ProductComponent getProduct() {
        return productComponent;
    }

    /**
     * <p>Gets the name of the base product.</p>
     * @return The name of the base product.
     */
    @Override
    public String getName() {
        return productComponent.getName();
    }

    /**
     * <p>Gets the description of the base product.</p>
     * @return The description of the base product.
     */
    @Override
    public String getDescription() {
        return productComponent.getDescription();
    }

    /**
     * <p>Gets the type of the base product.</p>
     * @return The type of the base product.
     */
    @Override
    public String getType() {
        return "Discounted " + productComponent.getType();
    }

    /**
     * <p>Gets the invoice price of the base product.</p>
     * @return The invoice price of the base product.
     */
    @Override
    public double getInvoicePrice() {
        return productComponent.getInvoicePrice();
    }

    /**
     * <p>Gets the stock quantity of the base product.</p>
     * @return The stock quantity of the base product.
     */
    @Override
    public int getStockQuantity() {
        return productComponent.getStockQuantity();
    }

    /**
     * <p>Gets the seller of the base product's username.</p>
     * @return The base product seller's username.
     */
    @Override
    public String getSeller() {
        return seller;
    }

    /**
     * <p>Sets the stock quantity of the item to a specified value.</p>
     * @param stockQuantity The quantity value to be set.
     * @throws IllegalArgumentException If stockQuantity is negative.
     */
    @Override
    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity >= 0) {
            this.getProduct().setStockQuantity(stockQuantity);
        } else {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
    }

    /**
     * <p>Reduces the stock quantity by the specified amount.</p>
     * @param stockQuantity The amount to reduce the stock by.
     * @throws IllegalArgumentException If stockQuantity is negative.
     */
    @Override
    public void reduceStockQuantity(int stockQuantity) {
        if (stockQuantity >= 0) {
            this.getProduct().reduceStockQuantity(stockQuantity);
        } else {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
    }
}
