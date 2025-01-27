package vn.graybee.controllers.business;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.models.business.Product;
import vn.graybee.services.business.ProductService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathParam("id") long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("")
    public ResponseEntity<String> getProducts(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return ResponseEntity.ok("Get Mapping");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body("DeleteMapping");
    }

}
