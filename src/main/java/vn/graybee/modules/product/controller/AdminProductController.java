package vn.graybee.modules.product.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
import vn.graybee.common.dto.PaginationInfo;
import vn.graybee.common.dto.SortInfo;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.product.dto.request.ProductRequest;
import vn.graybee.modules.product.dto.request.ProductUpdateRequest;
import vn.graybee.modules.product.dto.response.ProductResponse;
import vn.graybee.modules.product.dto.response.ProductUpdateDto;
import vn.graybee.modules.product.dto.response.ProductWithClassifyDto;
import vn.graybee.modules.product.service.AdminProductService;
import vn.graybee.modules.product.service.ProductDocumentService;
import vn.graybee.response.admin.products.ProductStatusResponse;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.adminApi.products}")
public class AdminProductController {


    private final ProductDocumentService productDocumentService;

    private final AdminProductService adminProductService;

    private final MessageSourceUtil messageSourceUtil;

    @PostMapping
    public ResponseEntity<BasicMessageResponse<ProductWithClassifyDto>> createProduct(@RequestBody @Valid ProductRequest request) {
        ProductWithClassifyDto productWithClassifyDto = adminProductService.createProduct(request);
        return ResponseEntity.ok(
                MessageBuilder.ok(productWithClassifyDto, messageSourceUtil.get("product.success.create", new Object[]{productWithClassifyDto.getProduct().getName()}))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ProductWithClassifyDto>> updateProduct(@PathVariable("id") long id, @RequestBody @Valid ProductUpdateRequest request) {
        ProductWithClassifyDto productWithClassifyDto = adminProductService.updateProduct(id, request);
        return ResponseEntity.ok(
                MessageBuilder.ok(productWithClassifyDto, messageSourceUtil.get("product.success.update", new Object[]{productWithClassifyDto.getProduct().getName()}))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Long>> deleteProductById(@PathVariable("id") long id) {
        return ResponseEntity.ok(
                MessageBuilder.ok(adminProductService.deleteProductById(id), messageSourceUtil.get("product.success.delete"))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ProductUpdateDto>> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(
                MessageBuilder.ok(adminProductService.getById(id), null)
        );
    }

    @GetMapping
    public ResponseEntity<MessageResponse<List<ProductResponse>>> getAllProductDtoForDashboard(
            @RequestParam(value = "status", defaultValue = "all") String status,
            @RequestParam(value = "category", defaultValue = "all") String category,
            @RequestParam(value = "manufacturer", defaultValue = "all") String manufacturer,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "15") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "order", defaultValue = "desc") String order
    ) {
        Page<ProductResponse> productResponsePage = adminProductService.getAllProductDtoForDashboard(status, category, manufacturer, page, size, sortBy, order);

        final String message = productResponsePage.getContent().isEmpty() ? messageSourceUtil.get("product.empty.list") : messageSourceUtil.get("product.success.fetch.list");

        return ResponseEntity.ok(
                MessageBuilder.ok(productResponsePage.getContent(), message, new PaginationInfo(
                        productResponsePage.getNumber(),
                        productResponsePage.getTotalPages(),
                        productResponsePage.getTotalElements(),
                        productResponsePage.getSize()
                ), new SortInfo(sortBy, order))
        );
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<BasicMessageResponse<ProductStatusResponse>> updateStatusById(@PathVariable("id") long id, @PathVariable("status") String status) {
        return ResponseEntity.ok(
                MessageBuilder.ok(adminProductService.updateStatus(id, status), messageSourceUtil.get("product.success.update.status"))
        );
    }


    @GetMapping("/load-elastic")
    public ResponseEntity<BasicMessageResponse<String>> loadProductsPublishedIndexIntoElastic() {
        return ResponseEntity.ok(productDocumentService.loadProductsPublishedIndexIntoElastic());
    }

}
