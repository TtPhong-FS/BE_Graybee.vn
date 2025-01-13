package vn.graybee.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.services.ProductService;

@RestController
@RequestMapping("${api.products}")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<String> getProducts(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return ResponseEntity.ok("Get Mapping");
    }

    @PostMapping("")
    public ResponseEntity<String> insertProduct(@RequestBody @Valid ProductCreateRequest request) {
        productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.OK).body("PostMapping");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body("UpdateMapping");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body("DeleteMapping");
    }

}
