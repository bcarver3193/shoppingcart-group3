package model;

public interface ProductComponent {
    double getPrice();        // Gets the price of the product or bundle
    String getName();         // Gets the name of the product or bundle
    String getDescription();  // Gets the description of the product or bundle

    // Optional methods for Composite (ProductBundle)
    default void add(ProductComponent product) {
        throw new UnsupportedOperationException("Cannot add product to this component.");
    }

    default void remove(ProductComponent product) {
        throw new UnsupportedOperationException("Cannot remove product from this component.");
    }
}
