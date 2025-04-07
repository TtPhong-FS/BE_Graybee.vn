package vn.graybee.controllers.publics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.response.publics.products.ProductDetailResponse;
import vn.graybee.services.products.ProductService_public;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/products")
public class ProductController {

    private final ProductService_public productServicePublic;

    public ProductController(ProductService_public productServicePublic) {
        this.productServicePublic = productServicePublic;
    }

    @GetMapping("/by-category-and-manufacturer")
    public ResponseEntity<BasicMessageResponse<List<ProductBasicResponse>>> findByCategoryAndManufacturer(
            @RequestParam("category") String category,
            @RequestParam("manufacturer") String manufacturer) {
        return ResponseEntity.ok(productServicePublic.findByCategoryAndManufacturer(category, manufacturer));
    }

    @GetMapping("/by-category-and-subcategory-and-tag")
    public ResponseEntity<BasicMessageResponse<List<ProductBasicResponse>>> findByCategoryAndSubcategoryAndTag(
            @RequestParam("category") String category,
            @RequestParam("subcategory") String subcategory,
            @RequestParam("tag") String tag) {
        return ResponseEntity.ok(productServicePublic.findByCategoryAndSubcategoryAndTag(category, subcategory, tag));
    }

    @GetMapping("/by-category")
    public ResponseEntity<BasicMessageResponse<List<ProductBasicResponse>>> findByCategory(
            @RequestParam("category") String category) {
        return ResponseEntity.ok(productServicePublic.findByCategoryName(category));
    }

    @GetMapping("/detail")
    public ResponseEntity<BasicMessageResponse<ProductDetailResponse
            >> findDetailById(@RequestParam("id") long id) {
        return ResponseEntity.ok(productServicePublic.getDetailById(id));
    }

}
