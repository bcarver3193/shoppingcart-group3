package model;

public class ProductComponentTester {
    public static void main(String[] args) {
        // Individual products
        ProductComponent product1 = new SingleProduct("Laptop", 1000.0, "A high-performance laptop.");
        ProductComponent product2 = new SingleProduct("Mouse", 25.0, "Wireless mouse.");
        ProductComponent product3 = new SingleProduct("Keyboard", 50.0, "Mechanical keyboard.");

        // Bundle of accessories
        ProductBundle accessoryBundle = new ProductBundle("Accessory Bundle");
        accessoryBundle.add(product2);
        accessoryBundle.add(product3);

        // Main product bundle including laptop and accessory bundle
        ProductBundle mainBundle = new ProductBundle("Electronics Bundle");
        mainBundle.add(product1);
        mainBundle.add(accessoryBundle);

        // Display total price and description for the main bundle
        System.out.println("Total Price of Main Bundle: $" + mainBundle.getPrice());
        System.out.println("Description:\n" + mainBundle.getDescription());
    }
}
