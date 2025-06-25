package vn.graybee.home;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.catalog.dto.response.SidebarDto;
import vn.graybee.modules.catalog.service.CategoryService;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;
import vn.graybee.modules.product.service.ProductService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.publicApi.home}")
public class HomeController {

    private final CategoryService categoryService;

    private final ProductService productService;

    @GetMapping("/sidebar")
    public ResponseEntity<BasicMessageResponse<List<SidebarDto>>> getSidebar() {
        return ResponseEntity.ok(
                MessageBuilder.ok(categoryService.getSidebar(), null)
        );
    }

//
//    @GetMapping("/carousel")
//    public ResponseEntity<BasicMessageResponse<List<ProductBasicResponse>>> findAllProductBasics() {
//        return ResponseEntity.ok(productService.());
//    }


    @GetMapping("/carousel/{category}")
    public ResponseEntity<BasicMessageResponse<List<ProductBasicResponse>>> getProductByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order
    ) {

        List<ProductBasicResponse> products = productService.carouselTopTenProductBestSellByCategory(category);

        return ResponseEntity.ok(
                MessageBuilder.ok(products, null)
        );
    }


}
