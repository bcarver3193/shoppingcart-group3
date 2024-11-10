package model;

public class Customer extends User {
    private ShoppingCart cart;

    public Customer(String username, String password) {
        super(username, password);
        this.cart = new ShoppingCart();
    }

    public ShoppingCart getCart() {
        return cart;
    }
}
