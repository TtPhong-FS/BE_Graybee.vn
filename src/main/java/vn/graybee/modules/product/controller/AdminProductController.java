package vn.graybee.modules.product.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.product.dto.request.ProductRequest;
import vn.graybee.modules.product.dto.request.ProductUpdateRequest;
import vn.graybee.modules.product.dto.request.ValidationProductRequest;
import vn.graybee.modules.product.dto.response.ProductDetailDto;
import vn.graybee.modules.product.dto.response.ProductResponse;
import vn.graybee.modules.product.dto.response.ProductUpdateDto;
import vn.graybee.modules.product.service.AdminProductService;
import vn.graybee.modules.product.service.ProductDocumentService;
import vn.graybee.response.admin.products.ProductStatusResponse;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.admin.products}")
public class AdminProductController {


    private final ProductDocumentService productDocumentService;

    private final AdminProductService adminProductService;

    private final MessageSourceUtil messageSourceUtil;

    @PostMapping
    public ResponseEntity<BasicMessageResponse<ProductResponse>> createProduct(@RequestBody @Valid ProductRequest request) {
        ProductResponse product = adminProductService.createProduct(request);
        return ResponseEntity.ok(
                MessageBuilder.ok(product, messageSourceUtil.get("product.success.create", new Object[]{product.getName()}))
        );
    }

    @PostMapping("/validation")
    public ResponseEntity<BasicMessageResponse<String>> validationStepGeneralNotId(
            @RequestBody @Valid ValidationProductRequest request) {
        adminProductService.checkBeforeCreate(null, request);

        return ResponseEntity.ok(
                MessageBuilder.ok(null, "Xác thực dữ liệu thành công")
        );
    }

    @PostMapping("/validation/{id}")
    public ResponseEntity<BasicMessageResponse<String>> validationStepGeneralById(
            @PathVariable(required = false) Long id,
            @RequestBody @Valid ValidationProductRequest request) {
        adminProductService.checkBeforeCreate(id, request);

        return ResponseEntity.ok(
                MessageBuilder.ok(null, "Xác thực dữ liệu thành công")
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ProductResponse>> updateProduct(@PathVariable("id") long id, @RequestBody @Valid ProductUpdateRequest request) {
        ProductResponse product = adminProductService.updateProduct(id, request);
        return ResponseEntity.ok(
                MessageBuilder.ok(product, messageSourceUtil.get("product.success.update", new Object[]{product.getName()}))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Long>> deleteProductById(@PathVariable("id") long id) {
        return ResponseEntity.ok(
                MessageBuilder.ok(adminProductService.deleteProductById(id), messageSourceUtil.get("product.success.delete"))
        );
    }

    @GetMapping("/for-update/{id}")
    public ResponseEntity<BasicMessageResponse<ProductUpdateDto>> getProductUpdateDtoById(@PathVariable("id") long id) {
        return ResponseEntity.ok(
                MessageBuilder.ok(adminProductService.getProductUpdateDtoById(id), null)
        );
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<BasicMessageResponse<ProductDetailDto>> getProductDetailById(@PathVariable("id") long id) {
        return ResponseEntity.ok(
                MessageBuilder.ok(adminProductService.getProductDetailById(id), null)
        );
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ProductResponse>>> getAllProductDtoForDashboard() {
        List<ProductResponse> productResponsePage = adminProductService.getAllProductDtoForDashboard();

        final String message = productResponsePage.isEmpty() ? messageSourceUtil.get("product.empty.list") : messageSourceUtil.get("product.success.fetch.list");

        return ResponseEntity.ok(
                MessageBuilder.ok(productResponsePage, message)
        );
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<BasicMessageResponse<ProductStatusResponse>> updateStatusById(@PathVariable("id") long id, @PathVariable("status") String status) {
        return ResponseEntity.ok(
                MessageBuilder.ok(adminProductService.updateStatus(id, status), messageSourceUtil.get("product.success.update.status"))
        );
    }


    @GetMapping("/load-elastic")
    public ResponseEntity<BasicMessageResponse<Integer>> loadProductsPublishedIndexIntoElastic() {
        int products = productDocumentService.loadProductsPublishedIndexIntoElastic();
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        products
                        ,
                        "Index thành công cho " + products + " sản phẩm"
                )
        );
    }

}
