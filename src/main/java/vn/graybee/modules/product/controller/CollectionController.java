package vn.graybee.modules.product.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;
import vn.graybee.modules.product.service.ProductCategoryService;
import vn.graybee.modules.product.service.ProductService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.publicApi.collections}")
public class CollectionController {

    private final ProductCategoryService productCategoryService;

    private final ProductService productService;

    @GetMapping("/{slug}")
    public ResponseEntity<BasicMessageResponse<List<ProductBasicResponse>>> findBySlugAndCategoryType(
            @PathVariable("slug") String slug,
            @RequestParam("type") String type
    ) {
        return ResponseEntity.ok(
                MessageBuilder.ok(productService.findProductByCategorySlugAndCategoryType(slug, type), null)
        );
    }

}
