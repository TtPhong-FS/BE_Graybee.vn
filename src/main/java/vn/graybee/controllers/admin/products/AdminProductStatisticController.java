package vn.graybee.controllers.admin.products;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.admin.products.ProductStatisticResponse;
import vn.graybee.services.products.ProductStatisticService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/admin/product-statistics")
public class AdminProductStatisticController {

    private final ProductStatisticService productStatisticService;

    public AdminProductStatisticController(ProductStatisticService productStatisticService) {
        this.productStatisticService = productStatisticService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ProductStatisticResponse>>> fetchAll() {
        return ResponseEntity.ok(productStatisticService.fetchAll());
    }

}
