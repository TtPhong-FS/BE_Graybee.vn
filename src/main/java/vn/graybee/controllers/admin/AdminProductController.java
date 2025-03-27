package vn.graybee.controllers.admin;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.requests.products.ProductUpdateRequest;
import vn.graybee.response.admin.products.ProductDto;
import vn.graybee.response.admin.products.ProductIdAndTagIdResponse;
import vn.graybee.response.admin.products.ProductResponse;
import vn.graybee.response.admin.products.ProductSubcategoryAndTagResponse;
import vn.graybee.response.admin.products.ProductSubcategoryIDResponse;
import vn.graybee.services.products.ProductService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/products")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<ProductResponse>> create(@RequestBody @Valid ProductCreateRequest request) {
        return ResponseEntity.ok(productService.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<ProductResponse>> update(@RequestParam("id") long id, @RequestBody @Valid ProductUpdateRequest request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Long>> delete(@RequestParam("id") long id) {
        return ResponseEntity.ok(productService.delete(id));
    }

    @DeleteMapping("/subcategories/delete")
    public ResponseEntity<BasicMessageResponse<ProductSubcategoryIDResponse>> deleteRelationByProductIdAndSubcategoryId(@RequestParam("productId") long productId, @RequestParam("subcategoryId") int subcategoryId) {
        return ResponseEntity.ok(productService.deleteRelationByProductIdAndSubcategoryId(productId, subcategoryId));
    }

    @DeleteMapping("/tags/delete")
    public ResponseEntity<BasicMessageResponse<ProductIdAndTagIdResponse>> deleteRelationByProductIdAndTagId(@RequestParam("productId") long productId, @RequestParam("tagId") int tagId) {
        return ResponseEntity.ok(productService.deleteRelationByProductIdAndTagId(productId, tagId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ProductDto>> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ProductResponse>>> fetchAll() {
        return ResponseEntity.ok(productService.fetchAll());
    }

    @GetMapping("/subcategories&tags")
    public ResponseEntity<BasicMessageResponse<List<ProductSubcategoryAndTagResponse>>> fetchProductsWithSubcategoriesAndTags() {
        return ResponseEntity.ok(productService.fetchAllWithSubcategoriesAndTags());
    }
    
}
