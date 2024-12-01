package cop4331.model.seller;

import cop4331.model.Command;
import cop4331.view.seller.SellerDashboardView;

/**
 * <p>Concrete command class used for logging the seller out. Implements
 * the {@code Command} interface.</p>
 * @author Benjamin Carver
 */
public class SellerLogoutCommand implements Command {
    private SellerDashboardView dashboardView;

    /**
     * <p>Constructs a new SellerLogoutCommand object.</p>
     * @param dashboardView The seller's dashboard view
     */
    public SellerLogoutCommand(SellerDashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }

    /**
     * <p>Executes the logout function when called.</p>
     */
    @Override
    public void execute() {
        dashboardView.logout();
    }
}
