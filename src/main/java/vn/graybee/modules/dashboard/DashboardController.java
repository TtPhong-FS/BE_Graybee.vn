package vn.graybee.modules.dashboard;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.dashboard.dto.OrderTotalResponse;
import vn.graybee.modules.dashboard.dto.ProductRevenue;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.admin.dashboard}")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/product-best-seller")
    public ResponseEntity<BasicMessageResponse<List<ProductRevenue>>> getProductBestseller() {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        dashboardService.getTenProductBestSeller(),
                        null
                )
        );
    }

    @GetMapping("/total-orders")
    public ResponseEntity<BasicMessageResponse<OrderTotalResponse>> getTotalOrders() {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        dashboardService.getTotalOrders(),
                        null
                )
        );
    }

}
