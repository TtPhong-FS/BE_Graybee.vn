package vn.graybee.modules.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.modules.product.model.ProductDocument;
import vn.graybee.modules.product.service.ProductDocumentService;
import vn.graybee.modules.product.service.ProductService;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.response.publics.products.ProductDetailResponse;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${api.publicApi.products}")
public class ProductController {

    private final ProductDocumentService productDocumentService;

    private final ProductService productService;


    public ProductController(ProductDocumentService productDocumentService, ProductService productService) {
        this.productDocumentService = productDocumentService;
        this.productService = productService;
    }

    @GetMapping("/{category}")
    public ResponseEntity<MessageResponse<List<ProductBasicResponse>>> findByCategory(
            @PathVariable("category") String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order
    ) {
        return ResponseEntity.ok(productService.findByCategoryName(category, page, size, sortBy, order));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<BasicMessageResponse<ProductDetailResponse
            >> findDetailById(@PathVariable("id") long id) {
        return ResponseEntity.ok(productService.getDetailById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<BasicMessageResponse<List<ProductDocument>>> search(@RequestParam("keyword") String keyword) throws IOException {
        return ResponseEntity.ok(productDocumentService.search(keyword));
    }

}
