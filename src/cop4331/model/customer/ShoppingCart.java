package cop4331.model.customer;

import cop4331.model.ProductComponent;

import java.io.Serializable;
import java.util.HashMap;

/**
 * <p>Represents a shopping cart that holds products.</p>
 * @author Jeremy Ladanowski
 * @author Benjamin Carver
 */
public class ShoppingCart implements Serializable {
    private HashMap<ProductComponent, Integer> cartItems;

    /**
     * <p>Constructs a new ShoppingCart object.</p>
     */
    public ShoppingCart() {
       this.cartItems = new HashMap<>();
    }

    /**
     * <p>Gets the cart items from the Shopping Cart.</p>
     * @return A {@code HashMap} of cart items stored inside the ShoppingCart.
     */
    public HashMap<ProductComponent, Integer> getCartItems() {
       return cartItems;
    }

    /**
     * <p>Adds a product to the ShoppingCart. If the product exists in the cart, add the
     * quantities together.</p>
     * @param product The product to add.
     * @param quantity The amount of the product to add.
     * @throws IllegalArgumentException If quantity is less than or equal to 0 or
     *                                  if product is null.
     */
    public void addProduct(ProductComponent product, int quantity) {
       if (product == null) {
           throw new IllegalArgumentException("Product cannot be null.");
       }
       if (quantity <= 0) {
           throw new IllegalArgumentException("Quantity must be greater than 0.");
       }
       // If product is already in the cart, add the desired quantity to the existing item.
       if (cartItems.containsKey(product)) {
           cartItems.put(product, cartItems.get(product) + quantity);
       }
       cartItems.put(product, quantity);
    }

    /**
     * <p>Removes a product from the shopping cart.</p>
     * @param product The product to remove.
     * @throws IllegalArgumentException If the product is null or
     */
    public void removeProduct(ProductComponent product) {
       if (product == null) {
           throw new IllegalArgumentException("Product cannot be null.");
       }
       if (!cartItems.containsKey(product)) {
           throw new IllegalArgumentException("Product does not exist in shopping cart.");
       }

       cartItems.remove(product);
    }

    /**
     * <p>Updates the quantity of a product in the shopping cart. If the desired quantity is 0,
     * the product is removed from the shopping cart.</p>
     * @param product The product whose quantity is changing.
     * @param quantity The new quantity of the product in the cart.
     */
    public void updateProductQuantity(ProductComponent product, int quantity) {
       if (product == null) {
           throw new IllegalArgumentException("Product cannot be null.");
       }
       if (!cartItems.containsKey(product)) {
           throw new IllegalArgumentException("Product does not exist in shopping cart.");
       }
       if (quantity < 0) {
           throw new IllegalArgumentException("Quantity cannot be negative.");
       } else if (quantity == 0) {
           // Setting the quantity to 0 removes the product from the cart.
           this.removeProduct(product);
       } else {
           cartItems.put(product, quantity);
       }
    }

    /**
     * <p>Calculates the total sale price of all items in the cart.</p>
     * @return The total sale price of all items in the cart.
     */
    public double calculateTotal() {
       double total = 0.0;
       for (ProductComponent product : cartItems.keySet()) {
           total += product.getSalePrice() * cartItems.get(product);
       }

       return total;
    }

    /**
     * <p>Converts the items in the shopping cart and their quantities into a string.
     * Used exclusively for debugging purposes.</p>
     * @return The string of the contents of the shopping cart.
     */
    @Override
    public String toString() {
       StringBuilder output = new StringBuilder();
       output.append("Shopping Cart:\n");
       for (ProductComponent product : cartItems.keySet()) {
           output.append("- ").append(product.getName()).append("\t\t| $").append(product.getSalePrice())
                   .append(" x").append(cartItems.get(product)).append("\n");
       }
       output.append("Total: $").append(this.calculateTotal());

       return output.toString();
    }

    /**
     * <p>Gets the total quantity of items in the cart.</p>
     * @return The total quantity of items in the cart.
     */
    public int getTotalQuantity() {
        int total = 0;
        for (ProductComponent product : cartItems.keySet()) {
            total += cartItems.get(product);
        }
        return total;
    }

    /**
     * <p>Clears the items currently stored in the cart.</p>
     */
    public void clear() {
        this.cartItems.clear();
    }
}
