package vn.graybee.controllers.admin;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
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
import vn.graybee.response.admin.products.ProductDto;
import vn.graybee.response.admin.products.ProductResponse;
import vn.graybee.response.admin.products.ProductStatusResponse;
import vn.graybee.response.publics.ProductBasicResponse;
import vn.graybee.services.products.ProductService;
import vn.graybee.validation.ProductValidation;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/products")
public class AdminProductController {

    private final ProductService productService;

    private final ProductValidation productValidation;

    public AdminProductController(ProductService productService, ProductValidation productValidation) {
        this.productService = productService;
        this.productValidation = productValidation;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<ProductResponse>> create(@RequestBody @Valid ProductCreateRequest request) {
        return ResponseEntity.ok(productService.create(request));
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ProductResponse>>> fetchAll() {
        return ResponseEntity.ok(productService.getProductsForAdmin());
    }

    @GetMapping("/")
    public ResponseEntity<BasicMessageResponse<ProductDto>> findById(@PathParam("id") long id) {
        return ResponseEntity.ok(productService.findById(id));
    }


    @GetMapping("/category/{categoryName}")
    public BasicMessageResponse<List<ProductBasicResponse>> fetchByCategoryId(@PathVariable("categoryName") String categoryName) {
        return productService.fetchByCategoryName(categoryName);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Long>> delete(@RequestParam("id") long id) {
        return ResponseEntity.ok(productService.delete(id));
    }

    @PutMapping("/status")
    public ResponseEntity<BasicMessageResponse<ProductStatusResponse>> fetchByCategoryId(@RequestParam("id") long id, @RequestBody String status) {
        return ResponseEntity.ok(productService.updateStatus(id, status));
    }

}
