package vn.graybee.modules.dashboard;

import vn.graybee.modules.dashboard.dto.OrderTotalResponse;
import vn.graybee.modules.dashboard.dto.ProductRevenue;

import java.util.List;

public interface DashboardService {

    List<ProductRevenue> getTenProductBestSeller();

    OrderTotalResponse getTotalOrders();

}
