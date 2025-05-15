package vn.graybee.controllers.admin.products.detail;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.requests.products.ProductUpdateRequest;
import vn.graybee.response.admin.products.ProductResponse;
import vn.graybee.services.products.detail.PcDetailServices;

@RestController
@RequestMapping("/api/v1/admin/products")
public class AdminProductDetailController {

    private final PcDetailServices pcDetailServices;

    public AdminProductDetailController(PcDetailServices pcDetailServices) {
        this.pcDetailServices = pcDetailServices;
    }

    @PostMapping("/pc")
    public ResponseEntity<BasicMessageResponse<ProductResponse>> createProductWithPc(@Valid @RequestBody ProductCreateRequest request) {
        return ResponseEntity.ok(pcDetailServices.saveDetail(request, request.getDetail()));
    }

    @PutMapping("/{productId}/pc")
    public ResponseEntity<BasicMessageResponse<ProductResponse>> updateProductWithPc(@PathVariable("productId") Long productId, @Valid @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.ok(pcDetailServices.updateDetail(productId, request, request.getDetail()));
    }

}
