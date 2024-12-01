package cop4331.model;

import java.io.Serializable;

/**
 * <p>Applies a flat discount to the price of a product. Extends the {@code DiscountDecorator}
 * class.</p>
 * @author Denis Ziegler
 * @author Benjamin Carver
 */
public class FlatDiscount extends DiscountDecorator {
    private double discount;

    /**
     * <p>Constructs a FlatDiscount object.</p>
     * @param productComponent The product to be discounted.
     * @param discount The discount amount.
     * @throws IllegalArgumentException If discount is negative or exceeds product's price.
     */
    public FlatDiscount(ProductComponent productComponent, double discount) {
        super(productComponent);
        if (discount <= 0) {
            throw new IllegalArgumentException("Discount cannot be negative.");
        } else if (discount > productComponent.getSalePrice()) {
            throw new IllegalArgumentException("Discount must be less than the product's sale price.");
        }
        this.discount = discount;
    }

    /**
     * <p>Gets the discount amount for the discount.</p>
     * @return The discount amount.
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * <p>Sets a new discount amount.</p>
     * @param discount The new discount amount.
     * @throws IllegalArgumentException If discount is negative or exceeds product's price.
     */
    public void setDiscount(double discount) {
        if (discount <= 0) {
            throw new IllegalArgumentException("Discount cannot be negative.");
        } else if (discount > productComponent.getSalePrice()) {
            throw new IllegalArgumentException("Discount must be less than the product's sale price.");
        }
        this.discount = discount;
    }

    /**
     * <p>Gets the discounted sale price of the product.</p>
     * @return The discounted sale price of the product.
     */
    @Override
    public double getSalePrice() {
        return productComponent.getSalePrice() - discount;
    }
}
