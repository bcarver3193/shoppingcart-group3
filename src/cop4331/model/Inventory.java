package cop4331.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * <p>Represents the inventory of products managed by the system. Provides functionality to add,
 * remove, update, and retrieve products. Implements the {@code Iterable} and {@code Subject} interfaces.</p>
 * @author Benjamin Carver
 */
public class Inventory implements Iterable<ProductComponent>, Subject {
    private static Inventory instance;
    private HashMap<String, ProductComponent> products;
    private ArrayList<Observer> observers;

    /**
     * <p>Private constructor for the Inventory.</p>
     */
    private Inventory() {
        this.products = new HashMap<>();
        this.observers = new ArrayList<>();
    }

    /**
     * <p>Gets the current Inventory instance or creates one if necessary.</p>
     * @return The Inventory instance.
     */
    public static synchronized Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }

        return instance;
    }

    /**
     * <p>Sets the current Inventory instance for use in Serialization.</p>
     * @param inventory The Inventory instance to be loaded.
     */
    public static void setInstance(Inventory inventory) {
        instance = inventory;
    }

    /**
     * <p>Adds a product to the Inventory.</p>
     * @param product The product to be added.
     * @throws IllegalArgumentException If the product is null or already exists in the Inventory.
     */
    public void addProduct(ProductComponent product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (products.containsKey(product.getName())) {
            throw new IllegalArgumentException("Product already exists.");
        }
        this.products.put(product.getName(), product);

        notifyObservers();
    }

    /**
     * <p>Removes a product from the Inventory.</p>
     * @param product The product to be removed.
     * @throws IllegalArgumentException If the product is null or does not exist in the Inventory.
     */
    public void removeProduct(ProductComponent product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (!products.containsKey(product.getName())) {
            throw new IllegalArgumentException("Product does not exist.");
        }

        products.remove(product.getName());

        notifyObservers();
    }

    /**
     * <p>Sets a product in the Inventory to a new value allowing for editing of product details
     * like prices and type.</p>
     * @param product The product to be put into the inventory.
     */
    public void setProduct(ProductComponent product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (!products.containsKey(product.getName())) {
            throw new IllegalArgumentException("Product does not exist.");
        }

        products.remove(product.getName());
        products.put(product.getName(), product);

        notifyObservers();
    }

    /**
     * <p>Creates an {@code iterator} for use when searching through the inventory.</p>
     * @return A new {@code Iterator}.
     */
    @Override
    public Iterator<ProductComponent> iterator() {
        return products.values().iterator();
    }

    /**
     * <p>Registers a new observer with the inventory.</p>
     * @param observer The new observer
     */
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * <p>Removes the observer from the observers list.</p>
     * @param observer The observer to be removed.
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * <p>Notifies all current observers of changes to the inventory.</p>
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
