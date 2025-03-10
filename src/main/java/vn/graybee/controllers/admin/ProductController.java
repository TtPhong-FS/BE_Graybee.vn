package vn.graybee.controllers.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.product.ProductProjection;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.requests.products.ProductValidationRequest;
import vn.graybee.response.products.ProductResponse;
import vn.graybee.services.products.ProductService;
import vn.graybee.validation.ProductValidation;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/products")
public class ProductController {

    private final ProductService productService;

    private final ProductValidation productValidation;

    public ProductController(ProductService productService, ProductValidation productValidation) {
        this.productService = productService;
        this.productValidation = productValidation;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<ProductResponse>> create(@RequestBody @Valid ProductCreateRequest request) {
        return ResponseEntity.ok(productService.create(request));
    }

    @PostMapping("/validation-general-info")
    public ResponseEntity<BasicMessageResponse<Map<String, String>>> validationGeneralInfo(@RequestBody @Valid ProductValidationRequest request) {
        return productValidation.validationGeneralInfo(request);
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ProductProjection>>> fetchAll() {
        return ResponseEntity.ok(productService.fetchAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body("DeleteMapping");
    }

}
