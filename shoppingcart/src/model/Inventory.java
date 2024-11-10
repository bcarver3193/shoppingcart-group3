package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory implements Iterable<ProductComponent> {
    private static Inventory instance;
    private List<ProductComponent> items;

    private Inventory() {
        items = new ArrayList<>();
    }

    public static synchronized Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }

    public void addItem(ProductComponent item) {
        items.add(item);
    }

    @Override
    public Iterator<ProductComponent> iterator() {
        return items.iterator();
    }
}
