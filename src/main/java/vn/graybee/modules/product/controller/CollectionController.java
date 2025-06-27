package vn.graybee.modules.product.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.common.dto.PaginationInfo;
import vn.graybee.common.dto.SortInfo;
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
    public ResponseEntity<MessageResponse<List<ProductBasicResponse>>> findBySlugAndCategoryType(
            @PathVariable("slug") String slug,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order
    ) {
        Page<ProductBasicResponse> products = productService.findProductByCategorySlugAndCategoryType(slug, page, sortBy, order);

        PaginationInfo paginationInfo = new PaginationInfo(products.getNumber(), products.getTotalPages(), products.getTotalElements(), products.getSize());

        SortInfo sortInfo = new SortInfo(sortBy, order);

        return ResponseEntity.ok(
                MessageBuilder.ok(products.getContent(), null, paginationInfo, sortInfo)
        );
    }

}
