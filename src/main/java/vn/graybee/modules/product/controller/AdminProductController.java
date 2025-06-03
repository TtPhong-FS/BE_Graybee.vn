package vn.graybee.modules.product.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.modules.product.dto.request.ProductCreateRequest;
import vn.graybee.modules.product.dto.request.ProductUpdateRequest;
import vn.graybee.modules.product.dto.response.ProductResponse;
import vn.graybee.modules.product.dto.response.ProductUpdateDto;
import vn.graybee.modules.product.service.AdminProductService;
import vn.graybee.modules.product.service.ProductDocumentService;
import vn.graybee.response.admin.products.ProductStatusResponse;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("${api.adminApi.products}")
public class AdminProductController {


    private final ProductDocumentService productDocumentService;

    private final AdminProductService adminProductService;

    public AdminProductController(ProductDocumentService productDocumentService, AdminProductService adminProductService) {
        this.productDocumentService = productDocumentService;
        this.adminProductService = adminProductService;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<ProductResponse>> create(@RequestBody @Valid ProductCreateRequest request) {
        return ResponseEntity.ok(adminProductService.createProduct(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ProductResponse>> update(@PathVariable("id") long id, @RequestBody @Valid ProductUpdateRequest request) {
        return ResponseEntity.ok(adminProductService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Long>> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(adminProductService.deleteProductById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ProductUpdateDto>> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(adminProductService.getById(id));
    }

    @GetMapping
    public ResponseEntity<MessageResponse<List<ProductResponse>>> fetchAll(
            @RequestParam(value = "status", defaultValue = "all") String status,
            @RequestParam(value = "category", defaultValue = "all") String category,
            @RequestParam(value = "manufacturer", defaultValue = "all") String manufacturer,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "15") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "order", defaultValue = "desc") String order
    ) {
        return ResponseEntity.ok(adminProductService.getAllProductDtoForDashboard(status, category, manufacturer, page, size, sortBy, order));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<BasicMessageResponse<ProductStatusResponse>> updateStatusById(@PathVariable("id") long id, @PathVariable("status") String status) {
        return ResponseEntity.ok(adminProductService.updateStatus(id, status));
    }

//    @PutMapping("/restore/{id}")
//    public ResponseEntity<BasicMessageResponse<ProductResponse>> restoreProduct(@PathVariable("id") long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
//        return ResponseEntity.ok(iProductServiceAdmin.restoreProduct(id, userPrincipal));
//    }

    @GetMapping("/load-elastic")
    public ResponseEntity<BasicMessageResponse<String>> loadProductsPublishedIndexIntoElastic() throws IOException {
        return ResponseEntity.ok(productDocumentService.loadProductsPublishedIndexIntoElastic());
    }

}
