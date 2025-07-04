package vn.graybee.home;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.common.dto.PaginationInfo;
import vn.graybee.common.dto.SortInfo;
import vn.graybee.common.utils.CodeGenerator;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.catalog.dto.response.SidebarDto;
import vn.graybee.modules.catalog.service.CategoryService;
import vn.graybee.modules.product.dto.response.CategoryWithProducts;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;
import vn.graybee.modules.product.dto.response.ProductDetailDto;
import vn.graybee.modules.product.model.ProductDocument;
import vn.graybee.modules.product.service.ProductDocumentService;
import vn.graybee.modules.product.service.ProductService;

import java.time.Duration;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.home}")
public class HomeController {

    private final CategoryService categoryService;

    private final ProductService productService;

    private final ProductDocumentService productDocumentService;


    @GetMapping("/sidebar")
    public ResponseEntity<BasicMessageResponse<List<SidebarDto>>> getSidebar() {
        return ResponseEntity.ok(
                MessageBuilder.ok(categoryService.getSidebar(), null)
        );
    }

    @GetMapping("/session")
    public ResponseEntity<BasicMessageResponse<String>> setSession(HttpServletResponse response) {
        String sessionId = CodeGenerator.generateSessionId(5, 4, CodeGenerator.ALPHANUMERIC);

        ResponseCookie cookie = ResponseCookie.from("sessionId", sessionId)
                .httpOnly(false)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofDays(7))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(
                MessageBuilder.ok(sessionId, null)
        );
    }

    @GetMapping("/carousel/{category}")
    public ResponseEntity<BasicMessageResponse<CategoryWithProducts>> getProductByCategory(
            @PathVariable String category
    ) {
        CategoryWithProducts categoryWithProducts = productService.carouselTopTenProductBestSellByCategory(category);

        return ResponseEntity.ok(
                MessageBuilder.ok(categoryWithProducts, null)
        );
    }

    @GetMapping("/collections/{slug}")
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

    @GetMapping("/products/detail/{slug}")
    public ResponseEntity<BasicMessageResponse<ProductDetailDto>> getProductDetailBySlug(
            @PathVariable("slug") String slug
    ) {
        return ResponseEntity.ok(
                MessageBuilder.ok(productService.findProductDetailBySlug(slug), null)
        );
    }

    @GetMapping("/products/search/{keyword}")
    public ResponseEntity<BasicMessageResponse<List<ProductDocument>>> searchProducts(@PathVariable("keyword") String keyword) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        productDocumentService.searchProductsByKeyword(keyword), null
                )
        );
    }


}
