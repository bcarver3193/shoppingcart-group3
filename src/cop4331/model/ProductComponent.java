package cop4331.model;

/**
 * <p>Interface for storing information regarding products, bundles, and discounts.</p>
 * @author Denry Ormejuste
 * @author Benjamin Carver
 */
public interface ProductComponent {
    String getName();
    String getDescription();
    String getType();
    int getStockQuantity();
    double getSalePrice();
    double getInvoicePrice();
    String getSeller();

    void setStockQuantity(int quantity);
    void reduceStockQuantity(int quantity);
}
