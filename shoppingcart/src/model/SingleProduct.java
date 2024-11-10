package model;

public class SingleProduct implements ProductComponent {
    private String name;
    private double price;
    private String description;

    public SingleProduct(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
