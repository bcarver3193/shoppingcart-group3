package cop4331.model;

/**
 * <p>Subject interface for publishers like the {@code Inventory}</p>
 * @code Benjamin Carver
 */
public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();

}
