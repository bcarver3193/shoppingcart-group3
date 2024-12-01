package cop4331.model;

import java.io.Serializable;

/**
 * <p>Applies a percentage discount to the price of the product. Extends the
 * {@code DiscountDecorator} class.</p>
 * @author Denis Ziegler
 * @author Benjamin Carver
 */
public class PercentageDiscount extends DiscountDecorator {
    private double percentage;

    /**
     * <p>Constructs a PercentageDiscount object.</p>
     * @param productComponent The product to be discounted.
     * @param percentage The discount percentage.
     * @throws IllegalArgumentException If percentage is negative or
     *                                  if percentage is greater than 1.
     */
    public PercentageDiscount(ProductComponent productComponent, double percentage) {
        super(productComponent);
        if (percentage < 0) {
            throw new IllegalArgumentException("Percentage cannot be negative.");
        }
        if (percentage > 1) {
            throw new IllegalArgumentException("Percentage cannot be greater than 1.");
        }
        this.percentage = percentage;
    }

    /**
     * <p>Gets the percentage of the discount.</p>
     * @return The discount percentage.
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * <p>Sets the percentage of the discount to a new value.</p>
     * @param percentage The new discount percentage.
     * @throws IllegalArgumentException If percentage is negative or
     *                                  if percentage is greater than 1.
     */
    public void setPercentage(double percentage) {
        if (percentage < 0) {
            throw new IllegalArgumentException("Percentage cannot be negative.");
        }
        if (percentage > 1) {
            throw new IllegalArgumentException("Percentage cannot be greater than 1.");
        }
        this.percentage = percentage;
    }

    /**
     * <p>Gets the discounted sale price of the Product.</p>
     * @return The discounted sale price.
     */
    @Override
    public double getSalePrice() {
        return productComponent.getSalePrice() * (1 - percentage);
    }
}
