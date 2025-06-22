package vn.graybee.modules.product.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;
import vn.graybee.modules.product.dto.response.ProductDetailDto;
import vn.graybee.modules.product.model.ProductDocument;
import vn.graybee.modules.product.service.ProductDocumentService;
import vn.graybee.modules.product.service.ProductService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.publicApi.products}")
public class ProductController {

    private final ProductDocumentService productDocumentService;

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ProductBasicResponse>>> fetchAllProductPublished(
    ) {
        return ResponseEntity.ok(
                MessageBuilder.ok(productService.getAllProductPublished(), null)
        );
    }

    @GetMapping("/detail/{slug}")
    public ResponseEntity<BasicMessageResponse<ProductDetailDto>> findProductDetailBySlug(
            @PathVariable("slug") String slug
    ) {
        return ResponseEntity.ok(
                MessageBuilder.ok(productService.findProductDetailBySlug(slug), null)
        );
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<BasicMessageResponse<List<ProductDocument>>> search(@PathVariable("keyword") String keyword) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        productDocumentService.search(keyword), null
                )
        );
    }


    @GetMapping("/categories/{category}")
    public ResponseEntity<BasicMessageResponse<List<ProductBasicResponse>>> getProductByCategory(
            @PathVariable String category
    ) {
        return ResponseEntity.ok(
                MessageBuilder.ok(productService.getProductByCategory(category), null)
        );
    }


}
