package cop4331.controller;

import cop4331.model.Inventory;
import cop4331.model.ProductComponent;
import cop4331.view.customer.CustomerDashboardView;
import cop4331.view.seller.SellerDashboardView;

import java.util.Iterator;

/**
 * <p>Handles interactions with the product inventory such as adding and removing products,
 * viewing products, and searching for products.</p>
 * @author Benjamin Carver
 */
public class InventoryController {
    private Inventory inventory;
    private CustomerDashboardView customerDashboardView;
    private SellerDashboardView sellerDashboardView;

    /**
     * <p>Constructs an InventoryController object.</p>
     */
    public InventoryController() {
        this.inventory = Inventory.getInstance();
    }

    /**
     * <p>Constructs an InventoryController object.</p>
     * @param customerDashboardView The customer dashboard view.
     */
    public InventoryController(CustomerDashboardView customerDashboardView) {
        this.inventory = Inventory.getInstance();
        this.customerDashboardView = customerDashboardView;
    }

    /**
     * <p>Constructs an InventoryController object.</p>
     * @param sellerDashboardView The seller dashboard view.
     */
    public InventoryController(SellerDashboardView sellerDashboardView) {
        this.inventory = Inventory.getInstance();
        this.sellerDashboardView = sellerDashboardView;
    }

    /**
     * <p>Adds a product to the inventory.</p>
     * @param product The product to be added.
     */
    public boolean addProduct(ProductComponent product) {
        try {
            inventory.addProduct(product);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        }

        sellerDashboardView.refreshView();
        return true;
    }

    /**
     * <p>Removes a product from the inventory.</p>
     * @param product The product to be removed.
     */
    public boolean removeProduct(ProductComponent product) {
        try {
            inventory.removeProduct(product);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        }

        sellerDashboardView.refreshView();
        return true;
    }

    /**
     * <p>Finds and returns a product from the inventory by its name.</p>
     * @param productName The name of the product to search for.
     * @return The ProductComponent with the matching name,
     * or null if not found.
     */
    public ProductComponent findProductByName(String productName) {
        Iterator<ProductComponent> it = inventory.iterator();
        while (it.hasNext()) {
            ProductComponent product = it.next();
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        // Product not found
        return null;
    }

    /**
     * <p>Updates a product by replacing it in the Inventory</p>
     * @param newProduct The edited product.
     * @throws IllegalArgumentException If product is null or the product did
     * not previously exist in the Inventory.
     */
    public void updateProduct(ProductComponent newProduct) {
        if (newProduct == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        ProductComponent oldProduct = findProductByName(newProduct.getName());
        if (oldProduct == null) {
            throw new IllegalArgumentException(newProduct.getName() + " does not exist");
        }

        try {
            Inventory.getInstance().setProduct(newProduct);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        inventory.notifyObservers();
    }

    /**
     * <p>Reduces the stock of a product by a set amount.</p>
     * @param product The product to subtract from.
     * @param quantity The amount to remove from the stock.
     * @return true if successful, false otherwise
     */
    public boolean reduceStock(ProductComponent product, int quantity) {
        try {
            product.reduceStockQuantity(quantity);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }
}
