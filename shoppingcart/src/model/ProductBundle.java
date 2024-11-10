package model;

import java.util.ArrayList;
import java.util.List;

public class ProductBundle implements ProductComponent {
    private String name;
    private List<ProductComponent> products = new ArrayList<>();

    public ProductBundle(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        double total = 0;
        for (ProductComponent product : products) {
            total += product.getPrice();
        }
        return total;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        StringBuilder description = new StringBuilder(name + " bundle includes:\n");
        for (ProductComponent product : products) {
            description.append("- ").append(product.getName()).append(": ").append(product.getDescription()).append("\n");
        }
        return description.toString();
    }

    @Override
    public void add(ProductComponent product) {
        products.add(product);
    }

    @Override
    public void remove(ProductComponent product) {
        products.remove(product);
    }
}
