package cop4331.model.seller;

import cop4331.model.Command;
import cop4331.view.seller.SellerDashboardView;

/**
 * <p>Concrete command class used for opening the bundle items menu. Implements
 * the {@code Command} interface.</p>
 * @author Benjamin Carver
 */
public class SellerBundleItemsCommand implements Command {
    private SellerDashboardView dashboardView;

    /**
     * <p>Constructs a new SellerBundleItemsCommand object.</p>
     * @param dashboardView The seller's dashboard view
     */
    public SellerBundleItemsCommand(SellerDashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }

    /**
     * <p>Executes the bundleItems command when called.</p>
     */
    @Override
    public void execute() {
        dashboardView.bundleItems();
    }
}
