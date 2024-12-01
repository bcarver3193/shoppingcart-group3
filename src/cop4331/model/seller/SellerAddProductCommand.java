package cop4331.model.seller;

import cop4331.model.Command;
import cop4331.view.seller.SellerDashboardView;

/**
 * <p>Concrete command class used for opening the add product menu. Implements
 * the {@code Command} interface.</p>
 * @author Benjamin Carver
 */
public class SellerAddProductCommand implements Command {
    private SellerDashboardView dashboardView;

    /**
     * <p>Constructs a new SellerAddProductCommand object.</p>
     * @param dashBoardView The seller's dashboard view
     */
    public SellerAddProductCommand(SellerDashboardView dashBoardView) {
        this.dashboardView = dashBoardView;
    }

    /**
     * <p>Executes the addProduct command when called.</p>
     */
    @Override
    public void execute() {
        dashboardView.addProduct();
    }
}
