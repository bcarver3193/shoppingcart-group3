package model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<CartItem> items = new ArrayList<>();

    public static class CartItem {
        ProductComponent product;
        int quantity;

        public CartItem(ProductComponent product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
    }

    public boolean addItem(ProductComponent product, int quantity) {
        if (product instanceof SingleProduct) {
            SingleProduct singleProduct = (SingleProduct) product;
            if (singleProduct.getStockQuantity() >= quantity) {
                items.add(new CartItem(product, quantity));
                return true;
            }
        }
        return false;
    }

    public void removeItem(ProductComponent product) {
        items.removeIf(item -> item.product == product);
    }

    public boolean updateQuantity(ProductComponent product, int newQuantity) {
        for (CartItem item : items) {
            if (item.product == product) {
                if (product instanceof SingleProduct) {
                    SingleProduct singleProduct = (SingleProduct) product;
                    if (singleProduct.getStockQuantity() >= newQuantity) {
                        item.quantity = newQuantity;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.product.getPrice() * item.quantity;
        }
        return total;
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }
}
