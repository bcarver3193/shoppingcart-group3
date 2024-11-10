package model;

public class SingleProduct implements ProductComponent {
    private String name;
    private double price;
    private String description;
    private int stockQuantity;
    private double invoicePrice;

    public SingleProduct(String name, double price, double invoicePrice, String description, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.invoicePrice = invoicePrice;
        this.description = description;
        this.stockQuantity = stockQuantity;
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

    public boolean updateStock(int quantity) {
        if (stockQuantity + quantity >= 0) {
            stockQuantity += quantity;
            return true;
        }
        return false;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public double getInvoicePrice() {
        return invoicePrice;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    public void setInvoicePrice(double newInvoicePrice) {
        this.invoicePrice = newInvoicePrice;
    }
}
