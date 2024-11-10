package model;

public class InventoryTester {
    public static void main(String[] args) {
        Inventory inventory = Inventory.getInstance();

        // Individual products
        ProductComponent product1 = new SingleProduct("Laptop", 1000.0, 750, "A high-performance laptop.", 8);
        ProductComponent product2 = new SingleProduct("Mouse", 25.0, 15, "Wireless mouse.", 20);
        ProductComponent product3 = new SingleProduct("Keyboard", 50.0, 35, "Mechanical keyboard.", 15);

        // Bundle of accessories
        ProductBundle accessoryBundle = new ProductBundle("Accessory Bundle");
        accessoryBundle.add(product2);
        accessoryBundle.add(product3);

        // Main product bundle including laptop and accessory bundle
        ProductBundle mainBundle = new ProductBundle("Electronics Bundle");
        mainBundle.add(product1);
        mainBundle.add(accessoryBundle);

        // Add to inventory
        inventory.addItem(product1);
        inventory.addItem(product2);
        inventory.addItem(product3);
        inventory.addItem(accessoryBundle);
        inventory.addItem(mainBundle);

        for (ProductComponent productComponent : inventory) {
            System.out.println(productComponent.getName());
        }
    }
}
