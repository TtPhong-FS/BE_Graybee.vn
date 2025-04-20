package vn.graybee.controllers.publics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.messages.MessageResponse;
import vn.graybee.models.products.ProductDocument;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.response.publics.products.ProductDetailResponse;
import vn.graybee.serviceImps.products.ProductDocumentService;
import vn.graybee.services.products.ProductService_public;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public/products")
public class ProductController {

    private final ProductDocumentService productDocumentService;

    private final ProductService_public productServicePublic;


    public ProductController(ProductDocumentService productDocumentService, ProductService_public productServicePublic) {
        this.productDocumentService = productDocumentService;
        this.productServicePublic = productServicePublic;
    }

    @GetMapping("/{category}/{manufacturer}")
    public ResponseEntity<MessageResponse<List<ProductBasicResponse>>> findByCategoryAndManufacturer(
            @PathVariable("category") String category,
            @PathVariable("manufacturer") String manufacturer,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order) {
        return ResponseEntity.ok(productServicePublic.findByCategoryAndManufacturer(category, manufacturer, page, size, sortBy, order));
    }

    @GetMapping("/{category}/{subcategory}/{tag}")
    public ResponseEntity<MessageResponse<List<ProductBasicResponse>>> findByCategoryAndSubcategoryAndTag(
            @PathVariable("category") String category,
            @PathVariable("subcategory") String subcategory,
            @PathVariable("tag") String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order) {
        return ResponseEntity.ok(productServicePublic.findByCategoryAndSubcategoryAndTag(category, subcategory, tag, page, size, sortBy, order));
    }

    @GetMapping("/{category}")
    public ResponseEntity<MessageResponse<List<ProductBasicResponse>>> findByCategory(
            @PathVariable("category") String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order
    ) {
        return ResponseEntity.ok(productServicePublic.findByCategoryName(category, page, size, sortBy, order));
    }

    @GetMapping("/detail")
    public ResponseEntity<BasicMessageResponse<ProductDetailResponse
            >> findDetailById(@RequestParam("id") long id) {
        return ResponseEntity.ok(productServicePublic.getDetailById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<BasicMessageResponse<List<ProductDocument>>> search(@RequestParam("keyword") String keyword) throws IOException {
        return ResponseEntity.ok(productDocumentService.search(keyword));
    }

}
