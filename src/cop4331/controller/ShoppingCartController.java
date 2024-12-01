package cop4331.controller;

import cop4331.model.ProductComponent;
import cop4331.model.customer.ShoppingCart;
import cop4331.view.customer.CustomerDashboardView;

import javax.swing.*;

/**
 * <p>Controller class for facilitating user interaction and updating the display
 * of the shopping cart.</p>
 *
 * @author Benjamin Carver
 */
public class ShoppingCartController {
    private CustomerDashboardView dashboardView;
    private ShoppingCart shoppingCart;

    /**
     * <p>Constructs a ShoppingCartController object.</p>
     * @param shoppingCart The ShoppingCart model object.
     * @param dashboardView The CustomerDashboard view object.
     */
    public ShoppingCartController(CustomerDashboardView dashboardView, ShoppingCart shoppingCart) {
        this.dashboardView = dashboardView;
        this.shoppingCart = shoppingCart;
    }

    /**
     * <p>Adds a product to the shopping cart and updates the view.</p>
     * @param product The product to be added.
     * @param quantity The amount of the product to add.
     */
    public void addProductToCart(ProductComponent product, int quantity) {
        if (product.getStockQuantity() >= quantity) {
            shoppingCart.addProduct(product, quantity);
            product.reduceStockQuantity(quantity);
            dashboardView.refreshView();
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient stock for "
                    + product.getName(),"Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * <p>Removes a product from the shopping cart and updates the view.</p>
     * @param product The product to be removed.
     */
    public void removeProductFromCart(ProductComponent product) {
        product.setStockQuantity(product.getStockQuantity()
                + shoppingCart.getCartItems().get(product));
        shoppingCart.removeProduct(product);
        dashboardView.refreshView();
    }

    /**
     * <p>Clears the cart of all items and adds the held stock back to the inventory.</p>
     */
    public void clearCart() {
        for (ProductComponent product : shoppingCart.getCartItems().keySet()) {
            product.setStockQuantity(product.getStockQuantity()
                    + shoppingCart.getCartItems().get(product));
        }
        shoppingCart.clear();
        dashboardView.refreshView();
    }

}
