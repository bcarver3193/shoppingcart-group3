package cop4331;

import cop4331.model.Inventory;
import cop4331.model.Product;
import cop4331.model.Session;
import cop4331.model.UserSystem;
import cop4331.view.login.LoginView;

public class Main {
    public static void main(String[] args) {
        LoginView loginView = new LoginView();

        UserSystem userSystem = UserSystem.getInstance();
        Inventory inventory = Inventory.getInstance();

        userSystem.registerCustomer("c", "c");
        userSystem.registerSeller("s", "s");

        Session.getInstance().setCurrentUser(userSystem.verifyUser("s", "s"));

        Product test0 = new Product("test0", "0", "0", 10, 0, 100);
        Product test1 = new Product("test1", "1", "1", 10, 1, 100);
        Product test2 = new Product("test2", "2", "2", 10, 2, 100);
        Product test3 = new Product("test3", "3", "3", 10, 3, 100);
        Product test4 = new Product("test4", "4", "4", 10, 4, 100);
        Product test5 = new Product("test5", "5", "5", 10, 5, 100);
        Product test6 = new Product("test6", "6", "6", 10, 6, 100);
        Product test7 = new Product("test7", "7", "7", 10, 7, 100);
        Product test8 = new Product("test8", "8", "8", 10, 8, 100);
        Product test9 = new Product("test9", "9", "9", 10, 9, 100);

        inventory.addProduct(test0);
        inventory.addProduct(test1);
        inventory.addProduct(test2);
        inventory.addProduct(test3);
        inventory.addProduct(test4);
        inventory.addProduct(test5);
        inventory.addProduct(test6);
        inventory.addProduct(test7);
        inventory.addProduct(test8);
        inventory.addProduct(test9);

        Session.getInstance().invalidate();
    }
}
